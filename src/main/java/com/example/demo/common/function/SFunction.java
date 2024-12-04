package com.example.demo.common.function;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.utils.SmartStringUtils;
import lombok.SneakyThrows;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

/**
 * 可序列化的函数式接口 <br/>
 *
 * 继承 Serializable 以便反射动态生成 writeReplace() 方法 <br/>
 * 继承 Function 以便使用 Lambda 表达式 <br/>
 *
 * @param <T> 输入类型
 * @param <R> 输出类型
 */
@FunctionalInterface
public interface SFunction<T, R> extends Serializable, Function<T, R> {

    @SneakyThrows
    private static <T, R> SerializedLambda getSerializedLambda(SFunction<T, R> sFunction) {
        // 通过 writeReplace() 方法以便获取 SerializedLambda 对象
        Method method = sFunction.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        return (SerializedLambda) method.invoke(sFunction);
    }

    /**
     * 获取字段名称
     * @param sFunction 可序列化的函数式接口
     * @return 字段名称
     * @param <T> 输入类型
     * @param <R> 输出类型
     */
    static <T, R> String getFieldName(SFunction<T, R> sFunction, FormatCastEnum formatCastEnum) {
        SerializedLambda serializedLambda = getSerializedLambda(sFunction);
        String implMethodName = serializedLambda.getImplMethodName();
        String prefix = "";
        if (implMethodName.startsWith("is")) {
            prefix = "is";
        } else if (implMethodName.startsWith("get")) {
            prefix = "get";
        } else {
            throw new RuntimeException("get()方法名称[" + implMethodName + "]不符合Java Bean规范");
        }
        // 截取字段名并将首字母转换为小写
        String str = Introspector.decapitalize(implMethodName.replace(prefix, ""));
        // 字符串转换
        if (Objects.equals(FormatCastEnum.UNDERLINE_2_CAMEL, formatCastEnum)) {
            str = SmartStringUtils.underline2Camel(str);
        } else if (Objects.equals(FormatCastEnum.CAMEL_2_UNDERLINE, formatCastEnum)) {
            str = SmartStringUtils.camel2underline(str);
        }
        return str;
    }

    static <T, R> String getFieldName(SFunction<T, R> sFunction) {
        return getFieldName(sFunction, null);
    }

    /**
     * 获取字段注解值
     * @param sFunction 可序列化的函数式接口
     * @param annotationClass 注解类
     * @param methodName 注解方法名
     * @return 注解值
     * @param <T> 输入类型
     * @param <R> 输出类型
     */
    @SneakyThrows
    static <T, R> Object getFieldAnnotationValue(SFunction<T, R> sFunction, Class<? extends Annotation> annotationClass, String methodName) {
        String fieldName = getFieldName(sFunction);
        SerializedLambda serializedLambda = getSerializedLambda(sFunction);
        Field field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
        field.setAccessible(true);
        Annotation annotation = field.getAnnotation(annotationClass);
        return Objects.nonNull(annotation) ? annotation.annotationType().getMethod(methodName).invoke(annotation) : null;
    }

    static <T, R> String getTableFieldValue(SFunction<T, R> sFunction) {
        return (String) getFieldAnnotationValue(sFunction, TableField.class, "value");
    }

    /**
     * 格式转换枚举
     */
    enum FormatCastEnum {
        /**
         * 下划线转驼峰
         */
        UNDERLINE_2_CAMEL,
        /**
         * 驼峰转下划线
         */
        CAMEL_2_UNDERLINE,
        ;
    }
}