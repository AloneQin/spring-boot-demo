package com.example.demo.common.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SFunctionTest {

    @Test
    void getFieldName() {
        System.out.println(SFunction.getFieldName(User::getId));
        System.out.println(SFunction.getFieldName(User::getName));
        System.out.println(SFunction.getFieldName(User::getAge));
        System.out.println(SFunction.getFieldName(User::getX_y_z));
    }

    @Data
    static class User {
        private Integer id;
        private String name;
        private Integer age;
        private String x_y_z;
    }

    void contrast(Integer id, String name) {
        // -------------------------DB查询-------------------------
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 修改前
        queryWrapper.eq("id", id)
                .eq("name", name);
        // 修改后
        queryWrapper.eq(SFunction.getFieldName(User::getId), id)
                .eq(SFunction.getFieldName(User::getName), name);

        // -------------------------ES查询-------------------------
        // 修改前
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("id", id))
                .must(QueryBuilders.termQuery("name", name));
        // 修改后
        boolQueryBuilder.must(QueryBuilders.termQuery(SFunction.getFieldName(User::getId), id))
                .must(QueryBuilders.termQuery(SFunction.getFieldName(User::getName), name));
    }
}