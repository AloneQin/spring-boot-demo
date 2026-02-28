package com.example.demo.common.statmachine;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderEventEnum> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderEventEnum> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new OrderStateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderEventEnum> states) throws Exception {
        states.withStates()
                // 初始状态：已创建
                .initial(OrderStatusEnum.CREATED)
                // 所有状态
                .states(EnumSet.allOf(OrderStatusEnum.class))
                // 结束状态：已收货
                .end(OrderStatusEnum.DELIVERED)
                // 结束状态：已取消
                .end(OrderStatusEnum.CANCELLED)
                // 结束状态：已退款
                .end(OrderStatusEnum.REFUNDED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderEventEnum> transitions) throws Exception {
        transitions
                // 已创建 -> 已支付
                .withExternal()
                .source(OrderStatusEnum.CREATED)
                .target(OrderStatusEnum.PAID)
                .event(OrderEventEnum.PAY)
                .and()

                // 已创建 -> 已取消
                .withExternal()
                .source(OrderStatusEnum.CREATED)
                .target(OrderStatusEnum.CANCELLED)
                .event(OrderEventEnum.CANCEL)
                .and()

                // 已支付 -> 已发货
                .withExternal()
                .source(OrderStatusEnum.PAID)
                .target(OrderStatusEnum.SHIPPED)
                .event(OrderEventEnum.SHIP)
                .and()

                // 已支付 -> 已退款
                .withExternal()
                .source(OrderStatusEnum.PAID)
                .target(OrderStatusEnum.REFUNDED)
                .event(OrderEventEnum.REFUND)
                .and()

                // 已发货 -> 已收货
                .withExternal()
                .source(OrderStatusEnum.SHIPPED)
                .target(OrderStatusEnum.DELIVERED)
                .event(OrderEventEnum.DELIVER)
                .and()

                // 已发货 -> 已退款
                .withExternal()
                .source(OrderStatusEnum.SHIPPED)
                .target(OrderStatusEnum.REFUNDED)
                .event(OrderEventEnum.REFUND);
    }
}
