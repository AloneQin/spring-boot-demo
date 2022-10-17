package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.metadata.constant.MsgConst;
import com.example.demo.common.response.ResultFormat;
import com.example.demo.model.dto.PhoneDTO;
import com.example.demo.model.vo.PhoneVo;
import com.example.demo.service.PhoneService;
import com.example.demo.utils.SmartBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 手机控制器
 */
@Validated
@ResultFormat
@RestController
@AllArgsConstructor
@RequestMapping("/phone")
@Api(tags = "phone API", description = "手机相关接口")
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping("/phoneList")
    @ApiOperation(value = "多条件查询汽车分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "int", paramType = "query", example = "10"),
            @ApiImplicitParam(name = "pageNum", value = "页号", required = true, dataType = "int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "name", value = "手机名称（支持模糊搜索）", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "brand", value = "手机品牌", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "手机备注（支持模糊搜索）", required = false, dataType = "string", paramType = "query"),
    })
    public Page<PhoneVo> getPhoneList(@NotNull(message = MsgConst.NOT_NULL_MSG)
                                          @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer pageSize,
                                      @NotNull(message = MsgConst.NOT_NULL_MSG)
                                          @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer pageNum,
                                      String name,
                                      String brand,
                                      String remark) {
        Page<PhoneDTO> phoneDTOPage = phoneService.getPhoneList(pageSize, pageNum, name, brand, remark);
        return SmartBeanUtils.copyPropertiesPage(phoneDTOPage, PhoneVo::new);
    }

    @GetMapping("/phone")
    public PhoneVo getPhoneById(@NotNull(message = MsgConst.NOT_NULL_MSG)
                                    @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer id) {
        PhoneDTO phoneDTO = phoneService.getPhoneById(id);
        return SmartBeanUtils.copyProperties(phoneDTO, PhoneVo::new);
    }

    @GetMapping("/phoneByName")
    public List<PhoneVo> getPhoneByName(@NotBlank(message = MsgConst.NOT_NULL_MSG) String name) {
        List<PhoneDTO> phoneDTOList = phoneService.getPhoneByName(name);
        return SmartBeanUtils.copyPropertiesList(phoneDTOList, PhoneVo::new);
    }

    @PostMapping("/phone")
    @ApiOperation(value = "添加手机")
    public void addPhone(@RequestBody @Validated PhoneVo phoneVo) {
        PhoneDTO phoneDTO = SmartBeanUtils.copyProperties(phoneVo, PhoneDTO::new);
        phoneService.addPhone(phoneDTO);
    }

    @PutMapping("/phone")
    @ApiOperation(value = "修改手机")
    public void modifyPhone(@RequestBody @Validated PhoneVo phoneVo) {
        PhoneDTO phoneDTO = SmartBeanUtils.copyProperties(phoneVo, PhoneDTO::new);
        phoneService.modifyPhone(phoneDTO);
    }

    @DeleteMapping("/phone")
    @ApiOperation(value = "删除手机")
    public void removePhone(@NotNull(message = MsgConst.NOT_NULL_MSG)
                                @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer id) {
        phoneService.removePhone(id);
    }

}