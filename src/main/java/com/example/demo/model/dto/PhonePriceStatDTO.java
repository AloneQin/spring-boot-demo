package com.example.demo.model.dto;

import com.example.demo.common.function.SFunction;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 手机价格统计
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PhonePriceStatDTO {

    /**
     * 价格区间
     * 6k+
     * 5-6k
     * 4-5k
     * 0-4k
     */
    private String priceRange;

    /**
     * 数量
     */
    private Long num;

    /**
     * 总数
     */
    private Long total;

    /**
     * 百分比
     */
    private String percent;

    public PhonePriceStatDTO(Map<String, Object> map) {
        this.priceRange = (String) map.get(SFunction.getFieldName(PhonePriceStatDTO::getPriceRange, SFunction.FormatCastEnum.CAMEL_2_UNDERLINE));
        this.num = (Long) map.get(SFunction.getFieldName(PhonePriceStatDTO::getNum));
        this.total = (Long) map.get(SFunction.getFieldName(PhonePriceStatDTO::getTotal));
        this.percent = (String) map.get(SFunction.getFieldName(PhonePriceStatDTO::getPercent));
    }
}
