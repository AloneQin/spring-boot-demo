package com.example.demo;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.Optional;

@Slf4j
@EnableRetry
@EnableAsync
@EnableWebSocket
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan({"com.example.demo.dao.mysql.mapper"})
@EnableMethodCache(basePackages = "com.example.demo.service")
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootDemoApplication.class, args);
        goodLuck(context);
    }

    public static void goodLuck(ConfigurableApplicationContext context) {
        Environment env = context.getEnvironment();
        String applicationName = env.getProperty("spring.application.name");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path")).orElse("");
        String port = env.getProperty("server.port");
        log.info("\n=======================================================================\n" +
                "                                                                          \n" +
                " ██████   ██████   ██████  ██████      ██      ██    ██  ██████ ██   ██   \n" +
                "██       ██    ██ ██    ██ ██   ██     ██      ██    ██ ██      ██  ██    \n" +
                "██   ███ ██    ██ ██    ██ ██   ██     ██      ██    ██ ██      █████     \n" +
                "██    ██ ██    ██ ██    ██ ██   ██     ██      ██    ██ ██      ██  ██    \n" +
                " ██████   ██████   ██████  ██████      ███████  ██████   ██████ ██   ██   \n" +
                "                                                                          \n" +
                "                 The moonlight is so beautiful tonight.                   \n" +
                "                                                                          \n" +
                "Application [{}] is ready, welcome to visit.                              \n" +
                "URL    : http://localhost:{}{}/                                           \n" +
                "swagger: http://localhost:{}{}/swagger-ui/index.html                      \n" +
                "=======================================================================\n",
                applicationName, port, contextPath, port, contextPath);
    }
}
