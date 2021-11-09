package com.example.demo.controller;

import com.example.demo.common.metadata.constant.Constant;
import com.example.demo.common.response.DefaultResponse;
import com.example.demo.domain.vo.PhoneVo;
import com.example.demo.service.PhoneService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 手机控制器
 */

@Validated
@RestController
@RequestMapping("/phone")
@Api(tags = "phone API", description = "手机相关接口")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;


    @GetMapping("/phoneList")
    @ApiOperation(value = "多条件查询汽车分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "int", paramType = "query", example = "10"),
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true, dataType = "int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "name", value = "手机名称（支持模糊搜索）", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "brand", value = "手机品牌", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "手机备注（支持模糊搜索）", required = false, dataType = "string", paramType = "query"),
    })
    public DefaultResponse<PageInfo<PhoneVo>> getPhoneList(@NotNull(message = Constant.NOT_NULL_MSG)
                                                               @Min(value = Constant.MIN_1_VAL, message = Constant.MIN_1_MSG) Integer pageSize,
                                                           @NotNull(message = Constant.NOT_NULL_MSG)
                                                               @Min(value = Constant.MIN_1_VAL, message = Constant.MIN_1_MSG) Integer pageNum,
                                                           String name,
                                                           String brand,
                                                           String remark) {
        return DefaultResponse.success(phoneService.getPhoneList(pageSize, pageNum, name, brand, remark));
    }

    @PostMapping("/phone")
    @ApiOperation(value = "添加手机")
    public DefaultResponse addPhone(@RequestBody @Validated PhoneVo phoneVo) {
        phoneService.addPhone(phoneVo);
        return DefaultResponse.success();
    }

    @PutMapping("/phone")
    @ApiOperation(value = "修改手机")
    public DefaultResponse modifyPhone(@RequestBody @Validated PhoneVo phoneVo) {
        phoneService.modifyPhone(phoneVo);
        return DefaultResponse.success();
    }

    @DeleteMapping("/phone")
    @ApiOperation(value = "删除手机")
    public DefaultResponse removePhone(@NotNull(message = Constant.NOT_NULL_MSG)
                                           @Min(value = Constant.MIN_1_VAL, message = Constant.MIN_1_MSG) Integer id) {
        phoneService.removePhone(id);
        return DefaultResponse.success();
    }
}