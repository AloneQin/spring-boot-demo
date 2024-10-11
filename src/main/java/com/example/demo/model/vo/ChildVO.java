package com.example.demo.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChildVO {

    @NotNull(message = "小孩ID不能为空")
    private Integer id;

    @NotNull(message = "小孩名称不能为空", groups = PeopleVO.Group1.class)
    private String name;

    @NotNull(message = "小孩年龄不能为空", groups = PeopleVO.Group2.class)
    private Integer age;

}
