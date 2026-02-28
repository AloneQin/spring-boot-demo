package com.example.demo.common.mq.kafka.producer;

import com.example.demo.common.mq.kafka.config.ClusterBKafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClusterBKafkaProducer extends AbstractKafkaProducer {

    private final KafkaTemplate<Object, Object> clusterAKafkaTemplate;

    private final ClusterBKafkaConfig clusterBKafkaConfig;

    public void send() {
        for (int i = 0; i < 10; i++) {
            super.send(clusterAKafkaTemplate, clusterBKafkaConfig.getTopicB1(), "hello kafka B " + i);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
