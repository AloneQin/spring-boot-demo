package com.example.demo.common.sid;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON 序列化与反序列化时将数值类型{@link Number}自动转换为字符串类型{@link String}，避免精度丢失
 */
@JacksonAnnotationsInside
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = NumberToStringSerializer.class)
@JsonDeserialize(using = StringToNumberDeserializerResolver.class)
public @interface JsonNum2Str {
}
