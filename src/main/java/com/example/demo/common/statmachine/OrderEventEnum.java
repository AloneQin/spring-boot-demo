package com.example.demo.common.statmachine;

/**
 * 订单事件枚举
 */
public enum OrderEventEnum {
    PAY,            // 支付
    SHIP,           // 发货
    DELIVER,        // 收货
    CANCEL,         // 取消
    REFUND          // 退款
}
