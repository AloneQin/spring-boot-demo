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
    public static String toString(Object obj) {
        return toString(obj, OBJECT_MAPPER);
    }

    /**
     * 对象转字符串，省略为<code>null<code/>的属性
     * @param obj 输入对象
     * @param objectMapper 对象映射器
     * @return 输出字符串
     */
    public static String toString(Object obj, ObjectMapper objectMapper) {
        try {
            return toStringThrow(obj, objectMapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转字符串，省略为<code>null<code/>的属性，需处理编译异常
     * @param obj 输入对象
     * @return 输出字符串
     * @throws JsonProcessingException 异常
     */
    public static String toStringThrow(Object obj) throws JsonProcessingException {
        return toStringThrow(obj, OBJECT_MAPPER);
    }

    /**
     * 对象转字符串，省略为<code>null<code/>的属性，需处理编译异常
     * @param obj 输入对象
     * @param objectMapper 对象映射器
     * @return 输出字符串
     * @throws JsonProcessingException 异常
     */
    public static String toStringThrow(Object obj, ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 对象转格式化字符串，省略为<code>null<code/>的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toStringFormat(Object obj) {
         return toStringFormat(obj, OBJECT_MAPPER);
    }

    /**
     * 对象转格式化字符串，省略为<code>null<code/>的属性
     * @param obj 输入对象
     * @param objectMapper 对象映射器
     * @return 输出字符串
     */
    public static String toStringFormat(Object obj, ObjectMapper objectMapper) {
        try {
            return toStringFormatThrow(obj, objectMapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转格式化字符串，省略为<code>null<code/>的属性，需处理编译异常
     * @param obj 输入对象
     * @param objectMapper 对象映射器
     * @return 格式化字符串
     * @throws JsonProcessingException 异常
     */
    public static String toStringFormatThrow(Object obj, ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    /**
     * 对象转字符串，保留为<code>null<code/>的属性，每次使用时会新建一个对象映射器
     * @param obj 输入对象
     * @return 输出字符串
     * @throws RuntimeException 异常
     */
    public static String toStringKeepNull(Object obj) {
        return toStringKeepNull(obj, copyInstance());
    }

    /**
     * 对象转字符串，保留为<code>null<code/>的属性
     * @param obj 输入对象
     * @param objectMapper 对象映射器
     * @return 输出字符串
     */
    public static String toStringKeepNull(Object obj, ObjectMapper objectMapper) {
        try {
            return toStringKeepNullThrow(obj, objectMapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转字符串，保留为<code>null<code/>的属性，需处理编译异常
     * @param obj 输入对象
     * @param objectMapper 对象映射器
     * @return 输出字符串
     * @throws JsonProcessingException 异常
     */
    public static String toStringKeepNullThrow(Object obj, ObjectMapper objectMapper) throws JsonProcessingException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 字符串转对象
     * @param str 输入字符串
     * @param clazz 对象类型
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, Class<T> clazz) {
        return toObject(str, clazz, OBJECT_MAPPER);
    }

    /**
     * 字符串转对象
     * @param str 输入字符串
     * @param clazz 对象类型
     * @param objectMapper 对象映射器
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, Class<T> clazz, ObjectMapper objectMapper) {
        try {
            return toObjectThrow(str, clazz, objectMapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转对象，需处理编译异常
     * @param str 输入字符串
     * @param clazz 对象类型
     * @param objectMapper 对象映射器
     * @param <T> 对象泛型
     * @return 输出对象
     * @throws JsonProcessingException 异常
     */
    public static <T> T toObjectThrow(String str, Class<T> clazz, ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.readValue(str, clazz);
    }

    /**
     * 字符串转对象，支持复杂泛型嵌套
     * @param str 输入字符串
     * @param typeReference 类型引用
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, TypeReference<T> typeReference) {
        return toObject(str, typeReference, OBJECT_MAPPER);
    }

    /**
     * 字符串转对象，支持复杂泛型嵌套
     * @param str 输入字符串
     * @param typeReference 类型引用
     * @param objectMapper 对象映射器
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, TypeReference<T> typeReference, ObjectMapper objectMapper) {
        try {
            return toObjectThrow(str, typeReference, objectMapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转对象，支持复杂泛型嵌套，需处理编译异常
     * @param str 输入字符串
     * @param typeReference 类型引用
     * @param objectMapper 对象映射器
     * @param <T> 对象泛型
     * @return 输出对象
     * @throws JsonProcessingException 异常
     */
    public static <T> T toObjectThrow(String str, TypeReference<T> typeReference, ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.readValue(str, typeReference);
    }

    /**
     * 初始化
     * @return 对象映射器
     */
    private static ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 遇到未知属性不抛出异常（忽略 json 中存在但对象不存在的属性）
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 属性没有 setter 方法时，序列化不报错，返回空对象
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 属性为 null 不进行序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    /**
     * 复制对象映射器
     * @return 对象映射器
     */
    public static ObjectMapper copyInstance() {
        return OBJECT_MAPPER.copy();
    }

    /**
     * 复制对象映射器
     * @param config 需要设置的配置
     * @return 映射器
     */
    public static ObjectMapper copyInstance(SerializationConfig config) {
        ObjectMapper objectMapper = OBJECT_MAPPER.copy();
        return objectMapper.setConfig(config);
    }
}
