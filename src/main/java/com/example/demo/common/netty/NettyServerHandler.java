package com.example.demo.common.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelMap.put(channel.id().asShortText(), channel);
        log.info("#channelActive, client connected, channel: {}, current channel num: {}", ctx.channel(), channelMap.size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelMap.remove(channel.id().asShortText());
        log.info("#channelInactive, client disconnected, channel: {}, current channel num: {}", ctx.channel(), channelMap.size());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            String message = frame.text();
            log.info("#channelRead, channel: {}, message: {}", ctx.channel().id().asShortText(), message);
            if (Objects.nonNull(message)) {
                // 将信息发给聊天室里的其他人
                channelMap.entrySet().stream()
                        .filter(entry -> !entry.getKey().equals(ctx.channel().id().asShortText()))
                        .forEach(entry -> {
                            Channel channel = entry.getValue();
                            channel.writeAndFlush(new TextWebSocketFrame(message));
                        });
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("#exceptionCaught", cause);
        ctx.close();
    }


}
