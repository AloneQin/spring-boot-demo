package com.example.demo.common.statmachine;

/**
 * 订单状态枚举
 */
public enum OrderStatusEnum {
    CREATED,        // 已创建
    PAID,           // 已支付
    SHIPPED,        // 已发货
    DELIVERED,      // 已收货
    CANCELLED,      // 已取消
    REFUNDED        // 已退款
}
