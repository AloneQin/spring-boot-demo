package com.example.demo.juc;

import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {

    static Integer i = 0;
    static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        new Thread(task).start();
        new Thread(task).start();
        Thread.sleep(3000L);
        System.out.println("i: " + i);
        System.out.println("ai: " + ai.get());
    }

    @AllArgsConstructor
    static class Task implements Runnable {

        @Override
        public void run() {
            for (int j = 0; j < 100; j++) {
                try {
                    Thread.sleep(10L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
                ai.getAndIncrement();
            }
        }
    }

}


