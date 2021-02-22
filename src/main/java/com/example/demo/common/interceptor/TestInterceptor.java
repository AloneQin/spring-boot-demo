package com.example.demo.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * test 拦截器：用于测试
 */
@Slf4j
public class TestInterceptor implements HandlerInterceptor {

    /**
     * controller 前执行
     * @return true=继续执行、false=拦截中断
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("interceptor -------------------- preHandle");

        return true;
    }

    /**
     * controller 后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("interceptor -------------------- postHandle");
    }


    /**
     * 视图渲染完毕之后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("interceptor -------------------- afterCompletion");
    }
}
