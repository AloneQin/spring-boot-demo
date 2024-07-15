package com.example.demo.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("minio")
public class MinioProperties {

    private String endpoint;

    private String username;

    private String password;

    private String bucket;

}
