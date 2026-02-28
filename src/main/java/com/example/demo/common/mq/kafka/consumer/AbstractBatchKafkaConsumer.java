package com.example.demo.common.mq.kafka.consumer;

import com.example.demo.common.trace.TraceContext;
import com.example.demo.utils.SmartCollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * 批量消息消费者抽象类
 * @param <T> 消息类型
 */
@Slf4j
public abstract class AbstractBatchKafkaConsumer<T> extends AbstractKafkaConsumer {

    /**
     * 批量监听消息
     * @param records 消费者记录列表
     * @param ack 确认消费标记
     */
    protected void listenBatch(List<ConsumerRecord<Object, Object>> records, Acknowledgment ack) {
        StopWatch stopWatch = new StopWatch("listenBatch-" + TraceContext.getTraceId());
        try {
            stopWatch.start();
            TraceContext.putTraceId();
            // 处理消息前置
            preHandleBatchMessage(records);
            // 处理消息
            handleBatchMessage(convertBatchMessage(records));
            // 确认消费
            super.ack(ack);
            // 处理消息后置
            postHandleBatchMessage(records);
        } catch (Exception e) {
            // 异常处理
            handleBatchMessageException(records, e);
        } finally {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            log.info("#listenBatch, 批量消息处理结束 - 主题: {}, 消息数量: {}, 耗时: {} ms", records.get(0).topic(), records.size(), stopWatch.getTotalTimeMillis());
            TraceContext.clear();
        }
    }

    /**
     * 批量消息处理前置
     * @param records 批量消息列表
     */
    protected void preHandleBatchMessage(List<ConsumerRecord<Object, Object>> records) {
        if (SmartCollectionUtils.isEmpty(records)) {
            return;
        }
        ConsumerRecord<Object, Object> first = records.get(0);
        ConsumerRecord<Object, Object> last = records.get(records.size() - 1);
        log.info("#preHandleBatchMessage, 监听到批量消息 - 主题: {}, 分区: {}, 消息数量: {}, 偏移量范围: {}-{}, 首时间戳: {}",
                first.topic(), first.partition(), records.size(), first.offset(), last.offset(), first.timestamp());
    }

    /**
     * 转换批量消息
     * @param records 批量消息列表
     * @return 批量消息列表
     */
    protected List<T> convertBatchMessage(List<ConsumerRecord<Object, Object>> records) {
        return records.stream()
                .map(record -> {
                    @SuppressWarnings("unchecked")
                    T message = (T) record.value();
                    return message;
                }).toList();
    }

    /**
     * 处理批量消息
     * @param list 批量消息列表
     */
    protected abstract void handleBatchMessage(List<T> list);

    /**
     * 批量消息处理后置
     * @param records 批量消息列表
     */
    protected void postHandleBatchMessage(List<ConsumerRecord<Object, Object>> records) {
        if (SmartCollectionUtils.isEmpty(records)) {
            return;
        }
        ConsumerRecord<Object, Object> first = records.get(0);
        ConsumerRecord<Object, Object> last = records.get(records.size() - 1);
        log.info("#postHandleBatchMessage, 批量消息处理完成 - 主题: {}, 分区: {}, 消息数量: {}, 偏移量范围: {}-{}",
                first.topic(), first.partition(), records.size(), first.offset(), last.offset());
    }

    /**
     * 批量消息处理异常
     * @param records 批量消息列表
     * @param exception 异常
     */
    protected void handleBatchMessageException(List<ConsumerRecord<Object, Object>> records, Exception exception) {
        if (SmartCollectionUtils.isEmpty(records)) {
            return;
        }
        ConsumerRecord<Object, Object> first = records.get(0);
        ConsumerRecord<Object, Object> last = records.get(records.size() - 1);
        log.error("#handleBatchMessageException, 批量消息处理异常 - 主题: {}, 分区: {}, 消息数量: {}, 偏移量范围: {}-{}",
                first.topic(), first.partition(), records.size(), first.offset(), last.offset());
    }
}
