package com.example.demo.common.executor;

import com.example.demo.utils.DateUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DynamicTaskExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        testBasicFunctionality();
//        testDynamicConsumerResize();
//        testConcurrentTaskAddition();
//        testTaskRetryMechanism();
    }

    /**
     * 测试基本功能
     */
    private static void testBasicFunctionality() throws InterruptedException {
        System.out.println("=== 测试基本功能 ===");

        // 初始化任务执行器
        DynamicTaskExecutor executor = new DynamicTaskExecutor(taskId -> {
            System.out.println(TaskManager.getNow() + " 执行任务 #" + taskId);
            try {
                // 模拟任务执行
                Thread.sleep(1000);
                // 75% 成功率
                return Math.random() < 0.75;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        // 设置初始消费者数量
        executor.resizeConsumerPool(4);

        // 添加任务
        for (int i = 0; i < 50; i++) {
            // 添加到队尾
            executor.addTask(false);
        }

        // 等待一段时间让任务执行
        Thread.sleep(2000);

        executor.resizeConsumerPool(2);
        System.out.println(TaskManager.getNow() + " 调整消费者队列大小");

        // 等待一段时间让任务执行
        Thread.sleep(10000);

        // 检查状态
        System.out.println("待执行任务数: " + executor.getPendingTasks().size());
        System.out.println("正在执行任务数: " + executor.getRunningTasks().size());

        // 显示任务详情
        Map<Integer, Task> allTasks = executor.getAllTasks();
        allTasks.values().forEach(task ->
                System.out.println("任务#" + task.getTaskId() +
                        ", 状态: " + task.getStatus() +
                        ", 执行次数: " + task.getExecuteCount()));

        executor.shutdown();
        System.out.println("基本功能测试完成\n");
    }

    /**
     * 测试动态调整消费者数量
     */
    private static void testDynamicConsumerResize() throws InterruptedException {
        System.out.println("=== 测试动态调整消费者数量 ===");

        DynamicTaskExecutor executor = new DynamicTaskExecutor(taskId -> {
            System.out.println("执行任务 #" + taskId + " by " + Thread.currentThread().getName());
            try {
                Thread.sleep(200); // 模拟较长的任务执行时间
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        // 添加大量任务
        for (int i = 0; i < 10; i++) {
            executor.addTask(false);
        }

        // 初始设置1个消费者
        System.out.println("设置1个消费者");
        executor.resizeConsumerPool(1);
        Thread.sleep(500);
        System.out.println("当前运行任务数: " + executor.getRunningTasks().size());

        // 增加到3个消费者
        System.out.println("增加到3个消费者");
        executor.resizeConsumerPool(3);
        Thread.sleep(500);
        System.out.println("当前运行任务数: " + executor.getRunningTasks().size());

        // 减少到2个消费者
        System.out.println("减少到2个消费者");
        executor.resizeConsumerPool(2);
        Thread.sleep(500);
        System.out.println("当前运行任务数: " + executor.getRunningTasks().size());

        Thread.sleep(3000); // 等待任务完成
        executor.shutdown();
        System.out.println("动态调整消费者数量测试完成\n");
    }

    /**
     * 测试并发添加任务
     */
    private static void testConcurrentTaskAddition() throws InterruptedException {
        System.out.println("=== 测试并发添加任务 ===");

        DynamicTaskExecutor executor = new DynamicTaskExecutor(taskId -> {
            try {
                Thread.sleep(50); // 快速执行
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        executor.resizeConsumerPool(3);

        // 使用多个线程并发添加任务
        ExecutorService producerPool = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(20);

        for (int i = 0; i < 20; i++) {
            final boolean addToFront = i % 3 == 0; // 每3个任务中有1个添加到队首
            producerPool.submit(() -> {
                try {
                    int taskId = executor.addTask(addToFront);
                    System.out.println("线程 " + Thread.currentThread().getName() +
                            " 添加任务 #" + taskId +
                            (addToFront ? " 到队首" : " 到队尾"));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 等待所有任务添加完成
        producerPool.shutdown();

        Thread.sleep(2000); // 等待任务执行

        System.out.println("总任务数: " + executor.getAllTasks().size());
        System.out.println("已完成任务数: " +
                executor.getAllTasks().values().stream()
                        .filter(task -> TaskStatusEnum.isTerminalState(task.getStatus()))
                        .count());

        executor.shutdown();
        System.out.println("并发添加任务测试完成\n");
    }

    /**
     * 测试任务重试机制
     */
    private static void testTaskRetryMechanism() throws InterruptedException {
        System.out.println("=== 测试任务重试机制 ===");

        DynamicTaskExecutor executor = new DynamicTaskExecutor(taskId -> {
            // 模拟会失败的任务
            double random = Math.random();
            boolean shouldFail = random < 0.7; // 70% 失败率

            System.out.println("执行任务 #" + taskId +
                    (shouldFail ? " 失败" : " 成功") +
                    " (随机值: " + String.format("%.2f", random) + ")");

            return !shouldFail;
        });

        executor.resizeConsumerPool(2);

        // 添加几个任务来测试重试
        for (int i = 0; i < 3; i++) {
            executor.addTask(false);
        }

        Thread.sleep(5000); // 给足够时间让重试发生

        // 检查重试情况
        Map<Integer, Task> allTasks = executor.getAllTasks();
        allTasks.values().forEach(task -> {
            System.out.println("任务 #" + task.getTaskId() +
                    ", 状态: " + task.getStatus() +
                    ", 执行次数: " + task.getExecuteCount() +
                    ", 最后错误: " + task.getLastErrorMessage());
        });

        executor.shutdown();
        System.out.println("任务重试机制测试完成\n");
    }
}
