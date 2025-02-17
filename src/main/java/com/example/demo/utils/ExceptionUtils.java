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

}
