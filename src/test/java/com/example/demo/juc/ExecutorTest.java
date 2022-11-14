package com.example.demo.juc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        // 简单使用
//        testSimple();

        // 有无返回值的线程使用
        testRunnableAndCallable();


    }

    private static void testSimple() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                // 核心线程数：当有任务来临时，线程池直接创建 corePoolSize 个线程
                2,
                // 最大线程数：核心线程数均被占用时，任务会被搁置在等待队列中，当等待队列满后，线程池会把线程数量创建至 maximumPoolSize
                4,
                // 最大存活时间：当大于核心线程数的线程在经过 keepAliveTime 后仍没有任务执行，则销毁线程
                1L,
                // 时间单位
                TimeUnit.SECONDS,
                // 任务队列：当核心线程数被占有时，任务被搁置在 workQueue
                new ArrayBlockingQueue<>(2),
                // 线程工厂：用来创建线程，保持默认即可
                Executors.defaultThreadFactory(),
                /*
                 * 饱和策略：当运行的线程达到最大线程数，同时任务队列被占满时，执行饱和策略
                 * ThreadPoolExecutor.AbortPolicy：抛出 RejectedExecutionException 异常拒绝新任务
                 * ThreadPoolExecutor.DiscardPolicy：直接丢弃新任务
                 * ThreadPoolExecutor.DiscardOldestPolicy：丢弃最早未处理的新任务
                 * ThreadPoolExecutor.CallerRunsPolicy：让提交任务的线程去执行新任务，此策略会降低新任务提交速度，影响线程池性能，但不会丢任务
                 */
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i = 0; i < 7; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolExecutor.shutdown();
        // 线程池已中断（销毁空闲线程，同时不再接收新任务）
        System.out.println(threadPoolExecutor.isShutdown());
        // 不会再执行
        threadPoolExecutor.execute(() -> System.out.println("我再跑一次"));
    }

    private static void testRunnableAndCallable() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                4,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // execute runnable 无返回值
        threadPoolExecutor.execute(() -> System.out.println("execute"));

        // submit runnable 无返回值
        Future<?> future1 = threadPoolExecutor.submit(() -> System.out.println("submit runnable1"));
        try {
            System.out.println("submit runnable1 result: " + future1.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // submit runnable 多参数 有返回值
        TestEntity testEntity = new TestEntity("submit runnable2");
        TestRunnable testRunnable = new TestRunnable(testEntity);
        Future<TestEntity> future2 = threadPoolExecutor.submit(testRunnable, testEntity);
        try {
            System.out.println("submit runnable2 result: " + future2.get().getName() + ", testEntity name: " + testEntity.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // submit callable 有返回值
        Future<Integer> future3 = threadPoolExecutor.submit(() -> {
            System.out.println("submit callable");
            return 1;
        });
        try {
            System.out.println("submit callable result: " + future3.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadPoolExecutor.shutdown();
    }

    private static void testThreadPool() {
        /*
         * 固定线程池
         *
         * 特征：
         * 1.核心线程数 corePoolSize = 最大线程数 maximumPoolSize
         * 2.等待队列容量为 Integer.MAX_VALUE
         *
         * 缺点：可近似认为等待队列无边界（无穷大），永远无法被占满，当短时间任务较多时，会堆积大量请求，可能出现 OOM
         *
         * 使用场景：任务量比较平稳的异步任务。
         *
         * 结论：存在安全隐患，不建议直接使用
         */
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        executorService1.shutdown();

        /*
         * 单线程线程池
         *
         * 特征：
         * 1.核心线程数 corePoolSize = 最大线程数 maximumPoolSize = 1
         * 2.等待队列容量为 Integer.MAX_VALUE
         *
         * 缺点：与 FixedThreadPool 相同
         *
         * 使用场景：任务量比较少的异步任务。
         *
         * 结论：存在安全隐患，不建议直接使用
         */
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.shutdown();

        /*
         * 缓存线程池
         *
         * 特征：
         * 1.核心线程数 corePoolSize = 0
         * 2.最大线程数 maximumPoolSize = Integer.MAX_VALUE
         * 3.等待队列为同步阻塞队列 SynchronousQueue，队列不存储数据，没有大小，只是线程间数据交换的媒介
         *
         * 缺点：SynchronousQueue 存在的作用只是为了为生产任务快速分派消费线程。当新的任务到来时，
         * 要么创建新的线程，要么从生存时长 60 秒的空闲线程里获取已有线程，以此来快速匹配消费者。
         * 当短时间任务较多时，因最大线程数近似无界（无穷大），线程池会不停创建新线程，可能造成服务器线程资源耗尽进而 OOM
         *
         * 使用场景：任务量起伏波动较大的异步任务。
         *
         * 结论：存在安全隐患，不建议直接使用
         */
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        executorService3.shutdown();

        /*
         * 周期性线程池
         *
         * 特征：
         * 1.最大线程数 maximumPoolSize = Integer.MAX_VALUE
         * 2.等待队列为延迟队列 DelayedWorkQueue，队列为数组实现的堆结构，有序并支持相对时间比较，初始容量为 16，支持扩容
         *
         * 缺点：DelayedWorkQueue 能持续扩容，容量近似无界（无穷大），永远无法被占满，当短时间任务较多时，会堆积大量请求，可能出现 OOM
         *
         * 使用场景：周期性异步任务。
         *
         * 结论：存在安全隐患，不建议直接使用
         */
        ExecutorService executorService4 = Executors.newScheduledThreadPool(10);
        executorService4.shutdown();
    }

    private static void testForkJoinPool() {
        /*
         * 工作窃取线程池
         *
         * 特征：
         * 1.使用 ForkJoinPool 实现，与 ThreadPoolExecutor 同级
         * 2.由并行度 parallelism 决定工作线程数量，一般为 CPU 核数
         * 3.每个工作线程都有独立的任务队列，当自己的队列中没有任务时，会从别的队列里窃取任务执行
         *
         * 使用场景：任务量大线程较少，阻塞时长比较少的异步任务。
         */
        ExecutorService executorService5 = Executors.newWorkStealingPool();
        executorService5.shutdown();
    }

    @AllArgsConstructor
    static class TestRunnable implements Runnable {
        private TestEntity testEntity;

        @Override
        public void run() {
            System.out.println(testEntity.getName());
            testEntity.setName("new name");
        }
    }

    @Data
    @AllArgsConstructor
    static class TestEntity {
        private String name;
    }
}
