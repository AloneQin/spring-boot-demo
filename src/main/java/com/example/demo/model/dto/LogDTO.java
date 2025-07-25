package com.example.demo.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LogDTO extends BaseDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

}
