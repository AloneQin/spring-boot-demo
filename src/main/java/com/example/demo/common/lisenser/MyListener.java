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

    @EventListener
    @Async("threadPoolTaskExecutor")
    public void MyEventListener(MyEvent myEvent) {
        log.info("#MyEventListener, input: {}, hash: {}", myEvent.getStr(), myEvent.getStr().hashCode());
        myEvent.setStr("new str");
    }

    @EventListener
    @Async("threadPoolTaskExecutor")
    public void MyEventListener2(MyEvent myEvent) {
        try {
            Thread.sleep(1000L);
            log.info("#MyEventListener2, input: {}, hash: {}", myEvent.getStr(), myEvent.getStr().hashCode());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
