package com.example.demo.common.executor;

/**
 * 任务执行器接口（由调用方实现具体业务逻辑）
 */
@FunctionalInterface
public interface TaskExecutor {

    /**
     * 执行具体任务
     * @param taskId 任务ID
     * @return 执行是否成功
     */
    boolean execute(int taskId);
}
