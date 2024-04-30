package com.example.demo.common.websocket;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * WebSocket 请求映射注解
 * <p> 效果与 {@link org.springframework.web.bind.annotation.RequestMapping} 类似，可以使用地址映射的方式处理 WebSocket 请求 </p>
 */
@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface WebSocketRequestMapping {

    /**
     * 请求地址
     * @return 地址数组
     */
    String[] value() default {};

}
