package com.example.demo.model.vo;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
public class WorkVO {

    @NotNull(message = "工作ID不能为空")
    private Integer id;

    @NotNull(message = "工作名称不能为空", groups = PeopleVO.Group1.class)
    private String name;

}
