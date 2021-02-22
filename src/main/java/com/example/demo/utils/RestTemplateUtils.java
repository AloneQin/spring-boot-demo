package com.example.demo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateUtils {

    /**
     * 发送请求
     * @param restTemplate restTemplate 实例
     * @param url 请求地址
     * @param method 请求方法类型
     * @param headerMap 请求头参数
     * @param contentType 内容类型
     * @param uriVariableMap 请求地址中的参数，key 对应 url 中用"{}"包裹的参数
     * @param requestBody 请求体，非 JSON 使用{@link MultiValueMap}，否则使用{@link JSONObject}或{@link JSONArray}
     * @param responseType 响应体类型
     * @param <T> 响应体类型泛型
     * @return
     */
    public static <T> ResponseEntity<T> send(RestTemplate restTemplate, String url, HttpMethod method,
                                             Map<String, String> headerMap, MediaType contentType,
                                             Map<String, ?> uriVariableMap, Object requestBody,
                                             Class<T> responseType) {
        // header
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headerMap != null) {
            httpHeaders.setAll(headerMap);
        }
        if (contentType != null) {
            httpHeaders.setContentType(contentType);
        }

        // send request
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariableMap);
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // ----------------------get----------------------
        // 方法一：参数写死在地址中
        String url = "http://${HOST}:${PORT}/xxx?a=1&b=2";
        ResponseEntity<String> responseEntity = send(restTemplate, url, HttpMethod.GET,
                null,null, null, null, String.class);

        // 方法二：地址中声明可变参数
        url = "http://${HOST}:${PORT}/xxx?a=${a}&b=${b}";
        HashMap<String, String> uriVariableMap = new HashMap<>();
        uriVariableMap.put("a", "1");
        uriVariableMap.put("b", "2");
        responseEntity = send(restTemplate, url, HttpMethod.GET,
                null,null, uriVariableMap, null, String.class);

        // 下载文件
        send(restTemplate, url, HttpMethod.GET,
                null,null, new HashMap<>(), null, byte[].class);

        // ----------------------post----------------------
        // 非 json 传参
        url = "http://${HOST}:${PORT}/xxx";
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("a", "1");
        requestBody.add("b", "2");
        responseEntity = send(restTemplate, url, HttpMethod.POST,
                null,null, new HashMap<>(), requestBody, String.class);

        // json 传参
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "1");
        jsonObject.put("b", "2");
        responseEntity = send(restTemplate, url, HttpMethod.POST,
                null, MediaType.APPLICATION_JSON, new HashMap<>(), jsonObject, String.class);

        // 上传文件
        requestBody.add("file", new FileSystemResource("/xxx/xx/x.txt"));
        responseEntity = send(restTemplate, url, HttpMethod.POST,
                null, MediaType.MULTIPART_FORM_DATA, new HashMap<>(), requestBody, String.class);

        // put、delete 类似
    }
}
