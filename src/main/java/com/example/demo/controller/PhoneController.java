package com.example.demo.controller;

import com.alicp.jetcache.anno.support.CacheContext;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.context.SystemContext;
import com.example.demo.common.metadata.constant.MsgConst;
import com.example.demo.common.response.ResponseFormat;
import com.example.demo.common.trace.TraceContext;
import com.example.demo.model.dto.PhoneDTO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.model.vo.PhoneVO;
import com.example.demo.service.PhoneService;
import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 手机控制器
 */
@Slf4j
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

    @GetMapping("/testLocalCache2")
    public void testLocalCache2() {
        PhonePO phonePO = phoneService.testLocalCache2();
        System.out.println(FastjsonUtils.toString(phonePO));
    }

    @GetMapping("/testLocalCache3")
    public void testLocalCache3() {
        PhonePO phonePO = phoneService.testLocalCache3(1);
        System.out.println(FastjsonUtils.toString(phonePO));
    }

    @GetMapping("/testRemoteCache")
    public void testRemoteCache() {
        PhonePO phonePO = phoneService.testRemoteCache(new PhonePO().setId(6));
        System.out.println("testRemoteCache: " + FastjsonUtils.toString(phonePO));
    }

    @GetMapping("/testRemoteCache2")
    public void testBothCache2() {
        List<PhonePO> list = phoneService.testRemoteListCache("iphone");
        System.out.println("testRemoteListCache-class: " + list.get(0).getClass());
        System.out.println("testRemoteListCache: " + FastjsonUtils.toString(list));
    }

    @GetMapping("/testRemoteCache3")
    public void testBothCache3(Integer i) {
        PhonePO phonePO = null;
        if (i % 2 == 1) {
            // 不启用缓存
            phonePO = phoneService.testRemoteCacheEnabled(new PhonePO().setId(3));
        } else {
            // 通过 enableCache 启用传参
            phonePO = CacheContext.enableCache(() -> phoneService.testRemoteCacheEnabled(new PhonePO().setId(3)));
        }
        System.out.println("testRemoteCache3: " + FastjsonUtils.toString(phonePO));
    }

    @GetMapping("/testRemoteCache4")
    public void testBothCache4(Integer i) {
        PhonePO phonePO = phoneService.testRemoteCacheCondition(new PhonePO().setId(3), i % 2 == 1);;
        System.out.println("testBothCache4: " + FastjsonUtils.toString(phonePO));
    }

    @GetMapping("/testRemoteCache5")
    public void testRemoteCache5(String phoneCode) {
        phoneService.testRemoteCacheUpdate(new PhonePO().setId(6).setPhoneCode(phoneCode));
    }

    @GetMapping("/testBothCache")
    public void testBothCache() {
        PhonePO phonePO = phoneService.testBothCache(new PhonePO().setId(2));
        System.out.println("testBothCache: " + FastjsonUtils.toString(phonePO));
    }

    @GetMapping("/testAsyncHandleDb")
    public Page<PhoneVO> testAsyncHandleDb() {
        Page<PhoneDTO> phoneDTOPage = phoneService.getPhoneList(10, 1, null, null, null);

        // 在不同线程中传递上下文
        Map<String, String> map = TraceContext.getCopyOfContextMap();
        ConcurrentHashMap<String, Object> contextMap = SystemContext.getDeepCopyOfContextMap();

        new Thread(() -> {
            try {
                TraceContext.setContextMap(map);
                SystemContext.setContextMap(contextMap);
                Thread.sleep(1L);
                PhoneDTO phoneDTO = phoneService.getPhoneById(1);
                log.info("phoneDTO: {}", FastjsonUtils.toString(phoneDTO));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        return SmartBeanUtils.copyPropertiesPage(phoneDTOPage, PhoneVO::new);
    }
}