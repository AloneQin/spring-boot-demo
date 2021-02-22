package com.example.demo.common.exception;

import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.ReturnCodeEnum;
import lombok.Getter;

/**
 * 自定义全局异常
 */
@Getter
public class BaseException extends RuntimeException {

    private DefaultResponse defaultResponse;

    public BaseException(DefaultResponse defaultResponse) {
        super(defaultResponse.getMessage());
        this.defaultResponse = defaultResponse;
    }

    public BaseException(ReturnCodeEnum returnCodeEnum) {
        this(DefaultResponse.fail(returnCodeEnum));
    }
}
