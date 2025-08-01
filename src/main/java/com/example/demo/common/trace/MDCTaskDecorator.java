package com.example.demo.common.trace;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * MDC 装饰器
 */
public class MDCTaskDecorator implements TaskDecorator {

    @NotNull
    @Override
    public Runnable decorate(@NotNull Runnable runnable) {
        Map<String, String> map = TraceContext.getCopyOfContextMap();
        return () -> {
            try {
                TraceContext.setContextMap(map);
                String traceId = TraceContext.getTraceId();
                TraceContext.putTraceId(traceId);
                runnable.run();
            } finally {
                TraceContext.clear();
            }
        };
    }
}
