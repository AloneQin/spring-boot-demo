package com.example.demo.common.mq.rabbit;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RabbitReceiver {

    @SneakyThrows
    @RabbitListener(queues = RabbitFanoutExchangeConfig.TEST_FANOUT_QUEUE)
    public void receiveMessage(String msg, Channel channel, Message message) {
        String msgId = message.getMessageProperties().getHeader("spring_returned_message_correlation");
        try {
            log.info("#receiveMessage, messageId: {}, message: {}", msgId, msg);
            /*
             * 确认消息
             * p1: 消息投递序号
             * p2: false=不批量确认
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.warn("#receiveMessage, 消息已被重复处理, messageId: {}, message: {}", msgId, msg);
                /*
                 * 拒绝消息
                 * p1: 消息投递序号
                 * p2: false=不重新入队，若配置了死信队列则进入死信队列
                 */
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.warn("#receiveMessage, 消息重新入队, messageId: {}, message: {}", msgId, msg);
                /*
                 * 消息重新入队
                 * p1: 消息投递序号
                 * p2: false=不批量确认
                 * p3: true=重新入队、false=丢弃消息
                 */
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}
