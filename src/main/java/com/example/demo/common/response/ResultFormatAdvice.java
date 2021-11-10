package com.example.demo.common.response;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 格式化返回结构通知处理
 */
@Slf4j
@RestControllerAdvice
public class ResultFormatAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class clazz) {
        // 判断类或方法上是否使用了相关注解
        return AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ResultFormat.class)
                || methodParameter.hasMethodAnnotation(ResultFormat.class);
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class clazz,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (obj instanceof DefaultResponse) {
            return obj;
        }

        DefaultResponse<Object> defaultResponse = DefaultResponse.success(obj);

        // 如果是使用字符串转换器，则将最后结果转化成 JSON 字符串进行返回
        if (StringHttpMessageConverter.class.equals(clazz)) {
            return JSON.toJSONString(defaultResponse);
        }

        return defaultResponse;
    }
}
