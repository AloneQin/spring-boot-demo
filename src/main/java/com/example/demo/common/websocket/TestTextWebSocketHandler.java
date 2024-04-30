package com.example.demo.common.websocket;

import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestTextWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        log.info("#[WebSocket] - received message: {}", message.getPayload());

        Session nativeSession = ((StandardWebSocketSession) session).getNativeSession();
        Map<String, List<String>> requestParameterMap = nativeSession.getRequestParameterMap();
        log.info("#[WebSocket] - requestParameterMap: {}", FastjsonUtils.toString(requestParameterMap));

        for (int i = 0; i < 10; i++) {
            Thread.sleep(300L);
            session.sendMessage(new TextMessage(StringUtils.format("Hello, WebSocket! no: {}, data: {}", i, FastjsonUtils.toString(requestParameterMap))));
        }
    }
}
