package com.example.demo.controller;

import com.example.demo.common.websocket.WebSocketRequestMapping;
import com.example.demo.utils.SmartStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Controller
public class WebSocketController2 {

    @WebSocketRequestMapping("/webSocket/ccc")
    private void test1(WebSocketSession webSocketSession, TextMessage textMessage) throws Exception {
        log.info("接收到消息：{}", textMessage.getPayload());
        for (int i = 0; i < 10; i++) {
            Thread.sleep(300L);
            webSocketSession.sendMessage(new TextMessage(SmartStringUtils.format("Hello, WebSocket! no: {}, data: {}", i, textMessage.getPayload())));
        }
    }

}
