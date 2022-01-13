package com.example.demo.model.vo;

import com.example.demo.common.metadata.constant.MsgConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel("手机视图对象")
public class PhoneVo {

    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "ID（自增长）", required = false, example = "1")
    private Integer id;

    /**
     * 手机编号
     */
    @NotNull(message = MsgConst.NOT_NULL_MSG)
    @ApiModelProperty(name = "phoneCode", value = "手机编号", required = true, example = "24d3901c-42a4-4584-92f1-af835e1a2efa")
    private String phoneCode;

    /**
     * 手机名称
     */
    @ApiModelProperty(name = "name", value = "名称", required = true, example = "iphone 12")
    @NotNull(message = MsgConst.NOT_NULL_MSG)
    private String name;

    /**
     * 品牌
     */
    @NotNull(message = MsgConst.NOT_NULL_MSG)
    @ApiModelProperty(name = "brand", value = "品牌", required = true, example = "apple")
    private String brand;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = MsgConst.NOT_NULL_MSG)
    @ApiModelProperty(name = "prodDate", value = "生产日期", required = true, example = "2020-10-14")
    private LocalDate prodDate;

    /**
     * 售价
     */
    @NotNull(message = MsgConst.NOT_NULL_MSG)
    @ApiModelProperty(name = "price", value = "售价", required = true, example = "6299.00")
    private BigDecimal price;

    /**
     * 备注
     */
    @ApiModelProperty(name = "remark", value = "备注", required = false, example = "磁吸无线充电超方便")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createdTime", value = "创建时间", required = false, example = "2020-11-20 15:32:11")
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(name = "updatedTime", value = "修改时间", required = false, example = "2020-11-20 15:32:11")
    private LocalDateTime updatedTime;
}
