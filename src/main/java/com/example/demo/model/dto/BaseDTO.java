package com.example.demo.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BaseDTO {

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 修改时间
     */
    private LocalDateTime updatedTime;

    /**
     * 修改人
     */
    private String updatedBy;

}
