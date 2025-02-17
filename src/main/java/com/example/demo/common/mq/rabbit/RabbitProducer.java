package com.example.demo.common.mq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RabbitProducer {

    @Lazy
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnsCallback);
        rabbitTemplate.convertAndSend(RabbitFanoutExchangeConfig.TEST_FANOUT_EXCHANGE, "", msg, message -> {
            MessageProperties properties = message.getMessageProperties();
            // 消息持久化
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            // 消息格式
            properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            return message;
        }, new CorrelationData("id_" + System.currentTimeMillis()));
    }

    private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) ->
            log.info("#confirmCallback, correlationData: {}, ack: {}, cause: {}", correlationData, ack, cause);

    private final RabbitTemplate.ReturnsCallback returnsCallback = returnedMessage ->
            log.info("#returnsCallback, returnedMessage: {}", returnedMessage);
}
