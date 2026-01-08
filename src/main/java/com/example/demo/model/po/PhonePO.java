package com.example.demo.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 手机表
 * </p>
 *
 * @author alone
 * @since 2021-12-06
 */
@Data
@TableName("phone")
@Accessors(chain = true)
public class PhonePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 手机编号
     */
    @TableField("phone_code")
    private String phoneCode;

    /**
     * 手机名称
     */
    @TableField("name")
    private String name;

    /**
     * 品牌
     */
    @TableField("brand")
    private String brand;

    /**
     * 生产日期
     */
    @TableField("prod_date")
    private LocalDate prodDate;

    /**
     * 售价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;

}
