package com.example.demo.common.response;

import com.example.demo.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 格式化响应 JSON 返回结构通知处理
 */
@Slf4j
@RestControllerAdvice
public class ResponseFormatAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, @NotNull Class clazz) {
        // 判断类或方法上是否使用了相关注解
        return AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ResponseFormat.class)
                || methodParameter.hasMethodAnnotation(ResponseFormat.class);
    }

    @Override
    public Object beforeBodyWrite(Object obj, @NotNull MethodParameter methodParameter, @NotNull MediaType mediaType, @NotNull Class clazz,
                                  @NotNull ServerHttpRequest serverHttpRequest, @NotNull ServerHttpResponse serverHttpResponse) {
        if (obj instanceof DefaultResponse) {
            return obj;
        }

        DefaultResponse<Object> defaultResponse = DefaultResponse.success(obj);

        // 如果是使用字符串转换器，则将最后结果转化成 JSON 字符串进行返回
        if (StringHttpMessageConverter.class.equals(clazz)) {
            return JacksonUtils.toString(defaultResponse);
        }

        return defaultResponse;
    }
}
