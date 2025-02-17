package com.example.demo.utils;

import com.example.demo.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PoolExecutorUtilsTest {

    public static void main(String[] args) throws InterruptedException {
//        newFixedThreadPool();
//        TaskTimeoutExample();
//        checkInterrupted();
//        timeoutHandle();

        try {
            exception();
        } catch (Exception e) {
            System.out.println();
        }

        HashMap<String, Integer> map = new HashMap<>();
        int a = Optional.ofNullable(map.get("a")).orElse(0);
        System.out.println(a);
    }

    static void newFixedThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) PoolExecutorUtils.newFixedThreadPool(1, 1);
        int size = threadPoolExecutor.getQueue().size();
        System.out.println(size);

        Future<String> future = threadPoolExecutor.submit(() -> {
            System.out.println("------");
            Thread.sleep(500L);
            System.out.println("异步任务执行结束");
            return "success";
        });
        System.out.println("开启任务");
        try {
            String result = future.get();
            System.out.println("结束任务:" + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        threadPoolExecutor.shutdown();
    }

    static void TaskTimeoutExample() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) PoolExecutorUtils.newFixedThreadPool(1, 1);
        Future<?> future = threadPoolExecutor.submit(() -> {
            try {
                Thread.sleep(2000L);
            } catch (Exception e) {
                System.out.println("任务被中断");
                // 重新设置中断状态
                Thread.currentThread().interrupt();
            }
            System.out.println("任务完成");
        });
        try {
            // runnable 是无返回值的，所以这里的返回值是 null
            Object o = future.get(1, TimeUnit.SECONDS);
            System.out.println(o);
        } catch (InterruptedException e) {
            System.out.println("主线程被中断");
            // 重新设置中断状态
            Thread.currentThread().interrupt();
        } catch (TimeoutException e) {
            System.out.println("任务超时，尝试停止任务");
            // 强制停止任务
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println("任务执行出错");
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

    static void checkInterrupted() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("线程正在运行...");
                    // 线程阻塞时遇到中断，会抛出 InterruptedException 异常，此时需要重新设置中断状态
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException e) {
                System.out.println("线程被中断");
                // 重新设置中断状态
                Thread.currentThread().interrupt();
            }
        });

        thread.start();
        // 模拟主线程在 3 秒后中断任务
        Thread.sleep(3000L);
        thread.interrupt();
    }

    static void timeoutHandle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步执行任务");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "success";
        }).orTimeout(3, TimeUnit.SECONDS);
        System.out.println("初始化完成");
        future.whenComplete((result, throwable) -> {
            if (Objects.isNull(throwable)) {
                System.out.println("任务正常执行完成: " + result);
            } else {
                if (throwable instanceof TimeoutException) {
                    System.out.println("任务超时了");
                } else {
                    System.out.println("异常信息：" + throwable.getMessage());
                }
            }
        });
        System.out.println("主线程模拟等待");
        // 防止主线程退出（仅用于演示）
        try {
            Thread.sleep(3000); // 等待足够时间以观察异步任务的结果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void exception() {
        try {
            if (true) {
                throw new BaseException("1", "test", "test");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}