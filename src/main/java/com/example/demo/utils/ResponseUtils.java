package com.example.demo.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * HttpServletResponse 工具类
 */
@Slf4j
public class ResponseUtils {

    /**
     * 输出响应
     * @param response HttpServletResponse
     * @param statusCode http 状态码
     * @param contentType 响应内容类型
     * @param content 主体信息
     * @throws IOException IO异常
     */
    public static void output(HttpServletResponse response, Integer statusCode, String contentType, String content) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(contentType);
        response.getWriter().write(content);
        response.getWriter().flush();
    }

    public static void outputJson(HttpServletResponse response, Integer statusCode, Object object) throws IOException {
        output(response, statusCode, "application/json;charset=UTF-8", FastjsonUtils.toStringKeepNull(object));
    }

    /**
     * 文件下载（以附件形式）
     * @param inputStream 输入流
     * @param fileName 文件名称
     * @param response HttpServletResponse
     */
    @SneakyThrows
    public static void fileDownloadByAttachment(InputStream inputStream, String fileName, HttpServletResponse response) {
        // 设置头信息
        response.setContentType("application/octet-stream;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        // IO操作
        outputStreamToResponse(inputStream, response);
    }

    /**
     * 文件下载
     * @param inputStream 输入流
     * @param fileName 文件名称
     * @param response HttpServletResponse
     */
    @SneakyThrows
    public static void fileDownload(InputStream inputStream, String fileName, HttpServletResponse response) {
        // 设置头信息
        response.addHeader("Content-Disposition", "fileName=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        // IO操作
        outputStreamToResponse(inputStream, response);
    }

    /**
     * 输出流到响应
     * @param inputStream 输入流
     * @param response 响应
     */
    @SneakyThrows
    public static void outputStreamToResponse(InputStream inputStream, HttpServletResponse response) {
        byte[] buffer = new byte[1024 * 10];
        @Cleanup BufferedInputStream bis = new BufferedInputStream(inputStream);;
        @Cleanup ServletOutputStream sos = response.getOutputStream();
        int i;
        while ((i = bis.read(buffer)) != -1) {
            sos.write(buffer, 0, i);
        }
        sos.flush();
    }

    /**
     * 字符串转换为输入流
     * @param inputStr 输入字符串
     * @return 输入流
     */
    public static InputStream stringToStream(String inputStr) {
        return Optional.ofNullable(inputStr).map(s -> new ByteArrayInputStream(s.getBytes())).orElse(null);
    }

    /**
     * 对象序列化并作为附件下载
     * @param obj 目标对象
     * @param fileName 文件名称
     * @param response HttpServletResponse
     * @throws Exception 异常
     */
    public static void fileDownloadByObj(Object obj, String fileName, HttpServletResponse response) throws Exception {
        String jsonStr = FastjsonUtils.toStringKeepNull(obj);
        fileDownloadByAttachment(stringToStream(jsonStr), fileName, response);
    }
}
