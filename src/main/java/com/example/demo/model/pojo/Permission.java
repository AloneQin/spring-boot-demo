package com.example.demo.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
public class Permission {

    private String name;

    @Field(type = FieldType.Keyword)
    private Integer type;

}
