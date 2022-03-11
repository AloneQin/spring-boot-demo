package com.example.demo.common.response;

import lombok.Data;

/**
 * 自定义默认返回结构（JSON）
 * @param <T> 内容主体泛型
 */
@Data
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

    private DefaultResponse(String code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
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
     * 检查响应信息是否是{@code "执行成功"}
     * @param defaultResponse 目标响应信息
     * @return {@code true}=是，{@code false}=否
     */
    public static boolean successful(DefaultResponse defaultResponse) {
        if (defaultResponse != null && ReturnCodeEnum.SUCCESS.code.equals(defaultResponse.code)) {
            return true;
        }

        return false;
    }
}
