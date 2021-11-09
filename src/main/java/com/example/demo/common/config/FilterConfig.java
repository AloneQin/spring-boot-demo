package com.example.demo.common.config;

import com.example.demo.common.metadata.enums.FilterConfigEnum;
import com.example.demo.common.filter.TestFilter;
import com.example.demo.common.filter.UrlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 */
@Configuration
public class FilterConfig {

    /**
     * URL 过滤器注册
     * @return
     */
    @Bean
    public FilterRegistrationBean urlFilterRegistrationBean() {
        FilterRegistrationBean<UrlFilter> registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new UrlFilter());
        registrationBean.setOrder(FilterConfigEnum.URL_FILTER.order);
        registrationBean.addUrlPatterns(FilterConfigEnum.URL_FILTER.urlPatterns);
        return registrationBean;
    }

    /**
     * test 过滤器注册
     * @return
     */
    @Bean
    public FilterRegistrationBean testFilterRegistrationBean() {
        FilterRegistrationBean<TestFilter> registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new TestFilter());
        registrationBean.setOrder(FilterConfigEnum.TEST_FILTER.order);
        registrationBean.addUrlPatterns(FilterConfigEnum.TEST_FILTER.urlPatterns);
        return registrationBean;
    }

}
