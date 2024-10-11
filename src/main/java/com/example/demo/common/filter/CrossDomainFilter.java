package com.example.demo.common.filter;

import com.example.demo.common.response.DefaultResponse;
import com.example.demo.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 跨域过滤器：允许特点域名进行跨域
 */
@Slf4j
public class CrossDomainFilter implements Filter {

    /**
     * 允许跨域的域名
     */
    private final static List<String> ALLOW_DOMAIN_LIST = Arrays.asList(
            "http://xiaoqingge.vip",
            "http://127.0.0.1:8888"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        doCrossDomain(request, response, filterChain);
    }

    private void doCrossDomain(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String origin = request.getHeader("Origin");
        log.info("#doCrossDomain, origin: {}, remoteAddr: {}, remoteHost: {}, remotePort:{}",
                origin, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort());
        boolean isAllow = false;
        if (isAllow == ALLOW_DOMAIN_LIST.contains(origin)) {
            // 允许跨域的域名
            response.setHeader("Access-Control-Allow-Origin", origin);
            // 允许携带 cookie 认证
            response.setHeader("Access-Control-Allow-Credentials", "true");
            // 允许的请求方式
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            // 允许的请求头字段
            String allowHeaders = String.format("Content-Type,Authorization,%s", "token");
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        log.info("#doCrossDomain, origin: {}, isAllow: {}", origin, isAllow);

        if ("OPTIONS".equals(request.getMethod())) {
            HttpMethod.OPTIONS.name();
            ResponseUtils.outputJson(response, HttpStatus.OK.value(), DefaultResponse.success());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
