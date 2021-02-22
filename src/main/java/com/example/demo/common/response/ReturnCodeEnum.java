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
    PARAM_ERROR                 ("00002", "参数缺省或错误"),
    SERVER_ERROR                ("00003", "服务器错误"),
    HTTP_STATUS_CODE_404        ("00404", "资源未找到"),

    // ---------------------------业务返回码---------------------------
    NEED_LOGIN                  ("10000", "需要登录"),
    PERMISSION_DENIED           ("10001", "权限不足"),
    USERNAME_OR_PASSWORD_ERROR  ("10002", "用户名或密码错误"),
    ;

    public String code;
    public String message;

}
