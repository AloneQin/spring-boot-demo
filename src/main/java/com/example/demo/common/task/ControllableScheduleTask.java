package com.example.demo.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * 可控制周期任务<br/>
 * - 自由启停<br/>
 * - 周期可变
 */
@Slf4j
@Component
public class ControllableScheduleTask {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    public ControllableScheduleTask(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    public void startTask() {
        scheduledFuture = threadPoolTaskScheduler.schedule(() -> {
            log.info("#startTask: 每隔5秒执行一次");
        }, new CronTrigger("0/5 * * * * ?"));
    }

    public void stopTask() {
        if (Objects.nonNull(scheduledFuture)) {
            boolean result = scheduledFuture.cancel(true);
            log.info("#stopTask, result: {}", result);
        }
    }

}
