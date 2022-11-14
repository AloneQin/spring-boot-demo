package com.example.demo.juc;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 本地线程随机类测试
 */
public class ThreadLocalRandomTest {

    public static void main(String[] args) throws InterruptedException {
        int max = 100000;
        testRandom(max);
        Thread.sleep(5000L);
        testThreadLocalRandom(max);
    }

    private static void testRandom(int max) {
        long startTime = System.currentTimeMillis();
        Random random = new Random();
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < max; i++) {
            executor.execute(() -> {
                try {
                    random.nextInt(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        while (true) {
            if (executor.isTerminated()) {
                System.out.println(String.format("Random耗时：%d ms", System.currentTimeMillis() - startTime));
                return;
            }
        }
    }

    private static void testThreadLocalRandom(int max) {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < max; i++) {
            executor.execute(() -> {
                try {
                    ThreadLocalRandom.current().nextInt(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        while (true) {
            if (executor.isTerminated()) {
                System.out.println(String.format("ThreadLocalRandom耗时：%d ms", System.currentTimeMillis() - startTime));
                return;
            }
        }
    }
}
