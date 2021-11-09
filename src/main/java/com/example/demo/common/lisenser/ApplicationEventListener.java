package com.example.demo.common.lisenser;

import com.example.demo.dao.PhoneMapper;
import com.example.demo.domain.entity.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全局事件监听器
 */
@Slf4j
@Component
public class ApplicationEventListener implements ApplicationListener {

    @Autowired
    PhoneMapper phoneMapper;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("##onApplicationEvent, listen event: [{}]", event.getClass().getName());
        if (event instanceof ApplicationStartedEvent) {
            List<Phone> byCondition = phoneMapper.findByCondition(null, null, null);
            System.out.println(byCondition.size());
        }
    }
}
