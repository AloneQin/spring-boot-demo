package com.example.demo.common.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统参数属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "system")
public class SystemProperties {

    /**
     * 是否开启调试模式
     *
     * {@code true}=发生异常时，返回具体的异常堆栈信息以帮助调试
     * {@code false}=不返回
     */
    private Boolean debugMode = false;

}
