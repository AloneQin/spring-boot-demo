package com.example.demo.common.mq.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit-扇形交换机 配置
 * <p> Fanout: 扇形交换机，广播模式，无视路由键，交换机在接收到消息后会转发到绑定到它上面的所有队列
 * <p> Direct: 直连交换机，根据消息携带的路由键将消息投递给对应队列，一个消息只能被一个消费者消费，一对一
 * <p> Topic: 主题交换机，支持路由（#: 匹配一个或多个、*: 匹配一个）灵活匹配队列，一个消息可被多个队列接收，
 *            若多个消费者监听同一个队列，会以轮询的方式发送给消费者，另类一对一
 */
@Configuration
@ConditionalOnProperty(name = "spring.rabbitmq.enabled", havingValue = "true")
public class RabbitFanoutExchangeConfig {

    public static final String TEST_FANOUT_QUEUE = "test_fanout_queue";

    public static final String TEST_FANOUT_EXCHANGE = "test_fanout_exchange";

    @Bean
    public Queue testFanoutQueue() {
        return new Queue(TEST_FANOUT_QUEUE, true);
    }

    @Bean
    public FanoutExchange testFanoutExchange() {
        return new FanoutExchange(TEST_FANOUT_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingFanoutExchangeOrderDicQueue(){
        return BindingBuilder.bind(testFanoutQueue()).to(testFanoutExchange());
    }
}
