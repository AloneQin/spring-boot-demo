package com.example.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThreadInterruptedTest {

    public static void main(String[] args) {
//        example1();
        example2();
    }

    /**
     * 示例 1：
     * 使用原生线程实现子线程的打断
     * 每个线程都有自己的执行栈，子线程出现打断时，会导致子线程终止，但其抛出的异常无法直接在主线程中捕获
     */
    private static void example1() {
        Thread workThread = new Thread(() -> {
            try {
                mockTask();
            } catch (InterruptedException e) {
                System.out.println("work thread is interrupted");
                throw new RuntimeException(e);
            }
        });
        workThread.start();

        try {
            Thread.sleep(1000);
            // 中断异步线程
            workThread.interrupt();
            // 主线程等待异步线程执行完毕
            workThread.join();
        } catch (InterruptedException e) {
            System.out.println("main thread interrupted");
            e.printStackTrace();
        }
    }

    private static void mockTask() throws InterruptedException {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("work thread exec..." + i++);
            Thread.sleep(100);
        }
        throw new InterruptedException();
//        for (int i = 0; i < 10; i++) {
//            // 判断当前线程是否被打断
//            if (Thread.currentThread().isInterrupted()) {
//                System.out.println("task interrupted by thread interruption");
//                throw new InterruptedException();
//            }
//            System.out.println("work thread exec..." + i);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                System.out.println("task interrupted during sleep");
//                Thread.currentThread().interrupt(); // 重新设置中断标志位
//                throw e;
//            }
//
//        }
    }

    private static void example2() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                mockTask();
            } catch (InterruptedException e) {
                System.out.println("work thread is interrupted");
                throw new RuntimeException(e);
            }
        });

        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                System.out.println("work thread exception");
                throwable.printStackTrace();
            } else {
                System.out.println("work thread finished");
            }
        });

        try {
            Thread.sleep(1000);
            // 设置中断标志，但并不会直接中断在运行中的线程，需要任务本身响应中断，所以任务中需要添加中断判断
            future.cancel(true);
        } catch (InterruptedException e) {
            System.out.println("main thread interrupted");
            e.printStackTrace();
        }

        System.out.println("main thread finished");
    }
}
