package com.example.demo.api;

import com.example.demo.common.response.DefaultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "open", url = "http://localhost:8888/demo/open")
public interface OpenAPI {

    @GetMapping("/first")
    DefaultResponse<String> first();

}
