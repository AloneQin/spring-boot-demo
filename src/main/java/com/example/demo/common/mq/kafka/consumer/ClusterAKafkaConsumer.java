package com.example.demo.common.mq.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClusterAKafkaConsumer extends AbstractSingleKafkaConsumer<String> {

    @KafkaListener(
            topics = "${spring.kafka.cluster-a.topic-a-1}",
            containerFactory = "clusterAKafkaListenerContainerFactory",
            groupId = "${spring.kafka.cluster-a.consumer.group-id}"
    )
    protected void listenTopicA1(ConsumerRecord<Object, Object> record, Acknowledgment ack) {
        super.listenSingle(record, ack);
    }

    @KafkaListener(
            topics = "${spring.kafka.cluster-a.topic-a-2}",
            containerFactory = "clusterAKafkaListenerContainerFactory",
            groupId = "${spring.kafka.cluster-a.consumer.group-id}"
    )
    protected void listenTopicA2(ConsumerRecord<Object, Object> record, Acknowledgment ack) {
        super.listenSingle(record, ack);
    }

    @Override
    protected void handleSingleMessage(String s) {
        log.info("#handleSingleMessage, 收到消息 - {}", s);
    }

}
