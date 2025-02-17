package com.example.demo.common.netty;

import com.example.demo.utils.RandomUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Slf4j
@Component
public class NettyServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel channel;

    @PostConstruct
    public void start() {
        // 使用子线程启动，避免阻塞主线程
        new Thread(this::init, "netty-" + RandomUtils.getNumRandom(6)).start();
    }

    public void init() {
        try {
            int port = 8889;
            channel = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new HttpServerCodec())
                                    .addLast(new ChunkedWriteHandler())
                                    .addLast(new HttpObjectAggregator(1024 * 8))
//                                    .addLast(new WebSocketServerProtocolHandler("/chat"))
//                                    .addLast(new NettyServerHandler())
                                    .addLast(new NettyServerHandshakeHandler())
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())

                            ;
                        }
                    })
                    .bind(port)
                    .sync()
                    .channel();
            log.info("#init, netty server start success, port: {}", port);
            // 监听端口关闭
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("#init, netty server start error", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void stop() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        if (Objects.nonNull(channel)) {
            channel.close();
        }
        log.info("#start, netty server stop success");
    }
}
