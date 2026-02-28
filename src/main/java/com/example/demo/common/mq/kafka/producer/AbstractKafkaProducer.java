package com.example.demo.common.mq.kafka.producer;

import com.example.demo.common.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

@Slf4j
public abstract class AbstractKafkaProducer {

    protected void send(KafkaTemplate<Object, Object> kafkaTemplate, String topic, Object data) {
        send(kafkaTemplate, topic, null, data);
    }

    protected void send(KafkaTemplate<Object, Object> kafkaTemplate, String topic, Object key, Object data) {
        setTraceId();
        kafkaTemplate.send(topic, key, data);
    }

    /**
     * 发送同步消息
     * @param kafkaTemplate kafka模板
     * @param topic 主题
     * @param message 消息内容
     * @return 消息元数据（包含分区、偏移量等信息）
     */
    protected RecordMetadata sendSync(KafkaTemplate<Object, Object> kafkaTemplate, String topic, Object message) {
        return sendSync(kafkaTemplate, topic, null, message);
    }

    /**
     * 发送同步消息
     * @param kafkaTemplate kafka模板
     * @param topic 主题
     * @param key 键
     * @param message 消息内容
     * @return 消息元数据（包含分区、偏移量等信息）
     */
    protected RecordMetadata sendSync(KafkaTemplate<Object, Object> kafkaTemplate, String topic, Object key, Object message) {
        try {
            setTraceId();
            SendResult<Object, Object> sendResult = kafkaTemplate.send(topic, key, message).get();
            RecordMetadata metadata = sendResult.getRecordMetadata();
            log.info("#sendSync, 同步消息发送成功 - 主题: {}, 分区: {}, 偏移量: {}, 消息: {}",
                    topic, metadata.partition(), metadata.offset(), message);
            return metadata;
        } catch (Exception e) {
            log.error("#sendSync, 同步消息发送失败 - 主题: {}, 消息: {}", topic, message, e);
            throw new RuntimeException("同步消息发送失败", e);
        }
    }

    private void setTraceId() {
        TraceContext.putTraceId(TraceContext.getTraceId());
    }
}
