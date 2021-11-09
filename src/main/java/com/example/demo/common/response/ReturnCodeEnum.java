package com.example.demo.common.response;

import lombok.AllArgsConstructor;

/**
 * 返回码枚举
 */
@AllArgsConstructor
public enum ReturnCodeEnum {

    // ---------------------------通用返回码---------------------------
    SUCCESS                     ("00000", "操作成功"),
    FAIL                        ("00001", "操作失败"),
    PARAM_ERROR                 ("00010", "参数缺省或错误"),
    RESOURCE_NOT_FOUND          ("00404", "资源未找到"),
    SERVER_ERROR                ("00500", "系统开小差了"),

    // ---------------------------业务返回码---------------------------
    NEED_LOGIN                  ("10000", "需要登录"),
    PERMISSION_DENIED           ("10001", "权限不足"),
    USERNAME_OR_PASSWORD_ERROR  ("10002", "用户名或密码错误"),
    ;

    public final String code;
    public final String message;

}
