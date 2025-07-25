package com.example.demo.model.vo;

import com.example.demo.common.sensitive.SensitiveEnum;
import com.example.demo.common.sensitive.valueconvert.SetValueTypeEnum;
import com.example.demo.common.sensitive.valueconvert.ValueConvert;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
@ValueConvert(includeSuperClass = false, value = "global value")
public class SetValVO extends SetValParentVO {

    /**
     * 本字段为null时赋值为: global value
     */
    private String f0;

    /**
     * 本字段为null时赋值为: is null
     */
    @ValueConvert(value = "is null")
    private String f1;

    /**
     * 本字段为空串""时赋值为: empty
     */
    @ValueConvert(type = SetValueTypeEnum.STR_EMPTY, value = "empty")
    private String f2;

    /**
     * 本字段为空格字符串" "时赋值为: blank
     */
    @ValueConvert(type = SetValueTypeEnum.STR_BLANK, value = "blank")
    private String f3;

    /**
     * 本字段为null时赋值为: is empty
     */
    @ValueConvert(type = SetValueTypeEnum.STR_IS_EMPTY, value = "is empty")
    private String f4;

    /**
     * 本字段为null时赋值为: is blank
     */
    @ValueConvert(type = SetValueTypeEnum.STR_IS_BLANK, value = "is blank")
    private String f5;

    /**
     * 本字段为数字0时赋值为: -1001
     */
    @ValueConvert(type = SetValueTypeEnum.ZERO, value = "-1001")
    private Integer f6;

    /**
     * 本字段为数字0时赋值为: -1002
     */
    @ValueConvert(type = SetValueTypeEnum.ZERO, value = "-1002")
    private Long f7;

    /**
     * 本字段为数字0时赋值为: -1003
     */
    @ValueConvert(type = SetValueTypeEnum.ZERO, value = "-1003")
    private Float f8;

    /**
     * 本字段为数字0时赋值为: -1004
     */
    @ValueConvert(type = SetValueTypeEnum.ZERO, value = "-1004")
    private Double f9;

    /**
     * 本字段为数字0时赋值为: -1005
     */
    @ValueConvert(type = SetValueTypeEnum.ZERO, value = "-1005")
    private BigDecimal f10;

    /**
     * 本字段为任意值时赋值为: is any
     */
    @ValueConvert(type = SetValueTypeEnum.ANY, value = "is any")
    private String f11;

    /**
     * 本字段值=self时赋值为: is self match
     */
    @ValueConvert(type = SetValueTypeEnum.SELF_MATCH, matchValue = "self", value = "is self match")
    private String f12;

    /**
     * 目标字段f1的值=-1001时赋值为: field
     */
    @ValueConvert(type = SetValueTypeEnum.FIELD, targetField = "f14", matchValue = "-1001", value = "is field match")
    private String f13;

    private Integer f14;

    private List<String> f15;

    private LocalDate f16;

    @ValueConvert(value = "true")
    private Boolean f17;

    @ValueConvert(value = "\u0041")
    private Character f18;

    @ValueConvert(sensitive = SensitiveEnum.PHONE_CODE)
    private String f19;

}
