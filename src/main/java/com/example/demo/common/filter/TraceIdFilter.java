package com.example.demo.common.filter;

import com.example.demo.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * 跟踪标识过滤器
 */
@Slf4j
public class TraceIdFilter implements Filter {

    /**
     * 日志跟踪标识
     */
    public static final String TRACE_ID = "TRACE_ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // MDC: logback 提供的哈希表，存储键值对，在当前线程有效，日志打印时格式（%X{key}，如：%X{TRACE_ID}）
        MDC.put(TRACE_ID, RandomUtils.getUUID(false));
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.clear();
    }
}
