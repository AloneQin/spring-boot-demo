package com.example.demo.common.sensitive.valueconvert;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.model.vo.SetValVO;
import com.example.demo.utils.FastjsonUtils;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ValueConverterTest {

    public static void main(String[] args) {
        SetValVO setValVO = new SetValVO()
                .setF2("")
                .setF3(" ")
                .setF6(0)
                .setF7(0L)
                .setF8(0f)
                .setF9(0d)
                .setF10(new BigDecimal("0"))
                .setF12("self")
                .setF13("self")
                .setF14(-1001)
                .setF15(Stream.of("1", "2").collect(Collectors.toList()))
                .setF19("1234567890")
                ;

        SetValVO copy1 = ValueConverter.copyAndConvert(setValVO, SetValVO::new);
        SetValVO copy2 = ValueConverter.copyAndConvert(setValVO, new TypeReference<>() {});
        // 修改原对象嵌套属性，浅拷贝对象也会被修改
        setValVO.getF15().add("3");
        System.out.println(FastjsonUtils.toStringFormat(setValVO));
        System.out.println(FastjsonUtils.toStringFormat(copy1));
        System.out.println(FastjsonUtils.toStringFormat(copy2));
    }

}