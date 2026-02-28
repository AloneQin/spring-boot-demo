package com.example.demo.common.statmachine;

import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderStateMachineTestService {

    @Resource
    private StateMachine<OrderStatusEnum, OrderEventEnum> orderStateMachine;

    public void test() {
        orderStateMachine.start();
        System.out.println("开启状态机");

        for (OrderEventEnum eventEnum : OrderEventEnum.values()) {
            boolean success = orderStateMachine.sendEvent(eventEnum);
            System.out.println("事件发送结果：" + success);
            System.out.println("当前状态：" + orderStateMachine.getState().getId());
        }

        orderStateMachine.stop();
        System.out.println("结束状态机");
    }

}
