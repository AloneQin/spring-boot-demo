package com.example.demo.common.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetryTestServiceImpl extends RetryManager implements RetryTestService {

    /**
     * 重试机制测试
     * <p> 1.{@link Retryable}注解在本类的方法相互调用时无效，重试方法必须在其他类
     * <p> 2.{@link Retryable}注解修饰方法的调用对象手动创建时无效，必须被 Spring IOC 容器管理
     */
    @Override
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 500L), recover = "recoverMockCall")
    public void mockCall() {
        String tryKey = getTryKey("mockCall", 1L);
        initTryInfo(tryKey, 5);
        tryTimesAutoincrement(tryKey);
        Integer tryTimes = getTryTimes(tryKey);
        try {
            if (tryTimes < 6) {
                throw new RuntimeException("出错了");
            }
            log.info("#第{}次调用成功", tryTimes);
        } catch (Exception e) {
            log.error("#第{}次调用失败", tryTimes);
            throw new RetryException("调用失败");
        } finally {
            cleanTryTimes(tryKey);
        }
    }

    @Recover
    public void recoverMockCall(Exception e) {
        log.error("重试全部失败，我来兜底", e);
    }
}