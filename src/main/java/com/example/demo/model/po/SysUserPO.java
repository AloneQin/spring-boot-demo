package com.example.demo.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author alone
 * @since 2025-07-24
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUserPO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 性别: 0-女, 1-男
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 修改时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;

    /**
     * 修改人
     */
    @TableField("updated_by")
    private String updatedBy;


}
