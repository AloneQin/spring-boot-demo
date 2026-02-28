package com.example.demo.common.mq.kafka.producer;

import com.example.demo.common.mq.kafka.config.ClusterAKafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClusterAKafkaProducer extends AbstractKafkaProducer {

    private final KafkaTemplate<Object, Object> clusterAKafkaTemplate;

    private final ClusterAKafkaConfig clusterAKafkaConfig;

    public void send() {
        for (int i = 0; i < 10; i++) {
            super.send(clusterAKafkaTemplate, clusterAKafkaConfig.getTopicA1(), "hello kafka A " + i);
            log.info("发送消息: {}", i);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
