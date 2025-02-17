package com.example.demo.common.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

/**
 * TODO
 * 动态握手处理器，用于处理 WebSocket 握手请求，存在问题，以后再处理
 */
@Slf4j
public class NettyServerHandshakeHandler extends ChannelInboundHandlerAdapter {

    public static final String WEBSOCKET_URI = "websocket_uri";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            // 监听 WebSocket 握手请求
            if (Objects.equals(request.method(), HttpMethod.GET) && request.headers().contains("Upgrade", "websocket", false)) {
                String uri = request.uri();
                log.info("#channelRead, uri: {}", uri);
                if (uri.startsWith("/chat/")) {
                    log.info("#channelRead, add chat room: {}", uri);
                    // 保存 uri
                    ctx.channel().attr(AttributeKey.valueOf(WEBSOCKET_URI)).set(uri);
                    // 动态添加处理器
                    ctx.pipeline().addLast(new WebSocketServerProtocolHandler(uri));
                    ctx.pipeline().addLast(new NettyServerHandler());
                    // 完成握手
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, Unpooled.EMPTY_BUFFER);
                    response.headers().set(HttpHeaderNames.UPGRADE, "websocket");
                    response.headers().set(HttpHeaderNames.CONNECTION, "Upgrade");
                    response.headers().set(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT, generateSecWebSocketAccept(request.headers().get(HttpHeaderNames.SEC_WEBSOCKET_KEY)));
                    ctx.writeAndFlush(response);
                    // 握手成功后，移除握手处理器
//                    ctx.pipeline().remove(this);
                } else {
                    // 非法请求
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                    ctx.close();
                }
            }
            // 调用下一个处理器
            ctx.fireChannelRead(msg);
        }
    }
    private String generateSecWebSocketAccept(String secWebSocketKey) throws Exception {
        String combined = secWebSocketKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = sha1.digest(combined.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
