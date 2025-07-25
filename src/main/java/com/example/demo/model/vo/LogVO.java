package com.example.demo.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LogVO extends BaseVO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

}
