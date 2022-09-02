package com.example.demo.common.retry;

import com.example.demo.utils.FastjsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 重试管理类
 * {@link Retryable}注解在本类的方法相互调用时无效，重试方法必须在其他类
 * {@link Retryable}注解修饰方法的调用对象手动创建时无效，必须被 Spring IOC 容器管理
 */
@Slf4j
public class RetryManager {

    /**
     * 重试信息集合
     */
    private final static Map<String, RetryInfo> RETRY_INFO_MAP = new HashMap();

    /**
     * 获取重试 key
     * @param type 重试类型
     * @param id 唯一标识
     * @return 重试 key
     */
    protected String getRetryKey(String type, Long id) {
        return type + "_" + id;
    }

    /**
     * 初始化重试信息
     * @param retryKey 重试 key
     */
    protected void initRetryInfo(String retryKey, Integer maxRetryTimes) {
        RetryInfo retryInfo = RETRY_INFO_MAP.get(retryKey);
        if (Objects.isNull(retryInfo)) {
            retryInfo = new RetryInfo(0, maxRetryTimes);
            RETRY_INFO_MAP.put(retryKey, retryInfo);
            log.info("initRetryInfo, key: {}, value: {}", retryKey, FastjsonUtils.toString(retryInfo));
        }
    }

    /**
     * 自增重试次数
     * @param retryKey 重试 key
     */
    protected synchronized void retryTimesAutoincrement(String retryKey) {
        RetryInfo retryInfo = RETRY_INFO_MAP.get(retryKey);
        if (Objects.nonNull(retryInfo)) {
            retryInfo.setRetryTimes(retryInfo.getRetryTimes() + 1);
            RETRY_INFO_MAP.put(retryKey, retryInfo);
            log.info("retryTimesAutoincrement, key: {}, value: {}", retryKey, FastjsonUtils.toString(retryInfo));
        }
    }

    /**
     * 获取重试信息
     * @param retryKey 重试 key
     * @return 重试信息
     */
    protected RetryInfo getRetryInfo(String retryKey) {
        return RETRY_INFO_MAP.get(retryKey);
    }

    /**
     * 获取重试次数
     * @param retryKey 重试 key
     * @return 当前重试次数
     */
    protected Integer getRetryTimes(String retryKey) {
        RetryInfo retryInfo = getRetryInfo(retryKey);
        return Objects.isNull(retryInfo) ? 0 : retryInfo.getRetryTimes();
    }

    /**
     * 清除重试次数
     * @param retryKey 重试 key
     */
    protected void cleanRetryTimes(String retryKey) {
        RetryInfo retryInfo = RETRY_INFO_MAP.get(retryKey);
        if (Objects.nonNull(retryInfo) && retryInfo.isToLimit()) {
            cleanRetryInfo(retryKey);
        }
    }

    /**
     * 清除重试信息
     * @param retryKey 重试 key
     */
    private void cleanRetryInfo(String retryKey) {
        RetryInfo retryInfo = RETRY_INFO_MAP.get(retryKey);
        if (Objects.nonNull(retryInfo)) {
            RETRY_INFO_MAP.remove(retryKey);
            log.info("cleanRetryInfo, key: {}", retryKey);
        }
    }

    /**
     * 重试信息
     */
    @Data
    @AllArgsConstructor
    class RetryInfo {

        /**
         * 重试次数
         */
        private Integer retryTimes;

        /**
         * 最大重试次数
         */
        private Integer maxRetryTimes;

        /**
         * 是否达到重试上限
         * @return
         */
        public boolean isToLimit() {
            return retryTimes >= maxRetryTimes;
        }
    }
}
