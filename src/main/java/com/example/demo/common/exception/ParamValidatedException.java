package com.example.demo.common.exception;

import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.ReturnCodeEnum;

import java.util.List;

/**
 * 参数校验异常
 */
public class ParamValidatedException extends BaseException {

    public ParamValidatedException(List<ParamError> paramErrorList) {
        super(DefaultResponse.fail(ReturnCodeEnum.PARAM_ERROR, paramErrorList));
    }

}
