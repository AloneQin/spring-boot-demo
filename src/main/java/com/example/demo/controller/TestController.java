package com.example.demo.controller;

import com.example.demo.common.lisenser.event.MyEvent;
import com.example.demo.common.retry.RetryTestService;
import com.example.demo.common.task.ControllableScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 * 测试控制器
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ControllableScheduleTask controllableScheduleTask;

    @Autowired
    private RetryTestService retryTestService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/index")
    public String index() {
        log.info("#index, welcome...");
        return "index";
    }

    @GetMapping("/index2")
    public Long index2() {
        log.info("#index, welcome...");
        return 10L;
    }

    /**
     * RequestWrapper 测试
     * @param request
     * @return
     */
    @PostMapping("/testRequestWrapper")
    public String testRequestWrapper(HttpServletRequest request) {
        // 获取本来就有的请求头
        String name = request.getHeader("name");
        log.info("#testRequestWrapper, header name: {}", name);

        // 获取添加的请求头
        Enumeration<String> from = request.getHeaders("from");
        ArrayList<String> fromList = Collections.list(from);
        log.info("#testRequestWrapper, header from: {}", fromList.toString());

        // 获取本来就传的参数
        String param1 = request.getParameter("param1");
        log.info("#testRequestWrapper, parameter param1: {}", param1);

        // 获取添加的参数
        String[] param2Arr = request.getParameterValues("param2");
        log.info("#testRequestWrapper, parameter param2: {}", Arrays.asList(param2Arr).toString());

        return "SUCCESS";
    }

    /**
     * 事件驱动测试
     * @param input 输入字符串
     * @return
     */
    @GetMapping("/testEventDriven")
    public String testEventDriven(String input) {
        log.info("#testEventDriven, input: {}, hash: {}", input, input.hashCode());
        publisher.publishEvent(new MyEvent(this, input));
        log.info("#testEventDriven, publish done.");

        return "SUCCESS";
    }

    /**
     * 重试机制测试
     */
    @GetMapping("/testRetry")
    public String testRetry() {
        log.info("#开始发起远程调用...");
        retryTestService.mockCall();
        log.info("#完成远程调用");

        return "SUCCESS";
    }

    /**
     * 开启定时任务
     */
    @GetMapping("/startTask")
    public void startTask() {
        controllableScheduleTask.startTask();
    }

    /**
     * 关闭定时任务
     */
    @GetMapping("/stopTask")
    public void stopTask() {
        controllableScheduleTask.stopTask();
    }

    /**
     * 跟踪标记测试
     */
    @GetMapping("/testTraceId")
    @Async("threadPoolTaskExecutor")
    public void testTraceId() {
        log.info("#test async thread 1");
        threadPoolTaskExecutor.execute(() -> {
            log.info("#test async thread 2");
        });
    }
}
