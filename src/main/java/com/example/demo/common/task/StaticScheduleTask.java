package com.example.demo.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 静态周期任务<br/>
 * - 项目启动开始执行<br/>
 * - 周期恒定<br/>
 */
@Slf4j
@Component
public class StaticScheduleTask {

    @Value("${schedule.task.static.enable}")
    private Boolean enable;

    @Scheduled(cron = "0/30 * * * * ?")
    private void execute() {
        if (!enable) {
            return;
        }
        log.info("#execute: 每隔30秒执行一次");
    }

}
