package com.example.demo.utils;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Minio 工具类
 */
@Slf4j
public class MinioUtils {

    /**
     * 判断桶是否存在
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @return 是否存在
     */
    @SneakyThrows
    public static boolean bucketExists(MinioClient minioClient, String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建桶
     * @param minioClient 客户端
     * @param bucketName 桶名称
     */
    @SneakyThrows
    public static void createBucket(MinioClient minioClient, String bucketName) {
        if (!bucketExists(minioClient, bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 文件是否存在
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @return 是否存在：true=存在、false=不存在
     */
    public static boolean fileExists(MinioClient minioClient, String bucketName, String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.info("#fileExists, file not exists, bucketName: {}, objectName: {}", bucketName, objectName);
            return false;
        }
        return true;
    }

    /**
     * 文件夹是否存在
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @return 是否存在：true=存在、false=不存在
     */
    public static boolean directoryExist(MinioClient minioClient, String bucketName, String objectName) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && item.objectName().equals(objectName + "/")) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("#directoryExist, directory not exists, bucketName: {}, objectName: {}", bucketName, objectName);
        }
        return false;
    }

    /**
     * 获取路径下文件列表
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称 根目录传空串 ""；文件夹以 "/" 结尾
     * @param recursive 是否递归: true=递归（查询全部子集）、false=非递归（只查找下一层）
     * @return 文件和文件夹列表
     */
    @SneakyThrows
    public static List<Item> listObjects(MinioClient minioClient, String bucketName, String objectName, boolean recursive) {
        Iterable<Result<Item>> iterable = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(objectName)
                        .recursive(recursive)
                        .build()
        );
        List<Item> list = new ArrayList<>();
        for (Result<Item> result : iterable) {
            list.add(result.get());
        }
        return list;
    }

    /**
     * 上传本地文件
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param filePath 文件路径
     * @return 上传结果
     */
    @SneakyThrows
    public static ObjectWriteResponse uploadFile(MinioClient minioClient, String bucketName, String objectName, String filePath) {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(filePath)
                        .build()
        );
    }

    /**
     * 上传文件流
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param inputStream 文件流
     * @return 上传结果
     */
    @SneakyThrows
    public static ObjectWriteResponse uploadFile(MinioClient minioClient, String bucketName, String objectName, InputStream inputStream) {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build()
        );
    }

    /**
     * 上传 MultipartFile 文件
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param file MultipartFile
     * @param objectName 对象名称
     * @param contentType 文件类型
     * @return 上传结果
     */
    @SneakyThrows
    public static ObjectWriteResponse uploadFile(MinioClient minioClient, String bucketName, MultipartFile file, String objectName, String contentType) {
        InputStream inputStream = file.getInputStream();
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .contentType(contentType)
                        .stream(inputStream, inputStream.available(), -1)
                        .build()
        );
    }

    /**
     * 获取文件
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @return 文件流
     */
    @SneakyThrows
    public static InputStream getObject(MinioClient minioClient, String bucketName, String objectName) {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    /**
     * 获取文件
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param offset 偏移量
     * @param length 长度
     * @return 文件流
     */
    @SneakyThrows
    public static InputStream getObject(MinioClient minioClient, String bucketName, String objectName, long offset, long length) {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .offset(offset)
                        .length(length)
                        .build()
        );
    }

    /**
     * 获取文件下载地址
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param expires 过期时间 <= 604800（7天）, 单位：秒
     * @return 文件下载地址
     */
    @SneakyThrows
    public static String getPresignedObjectUrl(MinioClient minioClient, String bucketName, String objectName, Integer expires) {
        GetPresignedObjectUrlArgs.Builder builder = GetPresignedObjectUrlArgs.builder();
        if (!Objects.isNull(expires)) {
            builder.expiry(expires);
        }
        GetPresignedObjectUrlArgs args = builder.bucket(bucketName).method(Method.GET).object(objectName).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 拷贝文件
     * @param minioClient 客户端
     * @param sourceBucketName 源桶名称
     * @param sourceObjectName 源对象名称
     * @param targetBucketName 目标桶名称
     * @param targetObjectName 目标对象名称
     * @return 拷贝结果
     */
    @SneakyThrows
    public static ObjectWriteResponse copyFile(MinioClient minioClient, String sourceBucketName, String sourceObjectName, String targetBucketName, String targetObjectName) {
        return minioClient.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(sourceBucketName).object(sourceObjectName).build())
                        .bucket(targetBucketName)
                        .object(targetObjectName)
                        .build());
    }

    /**
     * 删除文件
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     */
    @SneakyThrows
    public static void removeFile(MinioClient minioClient, String bucketName, String objectName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 删除文件夹
     * <p> MinIO官方客户端库不直接提供删除文件夹的方法，因此需要删除目录中的所有对象来模拟删除文件夹的操作。
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称 以"/"结尾
     */
    @SneakyThrows
    public static void removeDirectory(MinioClient minioClient, String bucketName, String objectName) {
        List<Item> items = listObjects(minioClient, bucketName, objectName, true);
        items.forEach(item -> {
            removeFile(minioClient, bucketName, item.objectName());
        });
    }

    /**
     * 获取文件信息
     * @param minioClient 客户端
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @return 文件信息
     */
    @SneakyThrows
    public static StatObjectResponse getObjectInfo(MinioClient minioClient, String bucketName, String objectName) {
        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }
}
