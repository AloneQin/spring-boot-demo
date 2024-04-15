package com.example.demo.model.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

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

    @Field(type = FieldType.Object)
    private List<Menu> menuList;
}
