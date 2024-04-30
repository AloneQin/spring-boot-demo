package com.example.demo.common.websocket;

import com.example.demo.common.websocket.TestTextWebSocketHandler;
import com.example.demo.common.websocket.WebSocketInterceptor;
import com.example.demo.common.websocket.WebSocketServerHandler;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.ServletWebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry) {
        // 替换网址路径工具为自定义
        if (registry instanceof ServletWebSocketHandlerRegistry) {
            ((ServletWebSocketHandlerRegistry)registry).setUrlPathHelper(new PrefixUrlPathHelper("/webSocket"));
        }

        registry
                // 如果项目中少量使用 webSocket 接口，使用这种简单配置的方法
                .addHandler(new TestTextWebSocketHandler(), "/test/aaa/bbb")
                // 如果项目中大量使用 webSocket 接口，使用这种通配符方法，将请求转发到一个公共的处理器中再按需进行路由处理
                .addHandler(new WebSocketServerHandler(), "/webSocket/**")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }

    /**
     * 前缀网址路径工具类，解决 WebSocket 处理器路径匹配不支持通配符的问题
     */
    @AllArgsConstructor
    public static class PrefixUrlPathHelper extends UrlPathHelper {

        /**
         * 前缀
         */
        private String prefix;

        @Override
        public @NotNull String resolveAndCacheLookupPath(@NotNull HttpServletRequest request) {
            // 获取全路径
            String path = super.resolveAndCacheLookupPath(request);
            // 如果匹配前缀，就替换为通配符路径，与处理器的路径保持一致
            if (path.startsWith("/websocket")) {
                return "/webSocket/**";
            }
            return path;
        }
    }
}
