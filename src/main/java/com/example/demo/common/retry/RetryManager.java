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
     * 尝试信息集合
     */
    private final static Map<String, TryInfo> TRY_INFO_MAP = new HashMap();

    /**
     * 获取尝试 key
     * @param type 重试类型
     * @param id 唯一标识
     * @return 重试 key
     */
    protected String getTryKey(String type, Long id) {
        return type + "_" + id;
    }

    /**
     * 初始化尝试信息
     * @param tryKey 尝试 key
     */
    protected void initTryInfo(String tryKey, Integer maxTryTimes) {
        TryInfo tryInfo = TRY_INFO_MAP.get(tryKey);
        if (Objects.isNull(tryInfo)) {
            tryInfo = new TryInfo(0, maxTryTimes);
            TRY_INFO_MAP.put(tryKey, tryInfo);
            log.info("initTryInfo, key: {}, value: {}", tryKey, FastjsonUtils.toString(tryInfo));
        }
    }

    /**
     * 自增尝试次数
     * @param tryKey 尝试 key
     */
    protected synchronized void tryTimesAutoincrement(String tryKey) {
        TryInfo tryInfo = TRY_INFO_MAP.get(tryKey);
        if (Objects.nonNull(tryInfo)) {
            tryInfo.setTryTimes(tryInfo.getTryTimes() + 1);
            TRY_INFO_MAP.put(tryKey, tryInfo);
            log.info("tryTimesAutoincrement, key: {}, value: {}", tryKey, FastjsonUtils.toString(tryInfo));
        }
    }

    /**
     * 获取尝试信息
     * @param tryKey 尝试 key
     * @return 尝试信息
     */
    protected TryInfo getTryInfo(String tryKey) {
        return TRY_INFO_MAP.get(tryKey);
    }

    /**
     * 获取尝试次数
     * @param tryKey 尝试 key
     * @return 当前尝试次数
     */
    protected Integer getTryTimes(String tryKey) {
        TryInfo tryInfo = getTryInfo(tryKey);
        return Objects.isNull(tryInfo) ? 0 : tryInfo.getTryTimes();
    }

    /**
     * 清除尝试次数
     * @param tryKey 尝试 key
     */
    protected void cleanTryTimes(String tryKey) {
        TryInfo tryInfo = TRY_INFO_MAP.get(tryKey);
        if (Objects.nonNull(tryInfo) && tryInfo.isToLimit()) {
            cleanTryInfo(tryKey);
        }
    }

    /**
     * 清除尝试信息
     * @param tryKey 尝试 key
     */
    private void cleanTryInfo(String tryKey) {
        TryInfo tryInfo = TRY_INFO_MAP.get(tryKey);
        if (Objects.nonNull(tryInfo)) {
            TRY_INFO_MAP.remove(tryKey);
            log.info("cleanTryInfo, key: {}", tryKey);
        }
    }

    /**
     * 尝试信息
     */
    @Data
    @AllArgsConstructor
    class TryInfo {

        /**
         * 尝试次数
         */
        private Integer tryTimes;

        /**
         * 最大尝试次数
         */
        private Integer maxTryTimes;

        /**
         * 是否达到尝试上限
         * @return
         */
        public boolean isToLimit() {
            return tryTimes >= maxTryTimes;
        }
    }
}
