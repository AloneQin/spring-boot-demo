package com.example.demo.juc;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 异步 API 测试类
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        simpleAsync();
//        stageAsync();
//        andAsync();
//        andAllOfAsync();
//        orAsync();
        orAnyOfAsync();
//        exceptionallyAsync();
    }

    /**
     * 简单异步<br/>
     * 异步调用耗时任务
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public static void simpleAsync() throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println(System.currentTimeMillis());
        // 无返回值，创建后启动
        CompletableFuture<Void>  future1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("future1执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 有返回值，创建后启动
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("future2执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "异步执行完成";
        });
        String result = null;
        try {
            result = future2.get(500L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            result = future2.getNow("给定默认值");
        }
        System.out.println("主线程执行");
        System.out.println("获取结果：" + result);
        Thread.sleep(1500L);
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 异步-串行<br/>
     * 各异步任务顺序执行
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void stageAsync() throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步1执行完成");
            return "1";
        }).thenApply(s -> {
            // s  为上一个任务的结果
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步2执行完成");
            return s + "2";
        });
        System.out.println("主线程执行");
        System.out.println("获取结果：" + future.get());
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 异步-AND<br/>
     * A + B 执行完后才能执行 C
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void andAsync() throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步1执行完成");
            return "1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步2执行完成");
            return "2";
        });
        // f1 为 future1 的结果，f2 为 future2 的结果
        CompletableFuture<String> future3 = future1.thenCombineAsync(future2, (f1, f2) -> {
            System.out.println("异步3执行完成");
            return f1 + f2 + "3";
        });
        System.out.println("主线程执行");
        System.out.println("获取结果：" + future3.get());
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 异步-AND ALL<br/>
     * A + B + C 全部完成
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void andAllOfAsync() throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步1执行完成");
            return "1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步2执行完成");
            return "2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步3执行完成");
            return "3";
        });
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(future1, future2, future3);
        System.out.println("主线程执行");
        // allOf 只是将所有任务打包成一个组合，这个组合本身并没有结果，可以用 CompletableFuture.allOf(future1, future2, future3).join() 代替
        System.out.println("获取结果：" + allOfFuture.get());
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 异步-AND<br/>
     * A || B 执行完任何一个后执行 C
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void orAsync() throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(501);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步1执行完成");
            return "1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步2执行完成");
            return "2";
        });
        CompletableFuture<String> future3 = future1.applyToEitherAsync(future2, s -> {
            // s  为 future1、future2 任意一个任务的结果
            System.out.println("异步3执行完成");
            return s + "3";
        });
        System.out.println("主线程执行");
        System.out.println("获取结果：" + future3.get());
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 异步-OR ANY <br/>
     * A || B || C 任何一个完成即可
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void orAnyOfAsync() throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步1执行完成");
            return "1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步2执行完成");
            return "2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步3执行完成");
            return "3";
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
        System.out.println("主线程执行");
        // 获取到的是最先完成任务的结果，其他任务并不会中断执行
        System.out.println("获取结果：" + anyOfFuture.get());
        Thread.sleep(2000);
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 异步-异常处理<br/>
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public static void exceptionallyAsync() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("运行时异常");
            }
            return "异步执行完成";
        }).handle((string, throwable) -> {
            if (Objects.isNull(throwable)) {
                return string;
            }
            throwable.printStackTrace();
            return "默认值";
        });
        System.out.println(future.get());
    }
}
