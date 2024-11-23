package com.example.demo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfigContext {

    private static String applicationName;

    @Value("${spring.application.name}")
    public void setApplicationName(String applicationName) {
        ApplicationConfigContext.applicationName = applicationName;
    }

    public static String applicationName() {
        return applicationName;
    }

}
