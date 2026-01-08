package com.example.demo.utils;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * fastjson2 工具类
 * 提供常用的 json 转换方法
 */
public class Fastjson2Utils {

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
        return JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);
    }

    /**
     * 对象转字符串，保留为{@code null}的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toStringKeepNull(Object obj) {
        return JSON.toJSONString(obj, JSONWriter.Feature.WriteMapNullValue);
    }

    /**
     * 对象转格式化字符串，保留为{@code null}的属性
     * @param obj 输入对象
     * @return 输出字符串
     */
    public static String toStringFormatKeepNull(Object obj) {
        return JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue);
    }

    /**
     * 对象转字符串
     * @param obj 输入对象
     * @param features 序列化参数<br/>
     *                 {@link JSONWriter.Feature#WriteMapNullValue} 是否输出值为 {@code null} 字段，默认不输出<br/>
     *                 {@link JSONWriter.Feature#PrettyFormat} 格式输出字符串，默认不格式化<br/>
     * @return 输出字符串
     */
    public static String toString(Object obj, JSONWriter.Feature... features) {
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
    public static <T> T toObject(String str, Class<T> clazz, JSONReader.Feature... features) {
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
    public static <T> T toObject(String str, TypeReference<T> typeReference, JSONReader.Feature... features) {
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

    /**
     * 字符串转对象数组，数组元素对象中允许泛型嵌套
     * @param str 输入字符串
     * @param clazz 元素对象类型
     * @param <T> 元素对象泛型
     * @return 输出对象数组
     */
    public static <T> List<T> toArray(String str, Class<T> clazz, JSONReader.Feature... features) {
        return JSON.parseArray(str, clazz, features);
    }

    public static void main(String[] args) {

        @Data
        @Accessors(chain = true)
        class Obj {
            private Integer id;
            private String name;
            private String remark;
        }

        Obj obj = new Obj().setId(1);
        System.out.println(toString(obj));
        System.out.println(toStringFormat(obj));
        System.out.println(toStringKeepNull(obj));
        System.out.println(toStringFormatKeepNull(obj));
        System.out.println(toString(obj, JSONWriter.Feature.WriteMapNullValue));

        String str1 = "{\"id\":1,\"name\":\"phone1\"}";
        obj = toObject(str1, Obj.class);
        System.out.println(obj.toString());
        obj = toObject(str1, Obj.class, JSONReader.Feature.SupportArrayToBean);
        System.out.println(obj.toString());
        obj = toObject(str1, new TypeReference<>(){});
        System.out.println(obj.toString());
        obj = toObject(str1, new TypeReference<>(){}, JSONReader.Feature.SupportArrayToBean);
        System.out.println(obj.toString());

        String str2 = "[{\"id\":1,\"name\":\"phone1\"},{\"id\":2,\"name\":\"phone2\"}]";
        List<Obj> objList = toArray(str2, Obj.class);
        System.out.println(objList);
        objList = toArray(str2, Obj.class, JSONReader.Feature.SupportArrayToBean);
        System.out.println(objList);
    }
}
