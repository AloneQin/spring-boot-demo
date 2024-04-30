package com.example.demo.common.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 *  <code>@WebSocketReqMapping</> 注解运行信息
 */
@Data
@AllArgsConstructor
public class WebSocketReqMappingInfo {

    /**
     * 注解所在类
     */
    private Class<?> clazz;

    /**
     * 注解所在方法
     */
    private Method method;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 注解所在 bean
     */
    private Object bean;

}
