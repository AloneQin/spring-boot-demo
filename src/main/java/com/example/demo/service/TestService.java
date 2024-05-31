package com.example.demo.service;

import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.common.trace.TraceManager;
import com.example.demo.utils.AssertUtils;
import com.example.demo.utils.RandomUtils;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Slf4j
@Service
public class TestService {

    public void checkName(String name) {
        AssertUtils.isTrue(StringUtils.isBlank(name), ReturnCodeEnum.NAME_ILLEGAL,
                () -> log.warn("the name illegal, name: {}", name)
        );
    }

    public void testStopWatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("testStopWatch_" + TraceManager.getTraceId());

        stopWatch.start("task1");
        log.info("taskName: {}", stopWatch.currentTaskName());
        Thread.sleep(1000L);
        stopWatch.stop();
        log.info("taskName: {}, taskTime: {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());

        stopWatch.start("task2");
        log.info("taskName: {}", stopWatch.currentTaskName());
        Thread.sleep(1500L);
        stopWatch.stop();
        log.info("taskName: {}, taskTime: {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());

        log.info("totalTime: {}ms", stopWatch.getTotalTimeMillis());
        log.info("simple summary: {}", stopWatch.shortSummary());
        // 输出时间单位：纳秒，1 ms = 1000,000 ns
        log.info("pretty summary:\n{}", stopWatch.prettyPrint());
    }
}
