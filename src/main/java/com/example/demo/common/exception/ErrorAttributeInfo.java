package com.example.demo.common.exception;

import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartBeanUtils;
import lombok.Data;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 错误属性信息，将{@link DefaultErrorAttributes}中 Map 的 key 映射为对象
 * @see DefaultErrorAttributes
 */
@Data
public class ErrorAttributeInfo {

    /**
     * 时间戳
     * @see DefaultErrorAttributes#getErrorAttributes(WebRequest, boolean)
     */
    private String timestamp;
    /**
     * HTTP 状态码
     * @see DefaultErrorAttributes#addStatus(Map, RequestAttributes)
     */
    private int status;
    /**
     * 错误信息
     * @see DefaultErrorAttributes#addStatus(Map, RequestAttributes)
     */
    private String error;
    /**
     * 其他错误信息
     * @see DefaultErrorAttributes#addErrorMessage(Map, Throwable)
     */
    private String errors;
    /**
     * 异常名称
     * @see DefaultErrorAttributes#addErrorDetails(Map, WebRequest, boolean)
     */
    private String exception;
    /**
     * 异常堆栈
     * @see DefaultErrorAttributes#addStackTrace(Map, Throwable)
     */
    private String trace;
    /**
     * 异常信息
     * @see DefaultErrorAttributes#addErrorDetails(Map, WebRequest, boolean)
     */
    private String message;
    /**
     * 请求路径
     * @see DefaultErrorAttributes#addPath(Map, RequestAttributes)
     */
    private String path;
    /**
     * 异常本身
     * @see DefaultErrorAttributes#getError(WebRequest)
     */
    private Throwable throwable;

    @Override
    public String toString() {
        ErrorAttributeInfo copy = SmartBeanUtils.copyProperties(this, ErrorAttributeInfo::new);
        if (copy != null) {
            copy.setTrace(null);
            copy.setThrowable(null);
        }
        return FastjsonUtils.toString(copy);
    }
}
