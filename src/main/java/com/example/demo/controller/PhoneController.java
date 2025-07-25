package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.metadata.constant.MsgConst;
import com.example.demo.common.response.ResponseFormat;
import com.example.demo.model.dto.PhoneDTO;
import com.example.demo.model.vo.PhoneVO;
import com.example.demo.service.PhoneService;
import com.example.demo.utils.SmartBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 手机控制器
 */
@Validated
@ResponseFormat
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
    public Page<PhoneVO> getPhoneList(@NotNull(message = MsgConst.NOT_NULL_MSG)
                                          @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer pageSize,
                                      @NotNull(message = MsgConst.NOT_NULL_MSG)
                                          @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer pageNum,
                                      String name,
                                      String brand,
                                      String remark) {
        Page<PhoneDTO> phoneDTOPage = phoneService.getPhoneList(pageSize, pageNum, name, brand, remark);
        return SmartBeanUtils.copyPropertiesPage(phoneDTOPage, PhoneVO::new);
    }

    @GetMapping("/phone")
    public PhoneVO getPhoneById(@NotNull(message = MsgConst.NOT_NULL_MSG)
                                    @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer id) {
        PhoneDTO phoneDTO = phoneService.getPhoneById(id);
        return SmartBeanUtils.copyProperties(phoneDTO, PhoneVO::new);
    }

    @GetMapping("/phoneByName")
    public List<PhoneVO> getPhoneByName(@NotBlank(message = MsgConst.NOT_NULL_MSG) String name) {
        List<PhoneDTO> phoneDTOList = phoneService.getPhoneByName(name);
        return SmartBeanUtils.copyPropertiesList(phoneDTOList, PhoneVO::new);
    }

    @GetMapping("/phoneByProdDate")
    public List<PhoneVO> getPhoneByProdDate(@NotNull(message = MsgConst.NOT_NULL_MSG) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate prodDate) {
        List<PhoneDTO> phoneDTOList = phoneService.getPhoneByProdDate(prodDate);
        return SmartBeanUtils.copyPropertiesList(phoneDTOList, PhoneVO::new);
    }

    @PostMapping("/phone")
    @ApiOperation(value = "添加手机")
    public void addPhone(@RequestBody @Valid PhoneVO phoneVo) {
        PhoneDTO phoneDTO = SmartBeanUtils.copyProperties(phoneVo, PhoneDTO::new);
        phoneService.addPhone(Objects.requireNonNull(phoneDTO));
    }

    @PutMapping("/phone")
    @ApiOperation(value = "修改手机")
    public void modifyPhone(@RequestBody @Valid PhoneVO phoneVo) {
        PhoneDTO phoneDTO = SmartBeanUtils.copyProperties(phoneVo, PhoneDTO::new);
        phoneService.modifyPhone(Objects.requireNonNull(phoneDTO));
    }

    @DeleteMapping("/phone")
    @ApiOperation(value = "删除手机")
    public void removePhone(@NotNull(message = MsgConst.NOT_NULL_MSG)
                                @Min(value = MsgConst.MIN_1_VAL, message = MsgConst.MIN_1_MSG) Integer id) {
        phoneService.removePhone(id);
    }

    /**
     * 测试事务失效
     */
    @GetMapping("/testTransactionLapse")
    public void testTransactionLapse() {
        phoneService.testTransactionLapse();
    }

    @GetMapping("/testLocalCache")
    public void testLocalCache() {
        phoneService.testLocalCache();
    }
}