package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 手机
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 手机编号
     */
    private String phoneCode;

    /**
     * 手机名称
     */
    private String name;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 生产日期
     */
    private LocalDate prodDate;

    /**
     * 售价
     */
    private BigDecimal price;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime updatedTime;

}
