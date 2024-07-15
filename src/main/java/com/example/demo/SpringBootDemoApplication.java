package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
@SpringBootApplication
@MapperScan({"com.example.demo.dao.mysql.mapper"})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootDemoApplication.class, args);
        goodLuck(context, args);
    }

    public static void goodLuck(ConfigurableApplicationContext context, String[] args) {
        Environment env = context.getEnvironment();
        String applicationName = env.getProperty("spring.application.name");
        String contextPath = env.getProperty("server.servlet.context-path");
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
                "Application [{}] is ready: http://localhost:{}{}/"                            +
                "\n=======================================================================\n",
                applicationName, port, Optional.ofNullable(contextPath).orElse(""));
    }
}
