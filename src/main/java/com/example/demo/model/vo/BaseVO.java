package com.example.demo.model.vo;

import com.example.demo.common.annotation.QueryUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BaseVO {

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 创建人
     */
    @QueryUser
    private String createdBy;

    /**
     * 修改时间
     */
    private LocalDateTime updatedTime;

    /**
     * 修改人
     */
    @QueryUser
    private String updatedBy;

}
