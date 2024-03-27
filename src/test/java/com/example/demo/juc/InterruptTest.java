package com.example.demo.juc;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().isInterrupted() + ": do something");
                }

                System.out.println("-------");
            } catch (Exception e) {
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.interrupted());
                System.out.println(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(10L);
        thread.interrupt();
    }

}
