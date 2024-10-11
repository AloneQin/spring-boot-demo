package com.example.demo.common.exception;

import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.ReturnCodeEnum;
import lombok.Getter;

/**
 * 自定义全局异常基类
 */
@Getter
public class BaseException extends RuntimeException {

    private DefaultResponse<?> defaultResponse;

    private BaseException(String message) {
        super(message);
    }

    public BaseException(DefaultResponse<?> defaultResponse) {
        this(defaultResponse.detailMessage());
        this.defaultResponse = defaultResponse;
    }

    public BaseException(ReturnCodeEnum returnCodeEnum) {
        this(DefaultResponse.fail(returnCodeEnum));
    }

    public BaseException(ReturnCodeEnum returnCodeEnum, String message) {
        this(returnCodeEnum.code, returnCodeEnum.message + ": " + message, null);
    }

    public <T> BaseException(String code, String message, T content) {
        this(DefaultResponse.fail(code, message, content));
    }
}
