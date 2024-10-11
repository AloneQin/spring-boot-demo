package com.example.demo.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author alone
 * @since 2024-09-12
 */
@Getter
@Setter
@TableName("log")
@Accessors(chain = true)
public class LogPO extends BasePO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

//    /**
//     * 创建时间
//     */
//    @TableField("created_time")
//    private LocalDateTime createdTime;
//
//    /**
//     * 创建人
//     */
//    @TableField("created_by")
//    private String createdBy;
//
//    /**
//     * 修改时间
//     */
//    @TableField("updated_time")
//    private LocalDateTime updatedTime;
//
//    /**
//     * 修改人
//     */
//    @TableField("updated_by")
//    private String updatedBy;


}
