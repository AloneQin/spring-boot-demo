package com.example.demo.utils;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.response.ReturnCodeEnum;

import java.util.Collections;

class AssertUtilsTest {

    public static void main(String[] args) {
//        AssertUtils.nonEmpty(null, new BaseException(ReturnCodeEnum.FAIL));
        AssertUtils.nonEmpty(Collections.emptyList(), new BaseException(ReturnCodeEnum.FAIL));

    }

}