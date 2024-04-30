package com.example.demo.common.websocket;

import com.example.demo.common.trace.TraceManager;
import com.example.demo.utils.FastjsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * WebSocket握手拦截器
 * <p> Websocket 仅需握手一次便建立连接，故拦截器仅在建立连接进行握手时触发
 */
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {

    public static final String URI = "uri";

    /**
     * 握手之前
     * @param request 请求
     * @param response 响应
     * @param webSocketHandler 处理器
     * @param attributes 属性集合
     * @return 是否允许握手
     * @throws Exception 异常
     */
    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler webSocketHandler, @NotNull Map<String, Object> attributes) throws Exception {
        log.info("#[WebSocket] - Before Handshake: The handshake begins, ready to make a connection.");
        // 设置 traceId
        TraceManager.putTraceId((String) attributes.get(TraceManager.TRACE_ID));
        Optional.ofNullable(TraceManager.getTraceId()).ifPresent(traceId -> attributes.put(TraceManager.TRACE_ID, traceId));
        if (request instanceof ServletServerHttpRequest) {
            String path = request.getURI().getPath();
            attributes.put(URI, path);
            log.info("#[WebSocket] - path: {}", path);

            HttpHeaders headers = request.getHeaders();
            log.info("#[WebSocket] - headers: {}", FastjsonUtils.toString(headers));

            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            Map<String, String[]> parameterMap = servletRequest.getParameterMap();
            log.info("#[WebSocket] - parameterMap: {}", FastjsonUtils.toString(parameterMap));
        }

        return true;
    }

    /**
     * 握手之后
     * @param request 请求
     * @param response 响应
     * @param webSocketHandler 处理器
     * @param e 异常
     */
    @Override
    public void afterHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler webSocketHandler, Exception e) {
        log.info("#[WebSocket] - After Handshake: The handshake is complete and the connection has been established.");
        Optional.ofNullable(e).ifPresent(ex -> log.error("#[WebSocket] - After Handshake: catch exception.", ex));
    }
}
