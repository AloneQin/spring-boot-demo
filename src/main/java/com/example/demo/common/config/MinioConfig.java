package com.example.demo.common.config;

import com.example.demo.common.prop.MinioProperties;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@ConditionalOnProperty(name = "minio.enabled", havingValue = "true")
public class MinioConfig {

    private final MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getUsername(), minioProperties.getPassword())
                .build();
    }

}
