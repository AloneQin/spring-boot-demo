package com.example.demo.common.response;

/**
 * 子业务返回码
 */
public interface MyReturnCode {

    DefaultResponse<Void> ORDER_STATUS_ERROR = DefaultResponse.fail("200000", "订单状态错误", null);

}
