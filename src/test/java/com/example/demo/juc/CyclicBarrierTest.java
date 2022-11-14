package com.example.demo.juc;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 循环栅栏测试
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        // 合并多线程计算结果
        mergeStatResult();

        Thread.sleep(1000L);
        System.out.println();

        // 重置屏障并重复使用
        resetForReuse();
    }

    private static void mergeStatResult() {
        int num = 3;
        CopyOnWriteArrayList<Integer> syncList = new CopyOnWriteArrayList<>();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, () -> {
            int total = syncList.stream().mapToInt(e -> e).sum();
            System.out.println("total: " + total);
        });

        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                int result = 60 + ThreadLocalRandom.current().nextInt(40);
                System.out.println("result: " + result);
                syncList.add(result);
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void resetForReuse() {
        int num = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, () -> {
            System.out.println("滴滴，车已满载，准备出发~~");
        });
        for (int i = 0; i < (num * 2); i++) {
            new Thread(() -> {
                try {
                    int sleepMillis = ThreadLocalRandom.current().nextInt(1000);
                    Thread.sleep(sleepMillis);
                    System.out.println(Thread.currentThread().getName() + " 乘客已就位，用时 " + sleepMillis + " ms");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
