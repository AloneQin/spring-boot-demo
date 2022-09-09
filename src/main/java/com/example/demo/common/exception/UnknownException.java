package com.example.demo.common.exception;

import com.example.demo.common.response.ReturnCodeEnum;

/**
 * 未知异常
 */
public class UnknownException extends BaseException {

    public UnknownException() {
        super(ReturnCodeEnum.SERVER_ERROR);
    }

}
