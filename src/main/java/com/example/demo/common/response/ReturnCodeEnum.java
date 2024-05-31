package com.example.demo.common.response;

import lombok.AllArgsConstructor;

/**
 * 返回码枚举
 */
@AllArgsConstructor
public enum ReturnCodeEnum {

    // ---------------------------通用返回码---------------------------
    SUCCESS                     ("000000", "操作成功"),
    FAIL                        ("000001", "操作失败"),
    PARAM_ERROR                 ("000010", "参数缺省或错误"),
    RESOURCE_NOT_FOUND          ("000404", "资源未找到"),
    METHOD_NOT_ALLOWED          ("000405", "方法不被允许"),
    SERVER_ERROR                ("000500", "系统开小差了"),

    // ---------------------------业务返回码---------------------------
    /*
     * 用户相关
     */
    NEED_LOGIN                  ("100000", "需要登录"),
    PERMISSION_DENIED           ("100001", "权限不足"),
    USERNAME_OR_PASSWORD_ERROR  ("100002", "用户名或密码错误"),

    /*
     * 手机相关
     */
    PHONE_ALREADY_EXISTS        ("101000", "手机已存在"),

    /**
     * 测试相关
     */
    NAME_ILLEGAL                ("102000", "名称不合法"),
    ;

    public final String code;
    public final String message;

}
