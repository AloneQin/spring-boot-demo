package com.example.demo.common.mq.kafka.consumer;

import com.example.demo.common.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.util.StopWatch;

/**
 * 单个消息消费者抽象类
 * @param <T> 消息类型
 */
@Slf4j
public abstract class AbstractSingleKafkaConsumer<T> extends AbstractKafkaConsumer {

    /**
     * 监听单个消息
     * @param record 消费者记录
     * @param ack 确认消费标记
     */
    protected void listenSingle(ConsumerRecord<Object, Object> record, Acknowledgment ack) {
        StopWatch stopWatch = new StopWatch("listenSingle-" + TraceContext.getTraceId());
        try {
            stopWatch.start();
            TraceContext.putTraceId();
            // 处理消息前置
            preHandleSingleMessage(record);
            // 处理消息
            handleSingleMessage(convertSingleMessage(record));
            // 确认消费
            super.ack(ack);
            // 处理消息后置
            postHandleSingleMessage(record);
        } catch (Exception e) {
            // 异常处理
            handleSingleMessageException(record, e);
        } finally {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            log.info("#listenSingle, 单个消息处理结束 - 主题: {}, 耗时: {} ms", record.topic(), stopWatch.getTotalTimeMillis());
            TraceContext.clear();
        }
    }

    /**
     * 处理单个消息前置
     * @param record 消费者记录
     */
    protected void preHandleSingleMessage(ConsumerRecord<Object, Object> record) {
        log.info("#preHandleSingleMessage, 监听到单个消息 - 主题: {}, 分区: {}, 偏移量: {}, 时间戳: {}, 消息: {}",
                record.topic(), record.partition(), record.offset(), record.timestamp(), record.value());
    }

    /**
     * 转换单个消息
     * @param record 消费者记录
     * @return 消息
     */
    protected T convertSingleMessage(ConsumerRecord<Object, Object> record) {
        @SuppressWarnings("unchecked")
        T message = (T) record.value();
        return message;
    }

    /**
     * 处理单个消息
     * @param t 消息
     */
    protected abstract void handleSingleMessage(T t);

    /**
     * 处理单个消息后置
     * @param record 消费者记录
     */
    protected void postHandleSingleMessage(ConsumerRecord<Object, Object> record) {
        log.info("#postHandleSingleMessage, 单个消息处理完成 - 主题: {}, 分区: {}, 偏移量: {}",
                record.topic(), record.partition(), record.offset());
    }

    /**
     * 处理单个消息异常
     * @param record 消费者记录
     * @param exception 异常
     */
    protected void handleSingleMessageException(ConsumerRecord<Object, Object> record, Exception exception) {
        log.error("#handleSingleMessageException, 单个消息处理异常 - 主题: {}, 分区: {}, 偏移量: {}, 键: {}, 值: {}",
                record.topic(), record.partition(), record.offset(), record.key(), record.value(), exception);
    }
}
