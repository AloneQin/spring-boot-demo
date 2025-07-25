package com.example.demo.common.annotation;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 用户内存缓存管理者
 */
@Slf4j
public class UserMemoryCacheManager {

    /**
     * 缓存项的过期时间（秒）
     */
    private static final long CACHE_EXPIRATION_TIME = 600L;

    /**
     * 缓存数据和过期时间的容器
     */
    private static final ConcurrentHashMap<String, CacheEntry> cacheMap = new ConcurrentHashMap<>();

    /**
     * 定时清理过期缓存的线程池
     */
    private static final ScheduledExecutorService cleanupExecutor = Executors.newSingleThreadScheduledExecutor();

    /**
     * 私有构造函数
     */
    private UserMemoryCacheManager() {
        // 初始化 600 秒后开始执行清理任务，每 600 秒执行一次
        cleanupExecutor.scheduleAtFixedRate(this::cleanup, CACHE_EXPIRATION_TIME, CACHE_EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    /**
     * 获取单例实例
     */
    public static UserMemoryCacheManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取缓存项
     * @param key 缓存键
     * @return 缓存值，不存在或已过期则返回<code>null</>
     */
    public String get(String key) {
        CacheEntry entry = cacheMap.get(key);
        if (entry == null) {
            return null;
        }
        if (entry.isExpired()) {
            cacheMap.remove(key);
            return null;
        }
        return entry.getValue();
    }

    /**
     * 添加缓存项
     * @param key 缓存键
     * @param value 缓存值
     */
    public void put(String key, String value) {
        cacheMap.put(key, new CacheEntry(value, CACHE_EXPIRATION_TIME * 1000));
    }

    /**
     * 清理过期缓存项
     */
    private void cleanup() {
        int size1 = cacheMap.size();
        cacheMap.entrySet().removeIf(entry -> entry.getValue().isExpired());
        int size2 = cacheMap.size();
        log.info("#cleanup, before total: {}, after total: {}, delete num: {}", size1, size2, size1 - size2);
    }

    /**
     * 关闭清理任务，释放资源
     */
    @PreDestroy
    public void shutdown() {
        cleanupExecutor.shutdown();
        try {
            if (!cleanupExecutor.awaitTermination(3, TimeUnit.SECONDS)) {
                cleanupExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            cleanupExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 缓存项类
     */
    private static class CacheEntry {

        @Getter
        private final String value;

        private final long expirationTime;

        public CacheEntry(String value, long ttlMillis) {
            this.value = value;
            this.expirationTime = System.currentTimeMillis() + ttlMillis;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }

    /**
     * 静态内部类单例实现
     */
    private static class SingletonHolder {
        private static final UserMemoryCacheManager INSTANCE = new UserMemoryCacheManager();
    }
}
