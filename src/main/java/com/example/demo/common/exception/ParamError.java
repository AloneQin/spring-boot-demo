package com.example.demo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 参数校验错误信息
 */
@Data
@AllArgsConstructor
public class ParamError {

    /**
     * 参数名称
     */
    private String name;
    /**
     * 错误信息
     */
    private String message;

}
