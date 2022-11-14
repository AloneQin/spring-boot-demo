package com.example.demo.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 倒计时锁存器测试
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        System.out.println("开始任务");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "线程开始运行");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        try {
            boolean result = countDownLatch.await(1, TimeUnit.SECONDS);
            if (result) {
                System.out.println("线程运行结束");
            } else {
                System.out.println("线程运行异常");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束任务");
    }


}
