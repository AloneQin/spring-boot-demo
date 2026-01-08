package com.example.demo.utils;

import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Cron表达式工具类
 */
public class CronUtils {

    /**
     * 验证Cron表达式是否有效
     *
     * @param cronExpression Cron表达式
     * @return 是否有效
     */
    public static boolean isValid(String cronExpression) {
        try {
            CronExpression.parse(cronExpression);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取下一个执行时间
     *
     * @param cronExpression Cron表达式
     * @param currentTime 当前时间
     * @return 下一次执行时间
     */
    public static LocalDateTime getNextExecutionTime(String cronExpression, LocalDateTime currentTime) {
        CronExpression cron = CronExpression.parse(cronExpression);
        return cron.next(currentTime);
    }

    /**
     * 获取未来几次的执行时间
     *
     * @param cronExpression Cron表达式
     * @param currentTime 当前时间
     * @param count 次数
     * @return 未来执行时间列表
     */
    public static List<LocalDateTime> getNextExecutionTimes(String cronExpression, LocalDateTime currentTime, int count) {
        List<LocalDateTime> times = new ArrayList<>();
        CronExpression cron = CronExpression.parse(cronExpression);
        LocalDateTime nextTime = currentTime;

        for (int i = 0; i < count; i++) {
            nextTime = cron.next(nextTime);
            if (nextTime == null) {
                break;
            }
            times.add(nextTime);
        }

        return times;
    }

    public static void main(String[] args) {
        String cron1 = "0 0 12 * * ?";
        System.out.println(isValid(cron1));
        System.out.println(getNextExecutionTime(cron1, LocalDateTime.now()));
        System.out.println(getNextExecutionTimes(cron1, LocalDateTime.now(), 5));

        String cron2 = "0 0 12 * * *";
        System.out.println(isValid(cron2));
        System.out.println(getNextExecutionTime(cron2, LocalDateTime.now()));
        System.out.println(getNextExecutionTimes(cron2, LocalDateTime.now(), 5));
    }
}
