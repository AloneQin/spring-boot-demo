package com.example.demo.common.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询用户信息注解
 */
@JacksonAnnotationsInside
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = QueryUserSerializer.class)
public @interface QueryUser {

    /**
     * 查询条件字段
     */
    String condition() default "id";

    /**
     * 查询结果字段
     */
    String result() default "real_name";

}
