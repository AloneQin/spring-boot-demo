package com.example.demo.common.mq.kafka.config;

import com.example.demo.utils.Fastjson2Utils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.Map;

@Data
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ClusterAKafkaConfig implements AbstractKafkaConfig {

    private static final String CLUSTER_NAME = "cluster-a";

    private static final String KAFKA_CONFIG_PREFIX = "${spring.kafka." + CLUSTER_NAME + ".";

    private final KafkaProperties kafkaProperties;

    @Value(KAFKA_CONFIG_PREFIX + "bootstrap-servers}")
    private String bootstrapServers;

    @Value(KAFKA_CONFIG_PREFIX + "topic-a-1}")
    private String topicA1;

    @Value(KAFKA_CONFIG_PREFIX + "topic-a-2}")
    private String topicA2;

    /**
     * Cluster A 专用的 KafkaTemplate，用于发送消息
     */
    @Bean
    public KafkaTemplate<Object, Object> clusterAKafkaTemplate() {
        Map<String, Object> configs = getProducerConfigs(kafkaProperties);
        // 使用自定义配置覆盖通用配置
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        ProducerFactory<Object, Object> producerFactory = new DefaultKafkaProducerFactory<>(configs);
        log.info("#clusterAKafkaTemplate, Kafka集群生产者初始化完成, 名称: {}\n{}", CLUSTER_NAME, Fastjson2Utils.toStringFormat(configs));
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Cluster A 专用的 ConcurrentKafkaListenerContainerFactory，用于创建和管理消费者容器
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> clusterAKafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        Map<String, Object> configs = getConsumerConfigs(kafkaProperties);
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        DefaultKafkaConsumerFactory<Object, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(configs);
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory);
        // 批量监听：关闭
        factory.setBatchListener(false);
        log.info("#clusterAKafkaListenerContainerFactory, Kafka集群消费者初始化完成, 名称: {}\nconsumer:\n{}\nlistener:\n{}", CLUSTER_NAME, Fastjson2Utils.toStringFormat(configs), Fastjson2Utils.toStringFormat(factory.getContainerProperties()));
        return factory;
    }

    /**
     * Cluster A 专用的 KafkaAdmin，用于创建 topic
     */
    @Bean
    public KafkaAdmin clusterAKafkaAdmin() {
        Map<String, Object> configs = getAdminConfigs(kafkaProperties);
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        KafkaAdmin kafkaAdmin = new KafkaAdmin(configs);
        kafkaAdmin.setAutoCreate(true);
        kafkaAdmin.createOrModifyTopics(newTopics());
        return new KafkaAdmin(configs);
    }

    public NewTopic[] newTopics() {
        return new NewTopic[]{
                TopicBuilder.name(topicA1)
                        .partitions(1)
                        .replicas(1)
                        .build(),
                TopicBuilder.name(topicA2)
                        .partitions(1)
                        .replicas(1)
                        .build()
        };
    }
}
