package com.example.demo.common.mq.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.HashMap;
import java.util.Map;

public interface AbstractKafkaConfig {

    default Map<String, Object> getAdminConfigs(KafkaProperties kafkaProperties) {
        return new HashMap<>(kafkaProperties.buildAdminProperties());
    }

    default Map<String, Object> getProducerConfigs(KafkaProperties kafkaProperties) {
        return new HashMap<>(kafkaProperties.buildProducerProperties());
    }

    default Map<String, Object> getConsumerConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> configs = kafkaProperties.buildConsumerProperties();

        // 关键配置：增强连接稳定性和减少协调器压力
        configs.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 2000);          // 重试间隔：2秒
        configs.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 1000);      // 重连基础间隔：1秒
        configs.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 10000); // 最大重连间隔：10秒
        configs.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 5000);     // 心跳间隔：5秒
        configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);       // 会话超时：60秒
        configs.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);    // 最大轮询间隔：5分钟
        configs.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, 30000);         // 元数据刷新间隔：30秒

        configs.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);              // broker 返回数据的小字节数：1 字节
        configs.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 1024 * 1024);    // broker 返回数据的大字节数：1MB
        configs.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);          // broker 等待数据的最大时间：500 ms
        configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);           // 单次拉取数据的最大条数：100 条

        return new HashMap<>(configs);
    }

}
