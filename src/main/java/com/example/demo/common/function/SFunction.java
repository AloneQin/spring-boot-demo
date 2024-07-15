package com.example.demo.common.function;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * 获取字段名称
     * @param sFunction 可序列化的函数式接口
     * @return 字段名称
     * @param <T> 输入类型
     * @param <R> 输出类型
     */
    static <T, R> String getFieldName(SFunction<T, R> sFunction) {
        try {
            // 通过 writeReplace() 方法以便获取 SerializedLambda 对象
            Method method = sFunction.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(sFunction);
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
            return Introspector.decapitalize(implMethodName.replace(prefix, ""));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}