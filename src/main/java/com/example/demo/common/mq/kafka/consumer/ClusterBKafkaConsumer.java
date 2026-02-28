package com.example.demo.common.mq.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ClusterBKafkaConsumer extends AbstractBatchKafkaConsumer<String> {

    @KafkaListener(
            topics = "${spring.kafka.cluster-b.topic-b-1}",
            containerFactory = "clusterBKafkaListenerContainerFactory",
            groupId = "${spring.kafka.cluster-b.consumer.group-id}"
    )
    protected void listenTopicB1(List<ConsumerRecord<Object, Object>> records, Acknowledgment ack) {
        super.listenBatch(records, ack);
    }

    @Override
    protected void handleBatchMessage(List<String> list) {
        log.info("#handleBatchMessage, 收到消息 - {}", list);
    }
}
