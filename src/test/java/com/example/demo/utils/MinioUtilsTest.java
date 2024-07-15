package com.example.demo.utils;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
class MinioUtilsTest {

    @Resource
    private MinioClient minioClient;
    
    @Test
    void bucketExists() {
    }

    @Test
    void createBucket() {
    }

    @Test
    void fileExists() {
        boolean result = MinioUtils.fileExists(minioClient, "test", "images/women.jpg");
        System.out.println(result);
    }

    @Test
    void directoryExist() {
        boolean result = MinioUtils.directoryExist(minioClient, "test", "images");
        System.out.println(result);
    }

    @Test
    void uploadLocalFile() {
        Path path = Paths.get("D:/work/dev/test/logo.png");
        File file = path.toFile();
        ObjectWriteResponse response = MinioUtils.uploadFile(minioClient, "test", "aaa/logo.png", file.getPath());
        System.out.println(FastjsonUtils.toStringFormat(response));
    }

    @Test
    @SneakyThrows
    void listObjects() {
        // 顶层传空串""
        List<Item> list = MinioUtils.listObjects(minioClient, "test", "", false);
        list.forEach(item -> System.out.println("1: " + item.objectName()));

        // 文件夹以"/"结尾
        list = MinioUtils.listObjects(minioClient,"test", "aaa/bbb/", false);
        list.forEach(item -> System.out.println("2: " + item.objectName()));
    }

    @Test
    void getObject() {
        InputStream inputStream = MinioUtils.getObject(minioClient,"test", "/aaa/bbb/helloWorld.json");
        String json = IOUtils.readStrFromInputStream(inputStream);
        System.out.println(json);
    }

    @Test
    void removeFile() {
        MinioUtils.removeFile(minioClient, "test", "aaa");
    }

    @Test
    void removeDirectory() {
        MinioUtils.removeDirectory(minioClient, "test", "images/");
    }
}