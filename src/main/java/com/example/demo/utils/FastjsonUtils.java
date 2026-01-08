package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * fastjson 工具类<br/>
 * 提供常用的 json 转换方法
 */
public class FastjsonUtils {

    /**
     * 对象转字符串，省略为{@code null}的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 对象转格式化字符串，省略为{@code null}的属性
     * @param obj 输入对象
     * @return 格式化字符串
     */
    public static String toStringFormat(Object obj) {
        return toString(obj, SerializerFeature.PrettyFormat);
    }

    /**
     * 对象转字符串，保留为{@code null}的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toStringKeepNull(Object obj) {
        return toString(obj, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 对象转格式化字符串，保留为{@code null}的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toStringFormatKeepNull(Object obj) {
        return toString(obj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 对象转字符串
     * @param obj 输入对象
     * @param features 序列化参数<br/>
     *                 {@link SerializerFeature#WriteMapNullValue} 是否输出值为 {@code null} 字段，默认不输出<br/>
     *                 {@link SerializerFeature#PrettyFormat} 格式输出字符串，默认不格式化<br/>
     * @return 输出字符串
     */
    public static String toString(Object obj, SerializerFeature... features) {
        return JSON.toJSONString(obj, features);
    }

    /**
     * 字符串转对象
     * @param str 输入字符串
     * @param clazz 对象类型
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }

    /**
     * 字符串转对象
     * @param str 输入字符串
     * @param clazz 对象类型
     * @param features 特征参数
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, Class<T> clazz, Feature... features) {
        return JSON.parseObject(str, clazz, features);
    }

    /**
     * 字符串转对象，支持复杂泛型嵌套
     * @param str 输入字符串
     * @param typeReference 类型引用
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, TypeReference<T> typeReference) {
        return JSON.parseObject(str, typeReference);
    }

    /**
     * 字符串转对象，支持复杂泛型嵌套
     * @param str 输入字符串
     * @param typeReference 类型引用
     * @param features 特征参数
     * @param <T> 对象泛型
     * @return 输出对象
     */
    public static <T> T toObject(String str, TypeReference<T> typeReference, Feature... features) {
        return JSON.parseObject(str, typeReference, features);
    }

    /**
     * 字符串转对象数组，数组元素对象中不允许泛型嵌套
     * @param str 输入字符串
     * @param clazz 元素对象类型
     * @param <T> 元素对象泛型
     * @return 输出对象数组
     */
    public static <T> List<T> toArray(String str, Class<T> clazz) {
        return JSON.parseArray(str, clazz);
    }
}
