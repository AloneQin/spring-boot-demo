package com.example.demo.common.response;

import com.example.demo.common.config.ApplicationConfigContext;
import com.example.demo.common.trace.TraceContext;
import com.example.demo.utils.FastjsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 自定义默认返回结构（JSON）
 * @param <T> 内容主体泛型
 */
@Getter
@Setter
public class DefaultResponse<T> {

    /**
     * 返回码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 内容主体
     */
    private T content;

    /**
     * 跟踪标识
     */
    private String traceId;

    /**
     * 应用名称
     */
    private String application;

    private DefaultResponse(String code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
        this.traceId = TraceContext.getTraceId();
        this.application = ApplicationConfigContext.applicationName();
    }

    public static <T> DefaultResponse<T> success(T content) {
        return new DefaultResponse<>(ReturnCodeEnum.SUCCESS.code, ReturnCodeEnum.SUCCESS.message, content);
    }

    public static <T> DefaultResponse<T> success() {
        return success(null);
    }

    public static <T> DefaultResponse<T> fail(String code, String message, T content) {
        return new DefaultResponse<>(code, message, content);
    }

    public static <T> DefaultResponse<T> fail(ReturnCodeEnum returnCodeEnum, T content) {
        return fail(returnCodeEnum.code, returnCodeEnum.message, content);
    }

    public static <T> DefaultResponse<T> fail(ReturnCodeEnum returnCodeEnum) {
        return fail(returnCodeEnum, null);
    }

    public static <T> DefaultResponse<T> fail() {
        return fail(ReturnCodeEnum.FAIL);
    }

    /**
     * 获取详细信息
     * @return 详细信息
     */
    public String detailMessage() {
        if (successful(this)) {
            return message;
        }
        if (Objects.isNull(content)) {
           return message;
        }
        return message + ": " + FastjsonUtils.toString(content);
    }

    /**
     * 检查响应信息是否是{@code "执行成功"}
     * @param defaultResponse 目标响应信息
     * @return {@code true}=是，{@code false}=否
     */
    public static <T> boolean successful(DefaultResponse<T> defaultResponse) {
        return defaultResponse != null && ReturnCodeEnum.SUCCESS.code.equals(defaultResponse.code);
    }
}
