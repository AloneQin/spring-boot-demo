package com.example.demo.utils;

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
    public static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        try {
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            log.error("getStackTrace, catch exception", e);
        } finally {
            printWriter.close();
        }
        return "unknown";
    }

}
