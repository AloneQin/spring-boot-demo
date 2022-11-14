package com.example.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "汽车视图对象", description = "对应汽车实体")
public class CarVO {

    @ApiModelProperty(name = "brand", value = "品牌", required = true, dataType = "string", example = "BMW")
    private String brand;

    @ApiModelProperty(name = "color", value = "颜色", required = true, dataType = "string", example = "red")
    private String color;

}
