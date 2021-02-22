package com.example.demo.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * url 过滤器：记录到达服务器的所有请求信息
 */
@Slf4j
@Component
@WebFilter
@Order(FilterOrderConstant.URL_FILTER_ORDER)
public class UrlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String contentType = request.getContentType();
        log.info("method: {}, uri: {}, contentType: {}", method, uri, contentType);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
