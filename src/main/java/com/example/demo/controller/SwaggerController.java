package com.example.demo.controller;

import com.example.demo.common.response.DefaultResponse;
import com.example.demo.domain.vo.CarVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger")
@Api(tags = "swagger API", description = "接口文档相关测试与展示")
public class SwaggerController {

    @GetMapping("/car")
    @ApiOperation(value = "查询汽车信息", notes = "根据品牌查询汽车")
    @ApiImplicitParams({
            /**
             * paramType：查询参数类型
             *      - query = 直接跟参数完成自动映射赋值（默认）
             *      - path = 以地址的形式提交数据
             *      - body = 以流的形式提交，仅支持 POST
             *      - header = 参数包含在请求头中
             *      - form = 以表单的形式提交，仅支持 POST
             */
            @ApiImplicitParam(name = "brand", value = "品牌", required = true, dataType = "string", paramType = "query" ,example = "BMW")
    })
    public DefaultResponse<CarVo> getCar(String brand) {

        CarVo carVo = new CarVo("BMW", "red");
        if (carVo.getBrand().equals(brand)) {
            return DefaultResponse.success(carVo);
        }

        return DefaultResponse.success();
    }

    @PostMapping("/car")
    @ApiOperation(value = "保存汽车信息")
    public DefaultResponse saveCar(@RequestBody CarVo carVo) {
        // save car ...

        return DefaultResponse.success();
    }

}
