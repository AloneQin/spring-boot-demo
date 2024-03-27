package com.example.demo.juc;

public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        };
        Thread t1 = new Thread(runnable, "t1");
        Thread t2 = new Thread(runnable, "t2");
        Thread t3 = new Thread(runnable, "t3");
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();

    }



}
