package com.example.demo.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PeopleVO {

    @NotNull(message = "姓名不能为空", groups = Group1.class)
    @Length(min = 1, max = 10, message = "名称长度必须在1-10之间", groups = Group1.class)
    private String name;

    @NotNull(message = "年龄不能为空", groups = Group2.class)
    @Range(min = 1, max = 150, message = "年龄必须在[1-150]之间", groups = Group2.class)
    private Integer age;

    @NotBlank(message = "地址不能为空", groups = {Group1.class, Group2.class})
    private String address;

    public interface Group1 {

    }

    public interface Group2 {

    }
}
