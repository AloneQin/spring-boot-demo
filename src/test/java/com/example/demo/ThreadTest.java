package com.example.demo;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ThreadTest {

    @Test
    void test() {
        TestThread testThread = new TestThread("abc");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.execute(testThread);
        threadPoolExecutor.shutdown();
        System.out.println("主线程执行完毕");
    }
}

@AllArgsConstructor
class TestThread implements Runnable {

    private String name;

    @Override
    public void run() {
        try {
            System.out.println(name);
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}