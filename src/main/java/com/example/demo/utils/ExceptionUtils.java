package com.example.demo.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 */
@Slf4j
public class ExceptionUtils {

    /**
     * 获取异常堆栈
     * @param throwable 错误 or 异常
     * @return 堆栈信息
     */
    @SneakyThrows
    public static String getStackTrace(Throwable throwable) {
        @Cleanup StringWriter stringWriter = new StringWriter();
        @Cleanup PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }

     /**
     * 包装异常为 RuntimeException
     * @param throwable 错误 or 异常
     */
    public static RuntimeException wrapRuntime(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        }
        return new RuntimeException(throwable);
    }

    /**
     * 包装异常为指定类型的异常
     * @param throwable 错误 or 异常
     * @param clazz 指定类型的异常类
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T wrap(Throwable throwable, Class<T> clazz) {
        if (clazz.isInstance(throwable)) {
            return (T) throwable;
        }

        try {
            return clazz.getConstructor(Throwable.class).newInstance(throwable);
        } catch (Exception e) {
            throw new RuntimeException("无法包装异常: " + throwable.getClass() + " -> " + clazz, e);
        }
    }

    /**
     * 递归判断异常链是否包含指定类型的异常原因
     * @param throwable 错误 or 异常
     * @param type 指定类型的异常类
     */
    public static boolean containsType(Throwable throwable, Class<? extends Throwable> type) {
        if (throwable == null || type == null) {
            return false;
        }
        if (type.isAssignableFrom(throwable.getClass())) {
            return true;
        }
        return containsType(throwable.getCause(), type);
    }

}
