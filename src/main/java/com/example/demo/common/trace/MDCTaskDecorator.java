package com.example.demo.common.trace;

import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * MDC 装饰器
 */
public class MDCTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> map = TraceManager.getCopyOfContextMap();
        return () -> {
            try {
                TraceManager.setContextMap(map);
                String traceId = TraceManager.getTraceId();
                TraceManager.putTraceId(traceId);
                runnable.run();
            } finally {
                TraceManager.clear();
            }
        };
    }
}
