package com.example.demo.dao.mq;

import com.example.demo.common.mq.rabbit.RabbitProducer;
import com.example.demo.utils.FastjsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Map;

@SpringBootTest
class RabbitProducerTest {

    @Resource
    private RabbitProducer rabbitProducer;

    @Test
    void send() {
        Map<String, String> map = Map.of("a", "a");
        rabbitProducer.send(FastjsonUtils.toString(map));
    }
}