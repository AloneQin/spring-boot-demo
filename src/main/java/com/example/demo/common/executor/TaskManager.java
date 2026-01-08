package com.example.demo.common.executor;

import com.example.demo.utils.DateUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务管理器
 * 负责任务的创建、排队和状态管理
 */
public class TaskManager {

    /**
     * 任务ID生成器
     */
    private final AtomicInteger taskIdGenerator = new AtomicInteger(0);

    /**
     * 所有任务存储
     */
    private final ConcurrentHashMap<Integer, Task> allTasks = new ConcurrentHashMap<>();

    /**
     * 等待执行的任务队列
     */
    private final LinkedBlockingDeque<Integer> pendingQueue = new LinkedBlockingDeque<>();

    /**
     * 正在运行中的任务集合
     */
    private final Set<Integer> runningTasks = ConcurrentHashMap.newKeySet();

    /**
     * 创建并注册新任务
     */
    public int createTask() {
        int taskId = taskIdGenerator.incrementAndGet();
        Task task = new Task(taskId);
        allTasks.put(taskId, task);
        return taskId;
    }

    /**
     * 将任务添加到等待队列
     */
    public void enqueueTask(int taskId, boolean addToFront) {
        if (addToFront) {
            pendingQueue.offerFirst(taskId);
        } else {
            pendingQueue.offerLast(taskId);
        }
    }

    /**
     * 从等待队列获取任务
     */
    public Integer dequeueTask() throws InterruptedException {
        return pendingQueue.poll(1, TimeUnit.SECONDS);
    }

    /**
     * 获取任务对象
     */
    public Task getTask(int taskId) {
        return allTasks.get(taskId);
    }

    /**
     * 标记任务为运行状态
     */
    public void markTaskAsRunning(int taskId) {
        Task task = getTask(taskId);
        if (task != null) {
            task.setStatus(TaskStatusEnum.STATUS_RUNNING.value);
            task.incrementExecuteCount();
            task.setLastExecuteTime(new Date());
            runningTasks.add(taskId);
        }
    }

    /**
     * 标记任务完成（成功或失败）
     */
    public void markTaskCompleted(int taskId, boolean success, String errorMessage) {
        Task task = getTask(taskId);
        if (task != null) {
            if (success) {
                task.setStatus(TaskStatusEnum.STATUS_SUCCESS.value);
                task.setFinishTime(new Date());
            } else {
                if (task.getExecuteCount() < 3) {
                    task.setStatus(TaskStatusEnum.STATUS_WAITING_RETRY.value);
                    task.setLastErrorMessage(errorMessage);
                } else {
                    task.setStatus(TaskStatusEnum.STATUS_FAILED.value);
                    task.setLastErrorMessage(errorMessage);
                    task.setFinishTime(new Date());
                }
            }
            runningTasks.remove(taskId);
        }
    }

    /**
     * 重新排队等待重试的任务
     */
    public void requeueForRetry(int taskId) {
        Task task = getTask(taskId);
        if (task != null && Objects.equals(task.getStatus(), TaskStatusEnum.STATUS_WAITING_RETRY.value)) {
            System.out.println(TaskManager.getNow() + " 准备重新执行任务 #" + taskId);
            pendingQueue.offerFirst(taskId);
        }
    }

    /**
     * 在系统关闭时清理所有运行中的任务状态
     * 将运行中的任务状态更新为已取消或中断状态
     */
    public void clearRunningTasksOnShutdown() {
        synchronized (runningTasks) {
            Iterator<Integer> iterator = runningTasks.iterator();
            while (iterator.hasNext()) {
                Integer taskId = iterator.next();
                Task task = getTask(taskId);
                if (task != null) {
                    // 更新任务状态为取消状态
                    task.setStatus(TaskStatusEnum.STATUS_WAITING_RETRY.value);
                    task.setLastErrorMessage("Task cancelled due to executor shutdown");
                }
                iterator.remove();
            }
        }
    }

    /**
     * 获取待执行的任务列表
     */
    public List<Integer> getPendingTasks() {
        return new ArrayList<>(pendingQueue);
    }

    /**
     * 获取正在执行的任务列表
     */
    public List<Integer> getRunningTasks() {
        return new ArrayList<>(runningTasks);
    }

    /**
     * 获取所有任务
     */
    public Map<Integer, Task> getAllTasks() {
        return new HashMap<>(allTasks);
    }

    public static String getNow() {
        return DateUtils.date2Str(new Date(), DateUtils.Y_M_D_H_M_S_S);
    }
}
