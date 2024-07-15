package com.example.demo.common.minio;

import com.example.demo.common.config.MinioProperties;
import com.example.demo.utils.MinioUtils;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@AllArgsConstructor
public class MinioManager {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public String getPresignedObjectUrl(String objectName, Integer expires) {
        return MinioUtils.getPresignedObjectUrl(minioClient, minioProperties.getBucket(), objectName, expires);
    }

    public ObjectWriteResponse uploadFile(MultipartFile file, String objectName, String contentType) {
        return MinioUtils.uploadFile(minioClient, minioProperties.getBucket(), file, objectName, contentType);
    }
}
