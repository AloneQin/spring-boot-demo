package com.example.demo.common.executor;

import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * 任务状态枚举
 */
@AllArgsConstructor
public enum TaskStatusEnum {

    STATUS_PENDING(10, "待执行"),
    STATUS_RUNNING(20, "执行中"),
    STATUS_SUCCESS(30, "任务成功"),
    STATUS_FAILED(40, "任务失败"),
    STATUS_WAITING_RETRY(50, "等待重试")
    ;

    public final Integer value;

    public final String name;


    /**
     * 判断是否为准备态（待执行或等待重试）
     */
    public static boolean isReadyState(Integer value) {
        return Objects.equals(value, STATUS_PENDING.value) || Objects.equals(value, STATUS_WAITING_RETRY.value);
    }

    /**
     * 判断是否为终止态（成功或失败）
     */
    public static boolean isTerminalState(Integer value) {
        return Objects.equals(value, STATUS_SUCCESS.value) || Objects.equals(value, STATUS_FAILED.value);
    }
}