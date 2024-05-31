package com.example.demo.common.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetryTestService2 {

    /**
     * 模拟调用
     * 问题：{@link Retryable} 不使用 recover 属性指定兜底方法 {@link Recover} 能正常生效，指定 recover 属性后，兜底方法不执行了？
     * 结论：尽量减少使用 recover 属性，改而用异常类型区分
     */
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 100L))
    public void mockCall(int num) {
        log.info("#我被第{}次调用了，num: {}", RetrySynchronizationManager.getContext().getRetryCount() + 1, num);
        if (num < 10) {
            throw new NumberFormatException("<10");
        } else if (num < 20) {
            throw new NullPointerException("<20");
        } else {
            throw new RuntimeException("other");
        }
    }

    @Recover
    public void recoverMockCall1(NumberFormatException e, int num) {
        log.error("<10 重试全部失败，我来兜底, num: {}", num, e);
    }

    @Recover
    public void recoverMockCall2(NullPointerException e, int num) {
        log.error("<20 重试全部失败，我来兜底, num: {}", num, e);
    }

    @Recover
    public void recoverMockCall3(Exception e, int num) {
        log.error("other 重试全部失败，我来兜底, num: {}", num, e);
    }
}
