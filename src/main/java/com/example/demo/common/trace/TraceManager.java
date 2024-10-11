package com.example.demo.common.trace;

import com.example.demo.utils.RandomUtils;
import com.example.demo.utils.SmartStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;

/**
 * 跟踪标识管理者<br/>
 * MDC: logback 提供的哈希表，存储键值对，在当前线程有效，日志打印时格式（%X{key}，如：%X{TRACE_ID}）
 */
@Slf4j
public class TraceManager {

    /**
     * 日志跟踪标识
     */
    public static final String TRACE_ID = "TRACE_ID";

    public static void putTraceId() {
        putTraceId(null);
    }

    public static void putTraceId(String traceId) {
        if (SmartStringUtils.isEmpty(traceId)) {
            MDC.put(TRACE_ID, RandomUtils.getUUID16().toUpperCase());
        } else {
            MDC.put(TRACE_ID, traceId);
        }
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static void clear() {
        MDC.clear();
    }

    public static Map<String, String> getCopyOfContextMap() {
        return MDC.getCopyOfContextMap();
    }

    public static void setContextMap(Map<String, String> map) {
        MDC.setContextMap(map);
    }
}
