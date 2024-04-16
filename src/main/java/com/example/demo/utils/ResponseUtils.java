package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

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
     * @throws Exception IO异常
     */
    public static void fileDownload(InputStream inputStream, String fileName, HttpServletResponse response) throws Exception {
        // 设置头信息
        response.setContentType("application/octet-stream;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
        // IO操作
        byte[] buffer = new byte[1024 * 10];
        BufferedInputStream bis = null;
        ServletOutputStream sos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            sos = response.getOutputStream();
            int i = -1;
            while ((i = bis.read(buffer)) != -1) {
                sos.write(buffer, 0, i);
            }
            sos.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (bis != null) {
                bis.close();
            }
            if (sos != null) {
                sos.close();
            }
        }
    }

    /**
     * 字符串转换为输入流
     * @param inputStr 输入字符串
     * @return 输入流
     */
    public static InputStream stringToStream(String inputStr) {
        if (inputStr == null) {
            return null;
        }

        return new ByteArrayInputStream(inputStr.getBytes());
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
        fileDownload(stringToStream(jsonStr), fileName, response);
    }
}
