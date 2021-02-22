package com.example.demo.common.exception;

import lombok.Data;

/**
 * 参数校验错误信息
 */
@Data
public class ParamError {

    /**
     * 参数名称
     */
    private String name;
    /**
     * 错误信息
     */
    private String message;

    public ParamError(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
