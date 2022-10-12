package com.example.demo.common.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetryTestServiceImpl extends RetryManager implements RetryTestService {

    /**
     * 重试机制测试
     * 1.{@link Retryable}注解在本类的方法相互调用时无效，重试方法必须在其他类
     * 2.{@link Retryable}注解修饰方法的调用对象手动创建时无效，必须被 Spring IOC 容器管理
     * @return
     */
    @Override
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 500L))
    public void mockCall() {
        String tryKey = getTryKey("mockCall", 1L);
        initTryInfo(tryKey, 5);
        tryTimesAutoincrement(tryKey);
        Integer tryTimes = getTryTimes(tryKey);
        try {
            if (tryTimes < 5) {
                throw new NullPointerException();
            }
            log.info("#第{}次调用成功", tryTimes);
        } catch (Exception e) {
            log.error("#第{}次调用失败", tryTimes);
            throw new RetryException("调用失败");
        } finally {
            cleanTryTimes(tryKey);
        }
    }
}