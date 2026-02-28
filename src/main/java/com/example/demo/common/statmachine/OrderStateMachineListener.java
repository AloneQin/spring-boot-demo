package com.example.demo.common.statmachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
public class OrderStateMachineListener extends StateMachineListenerAdapter<OrderStatusEnum, OrderEventEnum> {

    @Override
    public void stateChanged(State<OrderStatusEnum, OrderEventEnum> from, State<OrderStatusEnum, OrderEventEnum> to) {
        if (from == null) {
            log.info("#stateChanged, 初始状态: {}", to.getId());
        } else {
            log.info("#stateChanged, 状态变更, 变更前: {}, 变更后: {}", from.getId(), to.getId());
        }
    }

    @Override
    public void eventNotAccepted(Message<OrderEventEnum> event) {
        log.error("#eventNotAccepted, 当前状态不支持事件, event: {}", event.getPayload());
    }
}
