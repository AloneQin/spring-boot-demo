package com.example.demo.controller;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.exception.ParamError;
import com.example.demo.common.exception.ParamValidatedException;
import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.MyReturnCode;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.model.vo.PeopleVo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 通用返回结构的运用
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/commonReturn")
public class CommonReturnController {

    /**
     * 通用成功返回展示
     */
    @GetMapping("/success")
    public DefaultResponse success() {
        return DefaultResponse.success();
    }

    /**
     * 成功并携带数据返回展示
     */
    @GetMapping("/successWithData")
    public DefaultResponse successWithData() {
        return DefaultResponse.success(Arrays.asList(1, 2, 3));
    }

    /**
     * 通用失败返回展示
     * @return
     */
    @GetMapping("/fail")
    public DefaultResponse fail() {
        return DefaultResponse.fail();
    }

    /**
     * 自定义失败返回展示
     */
    @GetMapping("/customFail")
    public DefaultResponse customFail() {
        return DefaultResponse.fail(ReturnCodeEnum.PERMISSION_DENIED);
    }

    /**
     * 发生不可预期异常返回展示
     */
    @GetMapping("/exception")
    public void exception() {
        String s = null;
        System.out.println(s.length());
    }

    /**
     * 发生可预期异常返回展示
     */
    @GetMapping("/expectedException")
    public DefaultResponse expectedException(String token) {
        if (token == null) {
            throw new BaseException(ReturnCodeEnum.NEED_LOGIN);
        }

        return DefaultResponse.success();
    }

    /**
     * 子返回码异常返回展示
     */
    @GetMapping("/subReturnCodeException")
    public DefaultResponse subReturnCodeException(String token) {
        if (token == null) {
            throw new BaseException(MyReturnCode.ORDER_STATUS_ERROR);
        }

        return DefaultResponse.success();
    }

    /**
     * 参数校验返回展示
     */
    @GetMapping("/validated")
    public DefaultResponse validated(@NotNull(message = "姓名不能为空")
                                        @Length(min = 1, max = 10, message = "名称长度必须在1-10之间")  String name,
                                     @NotNull(message = "年龄不能为空")
                                        @Range(min = 1, max = 150, message = "年龄必须在[1-150]之间") Integer age) {
        log.info("name: {}, age: {}", name, age);

        return DefaultResponse.success();
    }

    /**
     * 非 JSON 对象传参校验展示
     */
    @GetMapping("/objectValidated")
    public DefaultResponse objectValidated(@Validated PeopleVo peopleVo) {
        log.info(peopleVo.toString());

        return DefaultResponse.success();
    }

    /**
     * JSON 对象传参校验展示
     */
    @PostMapping("/jsonObjectValidated")
    public DefaultResponse jsonObjectValidated(@RequestBody @Validated PeopleVo peopleVo) {
        log.info(peopleVo.toString());

        return DefaultResponse.success();
    }

    /**
     * 自定义参数校验返回展示
     */
    @GetMapping("/customValidated")
    public DefaultResponse customValidated(String name, Integer age) {
        if (!name.startsWith("李")) {
            throw new ParamValidatedException(Arrays.asList(new ParamError("name", "人名必须姓李")));
        }

        if (age%2 != 0) {
            throw new ParamValidatedException(Arrays.asList(new ParamError("age", "年龄不能为奇数")));
        }

        return DefaultResponse.success();
    }
}
