package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/index")
    public String index() {
        log.info("welcome...");

        return "index";
    }

    @PostMapping("/testRequestWrapper")
    public String testRequestWrapper(HttpServletRequest request) {
        // 获取本来就有的请求头
        String name = request.getHeader("name");
        log.info("header name: {}", name);

        // 获取添加的请求头
        Enumeration<String> from = request.getHeaders("from");
        ArrayList<String> fromList = Collections.list(from);
        log.info("header from: {}", fromList.toString());

        // 获取本来就传的参数
        String param1 = request.getParameter("param1");
        log.info("parameter param1: {}", param1);

        // 获取添加的参数
        String[] param2Arr = request.getParameterValues("param2");
        log.info("parameter param2: {}", Arrays.asList(param2Arr).toString());

        return "SUCCESS";
    }
}
