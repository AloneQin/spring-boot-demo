package com.example.demo.common.lisenser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 全局事件监听器
 */
@Slf4j
@Component
public class ApplicationEventListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("#onApplicationEvent, listen event: [{}]", event.getClass().getName());
        if (event instanceof ApplicationStartedEvent) {
            log.info("#onApplicationEvent, application init completed.");
        }
    }
}
