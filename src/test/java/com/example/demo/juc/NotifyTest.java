package com.example.demo.juc;

public class NotifyTest {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            synchronized (NotifyTest.class) {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                    try {
                        if ("t3".equals(Thread.currentThread().getName())) {
                            NotifyTest.class.notify();
                        } else {
                            if (i == 5) {
                                NotifyTest.class.wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t1 = new Thread(runnable, "t1");
        Thread t2 = new Thread(runnable, "t2");
        Thread t3 = new Thread(runnable, "t3");
        t1.start();
        t2.start();
        t3.start();

    }
}
