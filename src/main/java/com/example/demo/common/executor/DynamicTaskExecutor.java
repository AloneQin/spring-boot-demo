package com.example.demo.common.executor;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态任务执行器
 * 负责管理线程池和消费者
 */
public class DynamicTaskExecutor {

    private final TaskManager taskManager = new TaskManager();
    private final TaskExecutor taskExecutor;

    /**
     * 消费者线程池
     */
    private volatile ExecutorService consumerPool;

    /**
     * 消费者线程数量
     */
    private volatile int consumerCount = 0;

    /**
     * 实际运行中的消费者线程数量
     */
    private final AtomicInteger actualConsumerCount = new AtomicInteger(0);

    public DynamicTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * 添加任务到执行器
     * @param addToFront true表示添加到队首，false表示添加到队尾
     * @return 生成的任务ID
     */
    public int addTask(boolean addToFront) {
        int taskId = taskManager.createTask();
        taskManager.enqueueTask(taskId, addToFront);
        ensureConsumerStarted();
        return taskId;
    }

    /**
     * 确保至少有一个消费者在运行
     */
    private void ensureConsumerStarted() {
        if (consumerCount <= 0) {
            resizeConsumerPool(1);
        }
    }

    /**
     * 动态调整消费者线程数量
     * @param newCount 新的消费者数量
     */
    public synchronized void resizeConsumerPool(int newCount) {
        if (newCount < 0) {
            throw new IllegalArgumentException("消费者数量不能为负数");
        }

        if (newCount > consumerCount) {
            // 增加消费者
            if (consumerPool == null || consumerPool.isShutdown()) {
                consumerPool = Executors.newCachedThreadPool();
            }

            int toAdd = newCount - consumerCount;
            for (int i = 0; i < toAdd; i++) {
                consumerPool.submit(new ConsumerWorker(taskManager, taskExecutor, this));
            }
        }
        // 减少消费者通过不创建新任务来实现
        consumerCount = newCount;
    }

    /**
     * 检查消费者是否应该继续运行
     * 用于实现优雅的线程数量调整
     */
    public boolean shouldConsumerContinue() {
        return actualConsumerCount.get() <= consumerCount;
    }

    /**
     * 增加实际运行的消费者计数
     */
    public void incrementActualConsumer() {
        actualConsumerCount.incrementAndGet();
    }

    /**
     * 减少实际运行的消费者计数
     */
    public void decrementActualConsumer() {
        actualConsumerCount.decrementAndGet();
    }

    /**
     * 获取待执行的任务列表
     */
    public List<Integer> getPendingTasks() {
        return taskManager.getPendingTasks();
    }

    /**
     * 获取正在执行的任务列表
     */
    public List<Integer> getRunningTasks() {
        return taskManager.getRunningTasks();
    }

    /**
     * 获取所有任务列表
     */
    public Map<Integer, Task> getAllTasks() {
        return taskManager.getAllTasks();
    }

    /**
     * 关闭任务执行器
     */
    public void shutdown() {
        if (consumerPool != null) {
            // 不在接受新任务
            consumerPool.shutdown();
            try {
                // 等待 5 秒超时，然后终止线程池
                if (!consumerPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    consumerPool.shutdownNow();
                    // 通知TaskManager清理运行中的任务状态
                    taskManager.clearRunningTasksOnShutdown();
                }
            } catch (InterruptedException e) {
                consumerPool.shutdownNow();
                // 通知TaskManager清理运行中的任务状态
                taskManager.clearRunningTasksOnShutdown();
                Thread.currentThread().interrupt();
            }
        }
    }
}
