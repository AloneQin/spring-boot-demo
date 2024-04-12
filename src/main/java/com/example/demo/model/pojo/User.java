package com.example.demo.model.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Keyword 与 Text 均为字符串类型，区别是 Keyword 不支持分词，Text 是分词后进行储存的
 * 假如数据
 * {"attr":"abc"}
 * sql 语法 where attr = 'abc'，则查不到 Text 类型，因为是分词进行存储的
 *
 * Object 与 Nested 都可以用来储存复杂对象，区别是 Object 不允许内部属性独立的索引查询
 * 假如数据
 * 1: [{"name":"abc","type":1},{"name":"def","type":2}]
 * 2: [{"name":"abc","type":2},{"name":"def","type":1}]
 * sql 语法 where name = 'abc' and type = '1'，两条数据都会被检索到，Object 类型在存储时会将其属性进行扁平化处理
 * 此时会丢失 name 与 type 的关连性，所以无法得出想要的结果
 *
 */
@Data
@Document(indexName = "user", shards = 3, replicas = 1)
public class User {

    @Field(type = FieldType.Keyword)
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String name;

    private String nickname;

    @Field(type = FieldType.Text)
    private String note;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Date, format = DateFormat.epoch_millis)
    private Date birthday;

    @Field(type = FieldType.Boolean)
    private Boolean gender;

    @Field(type = FieldType.Float)
    private Float weight;

    @Field(type = FieldType.Object)
    private Role role;

    @Field(type = FieldType.Nested)
    private Permission permission;

    private String ext1;

}
