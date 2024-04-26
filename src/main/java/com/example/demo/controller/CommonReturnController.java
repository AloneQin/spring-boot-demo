package com.example.demo.controller;

import com.example.demo.common.exception.ParamError;
import com.example.demo.common.exception.ParamValidatedException;
import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.MyReturnCode;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.model.vo.PeopleVO;
import com.example.demo.utils.AssertUtils;
import com.example.demo.utils.FastjsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

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
    public DefaultResponse<Void> success() {
        return DefaultResponse.success();
    }

    /**
     * 成功并携带数据返回展示
     */
    @GetMapping("/successWithData")
    public DefaultResponse<List<Integer>> successWithData() {
        return DefaultResponse.success(Arrays.asList(1, 2, 3));
    }

    /**
     * 通用失败返回展示
     * @return
     */
    @GetMapping("/fail")
    public DefaultResponse<Void> fail() {
        return DefaultResponse.fail();
    }

    /**
     * 自定义失败返回展示
     */
    @GetMapping("/customFail")
    public DefaultResponse<Void> customFail() {
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
    public DefaultResponse<Void> expectedException(String token) {
        AssertUtils.nonNull(token, ReturnCodeEnum.NEED_LOGIN, () -> log.warn("token is null"));

        return DefaultResponse.success();
    }

    /**
     * 子返回码异常返回展示
     */
    @GetMapping("/subReturnCodeException")
    public DefaultResponse<Void> subReturnCodeException(String token) {
        AssertUtils.nonNull(token, MyReturnCode.ORDER_STATUS_ERROR, () -> log.warn("token is null"));

        return DefaultResponse.success();
    }

    /**
     * 参数校验返回展示
     */
    @GetMapping("/validated")
    public DefaultResponse<Void> validated(@NotNull(message = "姓名不能为空")
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
    public DefaultResponse<Void> objectValidated(@Valid PeopleVO peopleVo) {
        log.info(FastjsonUtils.toString(peopleVo));

        return DefaultResponse.success();
    }

    /**
     * JSON 对象传参校验展示
     */
    @PostMapping("/jsonObjectValidated")
    public DefaultResponse<Void> jsonObjectValidated(@RequestBody @Valid PeopleVO peopleVo) {
        log.info(FastjsonUtils.toString(peopleVo));

        return DefaultResponse.success();
    }

    /**
     * 自定义参数校验返回展示
     */
    @GetMapping("/customValidated")
    public DefaultResponse<Void> customValidated(String name, Integer age) {

        AssertUtils.isTrue(name.startsWith("李"), new ParamValidatedException(new ParamError("name", "人名必须姓李")));

        AssertUtils.state(() -> age%2 == 0, new ParamValidatedException(List.of(new ParamError("age", "年龄不能为奇数"))));

        return DefaultResponse.success();
    }
}
