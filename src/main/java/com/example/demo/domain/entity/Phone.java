package com.example.demo.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "phone")
public class Phone {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 手机编号
     */
    @Column(name = "phone_code")
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
    @Column(name = "prod_date")
    private Date prodDate;

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
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取手机编号
     *
     * @return phone_code - 手机编号
     */
    public String getPhoneCode() {
        return phoneCode;
    }

    /**
     * 设置手机编号
     *
     * @param phoneCode 手机编号
     */
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    /**
     * 获取手机名称
     *
     * @return name - 手机名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置手机名称
     *
     * @param name 手机名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取品牌
     *
     * @return brand - 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置品牌
     *
     * @param brand 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获取生产日期
     *
     * @return prod_date - 生产日期
     */
    public Date getProdDate() {
        return prodDate;
    }

    /**
     * 设置生产日期
     *
     * @param prodDate 生产日期
     */
    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    /**
     * 获取售价
     *
     * @return price - 售价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置售价
     *
     * @param price 售价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取修改时间
     *
     * @return updated_time - 修改时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置修改时间
     *
     * @param updatedTime 修改时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}