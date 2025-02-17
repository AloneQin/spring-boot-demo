package com.example.demo.common.mq.rabbit;

import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.rabbitmq.enabled", havingValue = "true")
public class RabbitConfig extends RabbitAutoConfiguration {

}
