package com.example.demo.controller;

import com.example.demo.common.websocket.WebSocketRequestMapping;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Controller
@WebSocketRequestMapping({"/webSocket/aaa", "/webSocket/bbb"})
public class WebSocketController {

    @WebSocketRequestMapping("/bbb")
    private void test1(WebSocketSession webSocketSession, TextMessage textMessage) throws Exception {
        log.info("接收到消息：{}", textMessage.getPayload());
        for (int i = 0; i < 10; i++) {
            Thread.sleep(300L);
            webSocketSession.sendMessage(new TextMessage(StringUtils.format("Hello, WebSocket! no: {}, data: {}", i, textMessage.getPayload())));
        }
    }

    @WebSocketRequestMapping("/ccc")
    public void test2(TextMessage textMessage) {

    }

    public void test3() {

    }

    public void test4() {

    }

    public void test5() {

    }

    public void test6() {

    }
}
