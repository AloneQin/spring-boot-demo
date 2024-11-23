package com.example.demo.common.lisenser;

import com.example.demo.common.lisenser.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 自定义监听器
 */
@Slf4j
@Component
public class MyListener {

    /**
     * 事件默认为同步
     */
    @EventListener
    public void MyEventListener(MyEvent myEvent) {
        log.info("#MyEventListener, input: {}, hash: {}", myEvent.getStr(), myEvent.getStr().hashCode());
        myEvent.setStr("1");
    }

    /**
     * 参数会在多个事件监听中传递
     */
    @EventListener
    public void MyEventListener2(MyEvent myEvent) throws InterruptedException {
        Thread.sleep(500);
        log.info("#MyEventListener2, input: {}, hash: {}", myEvent.getStr(), myEvent.getStr().hashCode());
        myEvent.setStr("2");
    }

    /**
     * 同时也支持异步事件
     */
    @EventListener
    @Async("threadPoolTaskExecutor")
    public void MyEventListener3(MyEvent myEvent) throws InterruptedException {
        Thread.sleep(2000L);
        log.info("#MyEventListener3, input: {}, hash: {}", myEvent.getStr(), myEvent.getStr().hashCode());
    }
}
