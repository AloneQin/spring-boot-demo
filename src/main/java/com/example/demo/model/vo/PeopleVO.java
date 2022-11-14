package com.example.demo.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class PeopleVO {

    @NotNull(message = "姓名不能为空")
    @Length(min = 1, max = 10, message = "名称长度必须在1-10之间")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Range(min = 1, max = 150, message = "年龄必须在[1-150]之间")
    private Integer age;

}
