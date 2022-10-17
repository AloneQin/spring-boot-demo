package com.example.demo.common.metadata.enums;

import lombok.AllArgsConstructor;

/**
 * 过滤器配置枚举类
 */
@AllArgsConstructor
public enum FilterConfigEnum {

    /**
     * traceId 过滤器
     */
    TRACE_ID_FILTER     (100, new String[] {"/*"}),
    /**
     * URL 过滤器
     */
    URL_FILTER          (200, new String[] {"/*"}),
    /**
     * 跨域过滤器
     */
    CROSS_DOMAIN_FILTER (300, new String[] {"/*"}),
    /**
     * test 过滤器
     */
    TEST_FILTER         (400, new String[] {"/*"}),
    ;

    /**
     * 过滤器顺序，越小越先执行
     */
    public final Integer order;
    /**
     * 拦截通配符
     */
    public final String[] urlPatterns;
}
