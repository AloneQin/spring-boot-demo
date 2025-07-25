package com.example.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.demo.api.OpenAPI;
import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.utils.FastjsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
public class OpenController implements OpenAPI {

    private final OpenAPI openAPI;

    @Override
    @GetMapping("/first")
    public DefaultResponse<String> first() {
        return DefaultResponse.success("first");
    }

    @GetMapping("/callFirst")
    public void callFirst() {
        DefaultResponse<String> response = openAPI.first();
        log.info("response: {}", FastjsonUtils.toString(response));
    }

    @GetMapping("/second")
    @SentinelResource(value = "open/second", blockHandler = "secondBlockHandler", fallback = "secondFallback")
    public DefaultResponse<String> second() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("模拟异常");
        }
        return DefaultResponse.success("second");
    }

    /**
     * 限流降级时触发（必须与原方法具有相同的参数列表，并加上 BlockException 参数）
     * @param ex 阻断异常
     * @return 返回结果
     */
    public DefaultResponse<String> secondBlockHandler(BlockException ex) {
        log.warn("secondBlockHandler", ex);
        return DefaultResponse.fail(ReturnCodeEnum.FAIL);
    }

    /**
     * 当方法执行过程中抛出异常（非 Sentinel 自身限流/降级）时触发
     * @param throwable 异常
     * @return 返回结果
     */
    public DefaultResponse<String> secondFallback(Throwable throwable) {
        log.error("secondFallback", throwable);
        return DefaultResponse.fail(ReturnCodeEnum.SERVER_ERROR);
    }
}
