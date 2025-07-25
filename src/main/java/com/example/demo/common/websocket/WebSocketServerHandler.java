package com.example.demo.common.websocket;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.lisenser.ApplicationEventListener;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.common.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
public class WebSocketServerHandler implements WebSocketHandler {

    /**
     * 建立连接
     * @param webSocketSession 会话
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession webSocketSession) throws Exception {
        log.info("#[WebSocket] - The connection is successful.");
    }

    /**
     * 接收消息
     * @param webSocketSession 会话
     * @param webSocketMessage 消息
     * @throws Exception 异常
     */
    @Override
    public void handleMessage(@NotNull WebSocketSession webSocketSession, @NotNull WebSocketMessage<?> webSocketMessage) throws Exception {
        // 设置 traceId
        Optional.ofNullable((String) webSocketSession.getAttributes().get(TraceContext.TRACE_ID)).ifPresent(TraceContext::putTraceId);
        // 根据自定义注解信息，根据路由将请求映射到不同的控制器上，并自动转换其消息类型
        sendToMethod(webSocketSession, webSocketMessage);

    }

    /**
     * 异常处理
     * @param webSocketSession 会话
     * @param throwable 处理之前的异常
     * @throws Exception 处理之后的异常
     */
    @Override
    public void handleTransportError(@NotNull WebSocketSession webSocketSession, @NotNull Throwable throwable) throws Exception {

    }

    /**
     * 连接关闭
     * @param webSocketSession 会话
     * @param closeStatus 关闭状态
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession webSocketSession, @NotNull CloseStatus closeStatus) throws Exception {
        log.info("#[WebSocket] - The connection is closed.");
        TraceContext.clear();
        webSocketSession.getAttributes().remove(TraceContext.TRACE_ID);
    }

    /**
     * 是否支持分段消息
     * @return true=支持，false=不支持
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送消息到对应的方法
     * @param webSocketSession 会话
     * @param webSocketMessage 消息
     * @throws InvocationTargetException 反射异常
     * @throws IllegalAccessException 访问异常
     */
    public void sendToMethod(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws InvocationTargetException, IllegalAccessException {
        Object uri = webSocketSession.getAttributes().get(WebSocketInterceptor.URI);
        if (uri == null) {
            log.error("#[WebSocket] - The request uri is not found");
            throw new BaseException(ReturnCodeEnum.SERVER_ERROR, "The request uri is not found");
        }

        WebSocketReqMappingInfo webSocketReqMappingInfo = ApplicationEventListener.webSocketReqMappingInfoMap.get(uri.toString());
        if (webSocketReqMappingInfo != null) {
            // 反射调用目标方法，根据入参列表传入 WebSocket 对象，其他入参全部置空
            Method method = webSocketReqMappingInfo.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] parameterValues = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                if ((WebSocketSession.class.equals(parameterTypes[i]))) {
                    parameterValues[i] = webSocketSession;
                } else if (WebSocketMessage.class.isAssignableFrom(parameterTypes[i])) {
                    parameterValues[i] = webSocketMessage;
                } else {
                    parameterValues[i] = null;
                }
            }
            log.info("#[WebSocket] - send message to method, uri: [{}], class: {}, method: {}()", uri, webSocketReqMappingInfo.getClazz().getName(), webSocketReqMappingInfo.getMethod().getName());
            method.setAccessible(true);
            method.invoke(webSocketReqMappingInfo.getBean(), parameterValues);
        } else {
            log.error("#[WebSocket] - The request mapping is not found, uri: [{}]", uri);
            throw new BaseException(ReturnCodeEnum.SERVER_ERROR, "The request mapping is not found");
        }
    }
}
