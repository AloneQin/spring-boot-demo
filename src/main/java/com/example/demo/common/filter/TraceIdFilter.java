package com.example.demo.common.filter;

import com.example.demo.common.trace.TraceManager;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 跟踪标识过滤器
 */
@Slf4j
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        TraceManager.putTraceId();
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        TraceManager.clear();
    }
}
