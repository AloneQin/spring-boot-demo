package com.example.demo.common.trace;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程池数
     */
    private final int CORE_POOL_SIZE = Math.max(8, Runtime.getRuntime().availableProcessors());

    /**
     * 最大线程池数
     */
    private final int MAX_POOL_SIZE = CORE_POOL_SIZE * 4;

    /**
     * 任务队列容量
     */
    private static final int QUEUE_CAPACITY = 0;

    /**
     * 非核心线程的存活时间
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setThreadNamePrefix("async-pool-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 链路追踪上下文传递
        executor.setTaskDecorator(new MDCTaskDecorator());
        executor.initialize();
        // 预热：提前创建核心线程
        executor.getThreadPoolExecutor().prestartAllCoreThreads();
        return executor;
    }

    /**
     * 高优先级缓存线程池 - 适应于优先级较高，注重任务性能的异步任务<br>
     * 特点：{@code 快}<br>
     * 1. 大容量 - 较大的核心线程数与最大线程数<br>
     * 2. 低延迟 - 不进入等待队列，立即执行任务<br>
     * 3. 长寿命 - 闲置线程存活时间长，避免频繁创建销毁<br>
     * 4. 全预热 - 提前创建所有核心线程，避免首次等待加载<br>
     * 5. 高保护 - 灵活的拒绝策略，避免任务丢失<br>
     * 使用场景举例：远程接口调用
     */
    @Bean
    public ThreadPoolTaskExecutor highPriorityCacheThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(ThreadPoolAttrEnum.HIGH_CACHE.corePoolSize);
        executor.setMaxPoolSize(ThreadPoolAttrEnum.HIGH_CACHE.maxPoolSize);
        executor.setQueueCapacity(ThreadPoolAttrEnum.HIGH_CACHE.queueCapacity);
        executor.setKeepAliveSeconds(ThreadPoolAttrEnum.HIGH_CACHE.keepAliveSeconds);
        executor.setThreadNamePrefix(ThreadPoolAttrEnum.HIGH_CACHE.threadNamePrefix);
        executor.setRejectedExecutionHandler(ThreadPoolAttrEnum.HIGH_CACHE.rejectedExecutionHandler);
        // 链路追踪上下文传递
        executor.setTaskDecorator(new MDCTaskDecorator());
        executor.initialize();
        // 预热：提前创建核心线程（全部创建）
        executor.getThreadPoolExecutor().prestartAllCoreThreads();
        return executor;
    }

    /**
     * 低优先级缓存线程池 - 适应于优先级较低，注重资源节省的异步任务<br>
     * 特点：{@code 省}<br>
     * 1. 小容量 - 较小的核心线程数与最大线程数<br>
     * 2. 低消耗 - 核心线程满载时，先进入等待队列<br>
     * 3. 短寿命 - 闲置线程存活时间短，节省资源<br>
     * 4. 单预热 - 提前创建一个核心线程，避免浪费<br>
     * 5. 轻保护 - 保守的拒绝策略，及时反馈错误<br>
     * 使用场景举例：日志清理
     */
    @Bean
    public ThreadPoolTaskExecutor lowPriorityFixedThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(ThreadPoolAttrEnum.LOW_FIXED.corePoolSize);
        executor.setMaxPoolSize(ThreadPoolAttrEnum.LOW_FIXED.maxPoolSize);
        executor.setQueueCapacity(ThreadPoolAttrEnum.LOW_FIXED.queueCapacity);
        executor.setKeepAliveSeconds(ThreadPoolAttrEnum.LOW_FIXED.keepAliveSeconds);
        executor.setThreadNamePrefix(ThreadPoolAttrEnum.LOW_FIXED.threadNamePrefix);
        executor.setRejectedExecutionHandler(ThreadPoolAttrEnum.LOW_FIXED.rejectedExecutionHandler);
        // 链路追踪上下文传递
        executor.setTaskDecorator(new MDCTaskDecorator());
        executor.initialize();
        // 预热：提前创建核心线程（只创建一个）
        executor.getThreadPoolExecutor().prestartCoreThread();
        return executor;
    }

    /**
     * 线程池属性枚举
     */
    @AllArgsConstructor
    enum ThreadPoolAttrEnum {

        /**
         * 高优先级缓存线程池
         */
        HIGH_CACHE(
                Math.max(8, Runtime.getRuntime().availableProcessors()),
                Math.max(8, Runtime.getRuntime().availableProcessors()) * 4,
                0,
                60,
                "hp-cache-pool-",
                // 满载时使用提交线程执行任务
                new ThreadPoolExecutor.CallerRunsPolicy()
        ),

        /**
         * 低优先级缓存线程池
         */
        LOW_FIXED(
                Math.min(4, Runtime.getRuntime().availableProcessors()),
                Math.min(4, Runtime.getRuntime().availableProcessors()) + 1,
                50,
                10,
                "lp-fixed-pool-",
                // 满载时抛出异常
                new ThreadPoolExecutor.AbortPolicy()
        );

        /**
         * 核心线程数
         */
        public final int corePoolSize;

        /**
         * 最大线程数
         */
        public final int maxPoolSize;

        /**
         * 任务队列容量
         */
        public final int queueCapacity;

        /**
         * 非核心线程的存活时间（单位：秒）
         */
        public final int keepAliveSeconds;

        /**
         * 线程名称前缀
         */
        public final String threadNamePrefix;

        /**
         * 线程池拒绝策略
         */
        public final RejectedExecutionHandler rejectedExecutionHandler;

    }
}
