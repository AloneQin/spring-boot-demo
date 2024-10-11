package com.example.demo.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DemoVO {

    private String f1;

    private String f2;

    private Integer f3;

    private List<DemoVO> list;

}
