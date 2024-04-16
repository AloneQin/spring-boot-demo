package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtils {

    /**
     * 默认超时时间，单位：秒
     */
    private static final long DEFAULT_TIME_OUT = 180L;

    /**
     * key 定义
     * 当 Content-Type 为{@link RawContentTypeEnum}中的一种时，表示需要在 body 中上传未经处理的原始类型
     * 此时需要在请求体中根据此 key 获取其值
     */
    private static final String BODY_RAW_CONTENT = "body-raw-content";

    public static OkHttpClient initClient(Long connectTimeOut, Long readTimeOut, Long writeTimeOut) {
        if (connectTimeOut == null) {
            connectTimeOut = DEFAULT_TIME_OUT;
        }
        if (readTimeOut == null) {
            readTimeOut = DEFAULT_TIME_OUT;
        }
        if (writeTimeOut == null) {
            writeTimeOut = DEFAULT_TIME_OUT;
        }

        // 设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS);

        return builder.build();
    }

    public static ResponseBody send(OkHttpClient client, String url, String method, Map<String, String> headerMap,
                                    Map<String, String> paramMap, Map<String, Object> bodyMap) throws IOException {
        // 编码 url
        url = encodeUrl(url, paramMap);

        // 设置 contentType
        String contentType = headerMap.get(HttpHeaders.CONTENT_TYPE) == null
                ? MediaType.APPLICATION_FORM_URLENCODED_VALUE
                : headerMap.get(HttpHeaders.CONTENT_TYPE);

        String content = "";
        RequestBody requestBody = null;

        // 根据 contentType 设置拼装参数
        if (contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            // 表单类型
            content = encodeContent(content, bodyMap);
        } else if (RawContentTypeEnum.contains(contentType)) {
            // raw 类型
            content = bodyMap.get(BODY_RAW_CONTENT).toString();
        } else if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            // 上传文件类型
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
                if (entry.getValue() instanceof File) {
                    // 文件参数
                    File file = (File) entry.getValue();
                    okhttp3.MediaType mediaType = okhttp3.MediaType.parse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                    bodyBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(mediaType, file));
                } else {
                    // 文本参数
                    bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
            }
            requestBody = bodyBuilder.build();
        } else {
            throw new RuntimeException("not support Content-Type: " + contentType);
        }

        // 校验并赋予初值，避免异常
        if (requestBody == null && HttpMethod.requiresRequestBody(method)) {
            requestBody = RequestBody.create(okhttp3.MediaType.parse(contentType), content == null ? "" : content);
        }

        // 构造请求
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headerMap))
                .method(method, requestBody)
                .build();
        // 获取响应
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.error("call http request failed: {}", response.message());
            return null;
        }

        return response.body();
    }

    /**
     * 编码 url
     *
     * <p> url = abc
     * <p> paramMap = {"p1":"1", "p2","2"}
     * <p> output -> abc?p1=1&p2=2
     *
     * @param uri 请求名
     * @param paramMap 参数集合
     * @return 组装好的 url
     */
    private static String encodeUrl(String uri, Map<String, String> paramMap) {
        if (uri.contains("?")) {
            return uri;
        }

        int index = 0;
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (index == 0) {
                uri += "?";
            } else {
                uri += "&";
            }
            uri += entry.getKey() + "=" + entry.getValue();
            index++;
        }

        return uri;
    }

    private static String encodeContent(String content, Map<String, Object> bodyMap) {
        int index = 0;
        for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
            if (index != 0) {
                content += "&";
            }
            content += entry.getKey() + "=" + entry.getValue();
            index++;
        }

        return content;
    }

    public static void main(String[] args) throws IOException {
        String uri = "http://www.abc.com/abc";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("p1", "1");
        paramMap.put("p2", "2");
        System.out.println("encodeUrl: " + encodeUrl(uri, paramMap));

        String content = "";
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("a", "a");
        bodyMap.put("b", "b");
        System.out.println("encodeContent: " + encodeContent(content, bodyMap));

        OkHttpClient client = initClient(null, null, null);

        // ------------------------ HEADER ------------------------
        uri = "http://localhost:8888/rest/getHeader";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", "ABCDEFG");
        ResponseBody result = send(client, uri, org.springframework.http.HttpMethod.GET.name(), headerMap,
                new HashMap<>(), new HashMap<>());
        System.out.println("GET getHeader: " + result.string());
        result.close();

        // ------------------------ GET ------------------------
        uri = "http://localhost:8888/commonReturn/validated";
        paramMap.clear();
        paramMap.put("name", "tom");
        paramMap.put("age", "20");
        result = send(client, uri, org.springframework.http.HttpMethod.GET.name(), new HashMap<>(),
                paramMap, new HashMap<>());
        System.out.println("GET commonReturn: " + result.string());
        result.close();

        // 下载文件
        uri = "http://localhost:8888/rest/fileDownload";
        headerMap.clear();
        bodyMap.clear();
        result = send(client, uri, org.springframework.http.HttpMethod.GET.name(), headerMap,
                new HashMap<>(), bodyMap);

        System.out.println("GET fileDownload: " + result.byteString().base64());
        IOUtils.copyFile(result.byteStream(), "/Users/alone/work/test/logo-copy.png");
        result.close();

        // ------------------------ POST ------------------------
        uri = "http://localhost:8888/rest/getPara";
        result = send(client, uri, org.springframework.http.HttpMethod.POST.name(), new HashMap<>(),
                new HashMap<>(), bodyMap);
        System.out.println("POST getPara: " + result.string());
        result.close();

        // 上传文件
        uri = "http://localhost:8888/rest/fileUpload";
        headerMap.clear();
        headerMap.put(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        bodyMap.clear();
        bodyMap.put("file", new File("/Users/alone/Downloads/logo.png"));
        bodyMap.put("savePath", "/Users/alone/Downloads/");
        result = send(client, uri, org.springframework.http.HttpMethod.POST.name(), headerMap,
                new HashMap<>(), bodyMap);
        System.out.println("POST fileUpload: " + result.string());
        result.close();
    }

}

@AllArgsConstructor
enum RawContentTypeEnum {

    TEXT            ("TEXT(text/plain)",                    MediaType.TEXT_PLAIN_VALUE),
    JSON            ("JSON(application/json)",              MediaType.APPLICATION_JSON_VALUE),
    JAVASCRIPT      ("javascript(application/javascript)",  "application/javascript"),
    XML_APPLICATION ("XML(application/xml)",                MediaType.APPLICATION_XML_VALUE),
    XML_TEXT        ("XML(text/xml)",                       MediaType.TEXT_XML_VALUE),
    HTML            ("HTML(text/html)",                     MediaType.TEXT_HTML_VALUE),
    ;

    public final String key;
    public final String value;

    public static boolean contains(String contentType) {
        for (RawContentTypeEnum rcte : values()) {
            if (contentType.startsWith(rcte.value)) {
                return true;
            }
        }
        return false;
    }
}
