package com.example.demo.common.trace;

import com.example.demo.common.filter.TraceIdFilter;
import com.example.demo.utils.RandomUtils;
import com.example.demo.utils.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * MDC 装饰器
 */
public class MDCTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> map = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(map);
                String traceId = MDC.get(TraceIdFilter.TRACE_ID);
                if (StringUtils.isBlank(traceId)) {
                    traceId = RandomUtils.getUUID(false);
                    MDC.put(TraceIdFilter.TRACE_ID, traceId);
                }
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
