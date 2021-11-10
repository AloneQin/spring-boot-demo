package com.example.demo.common.response;

import java.lang.annotation.*;

/**
 * 自定义注解：格式化返回结构
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ResultFormat {

}
