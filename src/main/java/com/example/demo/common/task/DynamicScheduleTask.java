package com.example.demo.common.task;

import com.example.demo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 动态周期任务<br/>
 * - 项目启动开始执行<br/>
 * - 周期根据触发器进行触发进行改变<br/>
 * - 触发器先执行，周期任务后执行<br/>
 */
@Slf4j
@Component
public class DynamicScheduleTask implements SchedulingConfigurer {

    @Value("${schedule.task.dynamic.enable}")
    private Boolean enable;

    private Integer currIndex = -1;

    private List<Map<Integer, String>> cronList = Arrays.asList(
            Map.ofEntries(Map.entry(1, "0/1 * * * * ?")),
            Map.ofEntries(Map.entry(2, "0/2 * * * * ?")),
            Map.ofEntries(Map.entry(3, "0/3 * * * * ?"))
    );

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        if (!enable) {
            return;
        }
        scheduledTaskRegistrar.addTriggerTask(() -> {
            log.info("#configureTasks, 执行任务, 当前时间: {}", DateUtils.date2Str(new Date()));
        }, (triggerContext) -> {
            Map.Entry<Integer, String> cronEntry = getCron()
                    .entrySet()
                    .stream()
                    .findFirst()
                    .get();
            log.info("#configureTasks, 触发器执行, 下一次时间间隔{}秒, cron: {}", cronEntry.getKey(), cronEntry.getValue());
            return new CronTrigger(cronEntry.getValue())
                    .nextExecutionTime(triggerContext);
        });
    }

    private Map<Integer, String> getCron() {
        currIndex += 1;
        currIndex = currIndex % cronList.size();
        return cronList.get(currIndex);
    }
}
