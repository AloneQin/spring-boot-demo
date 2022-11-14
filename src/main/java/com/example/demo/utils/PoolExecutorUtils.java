package com.example.demo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 */
public class PoolExecutorUtils {

    /**
     * 默认的线程工厂
     */
    public static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();

    /**
     * 默认的饱和策略：抛出 {@link RejectedExecutionException} 异常拒绝新任务
     */
    public static final RejectedExecutionHandler DEFAULT_HANDLER = new ThreadPoolExecutor.AbortPolicy();

    /**
     * 创建一个单线程的线程池
     *
     * <p>对 {@link Executors#newSingleThreadExecutor()} 的优化，增加等待队列容量参数，避免等待队列无边界。
     *
     * @param queueCapacity 等待队列容量
     * @return 线程执行者服务
     */
    public static ExecutorService newSingleThreadExecutor(int queueCapacity) {
        return newSingleThreadExecutor(queueCapacity, DEFAULT_THREAD_FACTORY, DEFAULT_HANDLER);
    }

    /**
     * 创建一个单线程的线程池
     *
     * <p>对 {@link Executors#newSingleThreadExecutor(ThreadFactory)} 的优化，增加如下参数：
     * <ul>
     *     <li>等待队列容量，避免等待队列无边界</li>
     *     <li>饱和策略，可按需设置策略</li>
     * </ul>
     *
     * @param queueCapacity 等待队列容量
     * @param threadFactory 线程工厂
     * @param handler 饱和策略
     * @return 线程执行者服务
     */
    public static ExecutorService newSingleThreadExecutor(int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity), threadFactory, handler);
    }

    /**
     * 创建一个固定大小的线程池
     *
     * <p>对 {@link Executors#newFixedThreadPool(int)} 的优化，增加等待队列容量参数，避免等待队列无边界。
     *
     * @param poolSize 线程池大小
     * @param queueCapacity 等待队列容量
     * @return 线程执行者服务
     */
    public static ExecutorService newFixedThreadPool(int poolSize, int queueCapacity) {
        return newFixedThreadPool(poolSize, queueCapacity, DEFAULT_THREAD_FACTORY, DEFAULT_HANDLER);
    }

    /**
     * 创建一个固定大小的线程池
     *
     * <p>对 {@link Executors#newFixedThreadPool(int, ThreadFactory)} 的优化，增加如下参数：
     * <ul>
     *     <li>等待队列容量，避免等待队列无边界</li>
     *     <li>饱和策略，可按需设置策略</li>
     * </ul>
     *
     * @param poolSize 线程池大小
     * @param queueCapacity 等待队列容量
     * @param threadFactory 线程工厂
     * @param handler 饱和策略
     * @return 线程执行者服务
     */
    public static ExecutorService newFixedThreadPool(int poolSize, int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity), threadFactory, handler);
    }

    /**
     * 创建一个缓存线程池
     *
     * <p>对 {@link Executors#newCachedThreadPool()} 的优化，增加最大线程数参数，避免线程资源耗尽。
     *
     * @param maximumPoolSize 最大线程数
     * @return 线程执行者服务
     */
    public static ExecutorService newCachedThreadPool(int maximumPoolSize) {
        return newCachedThreadPool(maximumPoolSize, DEFAULT_THREAD_FACTORY, DEFAULT_HANDLER);
    }

    /**
     * 创建一个缓存线程池
     *
     * <p>对 {@link Executors#newCachedThreadPool(ThreadFactory)} 的优化，增加如下参数：
     * <ul>
     *     <li>最大线程数，避免线程资源耗尽</li>
     *     <li>饱和策略，可按需设置策略</li>
     * </ul>
     *
     * @param maximumPoolSize 最大线程数
     * @param threadFactory 线程工厂
     * @param handler 饱和策略
     * @return 线程执行者服务
     */
    public static ExecutorService newCachedThreadPool(int maximumPoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(0, maximumPoolSize,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), threadFactory, handler);
    }

    /**
     * 创建一个周期调度线程池
     *
     * <p>参考 {@link Executors#newScheduledThreadPool(int)} 实现
     *
     * @deprecated 因等待列队支持扩容，近似无边界，可能会造成 OOM，慎用！
     *
     * @param corePoolSize 核心线程数
     * @return 周期调度线程执行者服务
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return Executors.newScheduledThreadPool(corePoolSize, DEFAULT_THREAD_FACTORY);
    }

    /**
     * 创建一个周期调度线程池
     *
     * <p>参考 {@link Executors#newScheduledThreadPool(int, ThreadFactory)} 实现
     *
     * @deprecated 因等待列队支持扩容，近似无边界，可能会造成 OOM，慎用！
     *
     * @param corePoolSize 核心线程数
     * @param threadFactory 线程工厂
     * @return 周期调度线程执行者服务
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        return Executors.newScheduledThreadPool(corePoolSize, threadFactory);
    }

    public static void main(String[] args) {
        newSingleThreadExecutor(1);
        newSingleThreadExecutor(1, DEFAULT_THREAD_FACTORY, DEFAULT_HANDLER);
    }
}
