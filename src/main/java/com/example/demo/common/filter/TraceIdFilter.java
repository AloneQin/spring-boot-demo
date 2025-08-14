package com.example.demo.common.filter;

import com.example.demo.common.context.SystemContext;
import com.example.demo.common.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * 跟踪标识过滤器
 */
@Slf4j
public class TraceIdFilter implements Filter {

    private static final String ERROR_ATTRIBUTE = "org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 跟踪标识
        String traceId = request.getHeader(TraceContext.TRACE_ID);
        TraceContext.putTraceId(traceId);
        // 调试模式
        boolean debugMode = Boolean.parseBoolean(request.getHeader(SystemContext.SystemContextKVEnum.DEBUG_MODE.key));
        SystemContext.set(SystemContext.SystemContextKVEnum.DEBUG_MODE, debugMode);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 无异常信息说明请求正常执行，即将结束，清理跟踪标识；否则，请求异常，将会转发到错误控制器统一处理，此处不能清除
            Throwable throwable = (Throwable) request.getAttribute(ERROR_ATTRIBUTE);
            if (Objects.isNull(throwable)) {
                TraceContext.clear();
                SystemContext.clear();
            }
        }
    }
}
