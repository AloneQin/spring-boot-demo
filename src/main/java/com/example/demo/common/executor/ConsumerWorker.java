package com.example.demo.common.executor;

/**
 * 消费者工作线程
 */
public class ConsumerWorker implements Runnable {

    private final TaskManager taskManager;
    private final TaskExecutor taskExecutor;
    private final DynamicTaskExecutor dynamicTaskExecutor;

    public ConsumerWorker(TaskManager taskManager, TaskExecutor taskExecutor,
                          DynamicTaskExecutor dynamicTaskExecutor) {
        this.taskManager = taskManager;
        this.taskExecutor = taskExecutor;
        this.dynamicTaskExecutor = dynamicTaskExecutor;
        // 记录新增的消费者线程
        this.dynamicTaskExecutor.incrementActualConsumer();
    }

    @Override
    public void run() {
        try {
            // 持续消费任务直到被通知停止
            while (!Thread.currentThread().isInterrupted()) {
                // 检查是否应该继续运行（基于配置的消费者数量）
                if (!dynamicTaskExecutor.shouldConsumerContinue()) {
                    break;
                }

                try {
                    // 从任务队列获取任务
                    Integer taskId = taskManager.dequeueTask();
                    if (taskId != null) {
                        // 执行任务
                        executeTask(taskId);
                    }
                } catch (InterruptedException e) {
                    // 线程被中断时退出循环
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } finally {
            // 确保消费者计数正确减少
            dynamicTaskExecutor.decrementActualConsumer();
        }
    }

    /**
     * 执行具体任务
     */
    private void executeTask(Integer taskId) {
        // 标记任务为运行状态
        taskManager.markTaskAsRunning(taskId);

        try {
            boolean success = taskExecutor.execute(taskId);
            System.out.println(TaskManager.getNow() + " 任务 #" + taskId + " 执行" + (success ? "成功" : "失败"));
            if (success) {
                taskManager.markTaskCompleted(taskId, true, null);
            } else {
                handleTaskFailure(taskId, "执行失败");
            }
        } catch (Exception e) {
            handleTaskFailure(taskId, "执行异常: " + e.getMessage());
        }
    }

    private void handleTaskFailure(Integer taskId, String errorMessage) {
        Task task = taskManager.getTask(taskId);
        if (task != null) {
            if (task.getExecuteCount() < 3) {
                taskManager.markTaskCompleted(taskId, false, errorMessage);
                taskManager.requeueForRetry(taskId);
            } else {
                taskManager.markTaskCompleted(taskId, false, errorMessage);
            }
        }
    }
}