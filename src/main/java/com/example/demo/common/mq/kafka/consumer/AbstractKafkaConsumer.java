package com.example.demo.common.mq.kafka.consumer;

import org.springframework.kafka.support.Acknowledgment;

/**
 * 消费者抽象类
 */
public abstract class AbstractKafkaConsumer {

    protected void ack(Acknowledgment ack) {
        ack.acknowledge();
    }

}
