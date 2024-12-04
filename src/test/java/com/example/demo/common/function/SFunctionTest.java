package com.example.demo.common.function;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SFunctionTest {

    @Test
    void getFieldName() {
        System.out.println(SFunction.getFieldName(User::getId));
        System.out.println(SFunction.getFieldName(User::getName));
        System.out.println(SFunction.getFieldName(User::getAge));
        System.out.println(SFunction.getFieldName(User::getX_y_z, SFunction.FormatCastEnum.UNDERLINE_2_CAMEL));
        System.out.println(SFunction.getTableFieldValue(User::getName));
        System.out.println(SFunction.getFieldAnnotationValue(User::getAge, TableField.class, "exist"));
    }

    @Data
    static class User {
        private Integer id;

        @TableField(value = "c_name")
        private String name;

        @TableField(value = "c_age", exist = false)
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