package com.example.demo.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * test 过滤器：用于测试
 */
@Slf4j
@Component
@WebFilter
@Order(FilterOrderConstant.TEST_FILTER_ORDER)
public class TestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("------------------------------------------------------");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        MapRequestWrapper requestWrapper = new MapRequestWrapper(request);
        // 添加请求头
        requestWrapper.addHeader("from", "wrapper");

        // 添加参数
        requestWrapper.addParameter("param2", "wrapper");

        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
