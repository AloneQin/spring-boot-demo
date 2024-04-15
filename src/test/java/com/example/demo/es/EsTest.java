package com.example.demo.es;

import com.example.demo.dao.es.UserDAO;
import com.example.demo.model.pojo.Menu;
import com.example.demo.model.pojo.Permission;
import com.example.demo.model.pojo.Role;
import com.example.demo.model.pojo.User;
import com.example.demo.utils.DateUtils;
import com.example.demo.utils.FastjsonUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *         数据类型：
 *             字符串类型
 *                 keyword：不会进行分词，用于短文本，可以进行过滤、排序、聚合等操作，占用内存小。
 *                 text：会进行分词，用于长文本，不能进行过滤、排序、聚合等操作，占用内存大。
 *                 不指定类型，默认会给定 keyword 与 text 双类型，默认使用 text 进行查询，若想使用 keyword 必须在查询条件中进行指定 xxx.keyword
 *
 *         检索方式：
 *             term：精确查询，根据字段精确值进行查询，命中项必须精确相等
 *             terms：精确查询，根据字段精确值进行查询，查询条件可输入多项，命中项必须精确相等
 *             match：匹配查询，根据字段内容进行全文匹配，查询条件会被分词，命中项只要与一个查询条件分词匹配，即会返回，并按匹配程度排序
 *             wildcard：通配符查询，支持 * 和 ? 进行模糊匹配
 *             range：范围查询，gt 大于、gte 大于等于、lt 小于、lte 小于等于
 *             nested：嵌套查询，根据嵌套对象进行查询
 *             bool：布尔查询，通过逻辑运算符（must、must_not、should）组合多条件进行查询
 *
 *         逻辑运算符：
 *             must：必须满足，相当于 AND
 *             must_not：必须不满足，相当于 NOT
 *             should：至少满足一个，相当于 OR
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;

    @Test
    public void createIndex() {
        System.out.println("项目启动，创建索引");
    }

    /**
     * save() 方法无 id 时为新增，有 id 时为修改
     * @throws ParseException
     */
    @Test
    public void add() throws ParseException {
        User user1 = new User();
        user1.setId(1);
        user1.setName("小华");
        user1.setNickname("华华");
        user1.setNote("good boy");
        user1.setAge(24);
        user1.setBirthday(DateUtils.str2Date("2020-04-06 00:00:00"));
        user1.setGender(true);
        user1.setWeight(120.0f);
        user1.setRole(new Role("root", 1));
        user1.setPermission(new Permission("root", 1));
        user1.setMenuList(Arrays.asList(new Menu("main", "/main"), new Menu("manage", "/manage")));
        userDAO.save(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setName("小红");
        user2.setNickname("红红");
        user2.setNote("good girl");
        user2.setAge(22);
        user2.setBirthday(DateUtils.str2Date("2022-04-06 00:00:00"));
        user2.setGender(false);
        user2.setWeight(100.0f);
        user2.setRole(new Role("normal", 2));
        user2.setPermission(new Permission("normal", 2));
        user2.setMenuList(Arrays.asList(new Menu("main", "/main")));
        userDAO.save(user2);
    }

    @Test
    public void findAll() {
        Iterable<User> all = userDAO.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for(User user : all) {
            System.out.println(FastjsonUtils.toStringFormat(user));
        }
    }

    @Test
    public void findByPage() {
        // page 是从 0 开始计算
        PageRequest pageRequest = PageRequest.of(1, 1, Sort.by(Sort.Direction.ASC, "id"));
        Page<User> all = userDAO.findAll(pageRequest);
        for(User user : all) {
            System.out.println(FastjsonUtils.toStringFormat(user));
        }
    }

    @Test
    public void findOne() {
        Optional<User> optional = userDAO.findById(2);
        System.out.println(FastjsonUtils.toStringFormat(optional.orElse(new User())));
    }

    /**
     * keyword term
     */
    @Test
    public void term() {
        TermQueryBuilder builder = QueryBuilders.termQuery("name", "小华");
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .build();
        SearchHits<User> searchHits = esRestTemplate.search(query, User.class);
        List<User> userList = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));
    }

    /**
     * text term
     */
    @Test
    public void term2() {
        // 查询条件
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("note", "good");
        // 排序条件
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("id").order(SortOrder.DESC);
        // 分页参数
        PageRequest pageRequest = PageRequest.of(0, 2);
        // 执行查询
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSort(sortBuilder)
                .withPageable(pageRequest)
                .build();
        SearchHits<User> searchHits = esRestTemplate.search(query, User.class);
        // 获取分页信息
        SearchPage<User> searchPage = SearchHitSupport.searchPageFor(searchHits, query.getPageable());
        List<User> userList = searchPage.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        // 组装返回信息
        Page<User> page = new PageImpl<>(userList, searchPage.getPageable(), searchPage.getTotalElements());
        System.out.println(FastjsonUtils.toStringFormat(page));
    }

    @Test
    public void terms() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termsQuery("note", "girl", "boy"))
                .build();
        List<User> userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));
    }

    @Test
    public void match() {
        // keyword 不分词，匹配不上
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "华"))
                .build();
        List<User> userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));

        // text 分词，能匹配上
        query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("nickname", "张华"))
                .build();
        userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));
    }

    @Test
    public void wildcard() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.wildcardQuery("note", "*o*"))
                .build();
        List<User> userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));

        // 分词后不支持多个词进行查询
        query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.wildcardQuery("note", "good girl"))
                .build();
        userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));

        // 需要支持多个词必须对多个词进行模糊查询
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder
                .must(QueryBuilders.wildcardQuery("note", "good"))
                .must(QueryBuilders.wildcardQuery("note", "girl"));
        query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .build();
        userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));
    }

    /**
     * 范围查询
     * gt：大于
     * gte：大于等于
     * lt：小于
     * lte：小于等于
     */
    @Test
    public void range() throws ParseException {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.rangeQuery("birthday").gt(DateUtils.str2Date("2020-04-06 00:00:00").getTime()))
                .build();
        List<User> userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));
    }

    @Test
    public void object() {
        /*
            object 对象类型可正常取交集
            sql：where role.name = 'root' and role.type = 1
            现象：能正常取到交集
            结论：单个对象时（JSONObject）使用 object 类型
         */
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder
                .must(QueryBuilders.termQuery("role.name", "root"))
                .must(QueryBuilders.termQuery("role.type", "1"));
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .build();
        List<User> userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));

        /*
            object 数组类型取不到交集
            sql: where menuList.path = 'manage' and menuList.name = 'main'
            现象：这条数据其实是不存在的，但还是查出来，交集变成了并集
            结论：数组对象时（JSONArray）使用 nested 类型
         */
        builder = QueryBuilders.boolQuery();
        builder
                .must(QueryBuilders.matchQuery("menuList.path", "manage"))
                .must(QueryBuilders.termQuery("menuList.name", "main"));
        query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .build();
        userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));
    }

    @Test
    public void nested() {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.nestedQuery("permission", QueryBuilders.termQuery("permission.name", "root"), ScoreMode.None))
                .build();
        // 打印查询语句
        System.out.println("#Elasticsearch Query:" + System.lineSeparator() + query.getQuery().toString());
        List<User> userList = esRestTemplate.search(query, User.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        System.out.println(FastjsonUtils.toStringFormat(userList));
    }
}