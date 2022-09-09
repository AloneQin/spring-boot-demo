package com.example.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * jackson 工具类<br/>
 * 提供常用的 json 转换方法
 */
public class JacksonUtils {

    private static final ObjectMapper OBJECT_MAPPER = initObjectMapper();

    /**
     * 对象转字符串，省略为<code>null<code/>的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toString(Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * 对象转格式化字符串，省略为<code>null<code/>的属性
     * @param obj 输入对象
     * @return 格式化字符串
     * @throws JsonProcessingException
     */
    public static String toStringFormat(Object obj) throws JsonProcessingException {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    /**
     * 对象转字符串，保留为<code>null<code/>的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toStringKeepNull(Object obj) throws JsonProcessingException {
        // 属性为 null 不进行序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * 对象转字符串
     * @param obj 输入对象
     * @param config 序列化配置
     * @return 输出字符串
     * @throws JsonProcessingException
     */
    public static String toString(Object obj, SerializationConfig config) throws JsonProcessingException {
        OBJECT_MAPPER.setConfig(config);
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * 字符串转对象
     * @param str 输入字符串
     * @param clazz 对象类型
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, Class<T> clazz) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(str, clazz);
    }

    /**
     * 字符串转对象，支持复杂泛型嵌套
     * @param str 输入字符串
     * @param typeReference 类型引用
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, TypeReference<T> typeReference) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(str, typeReference);
    }

    /**
     * 初始化
     * @return
     */
    private static ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 遇到未知属性不抛出异常（忽略 json 中存在但对象不存在的属性）
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 属性没有 setter 方法时，序列化不报错，返回空对象
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }
}
