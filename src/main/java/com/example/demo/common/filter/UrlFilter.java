package com.example.demo.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * url 过滤器：记录到达服务器的所有请求信息
 */
@Slf4j
public class UrlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String contentType = request.getContentType();
        long start = System.currentTimeMillis();
        log.info("#doFilter, IN: ●--->, method: {}, uri: {}, contentType: {}", method, uri, contentType);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            log.info("#doFilter, OUT: <---●, status: {}, encoding: {}, contentType: {}, cost: {} ms", response.getStatus(), response.getCharacterEncoding(), response.getContentType(), System.currentTimeMillis() - start);
        }
    }
}
