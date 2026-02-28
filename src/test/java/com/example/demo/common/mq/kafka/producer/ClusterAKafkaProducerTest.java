package com.example.demo.common.mq.kafka.producer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ClusterAKafkaProducerTest {

    @Resource
    ClusterAKafkaProducer clusterAKafkaProducer;

    @Resource
    ClusterBKafkaProducer clusterBKafkaProducer;

    @Test
    void sendA() {
        clusterAKafkaProducer.send();
    }

    @Test
    void sendB() {
        clusterBKafkaProducer.send();
    }
}