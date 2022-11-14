package com.example.demo.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        method1(lock, condition);
        method2(lock, condition);
    }

    private static void method1(Lock lock, Condition condition) {
        new Thread(() -> {
            try {
                lock.lock();
                condition.await();
                System.out.println("method1");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
    }

    private static void method2(Lock lock, Condition condition) {
        new Thread(() -> {
            boolean result = false;
            try {
                if (result = lock.tryLock(1L, TimeUnit.SECONDS)) {
                    System.out.println("method2");
                    condition.signal();
                    Thread.sleep(1000);
                } else {
                    System.out.println("获取锁失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (result) lock.unlock();
                System.out.println("method2 unlock");
            }
        }).start();
    }

}
