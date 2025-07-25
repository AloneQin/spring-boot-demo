package com.example.demo.common.sensitive.valueconvert;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 赋值类型枚举
 */
@AllArgsConstructor
public enum SetValueTypeEnum {

    /**
     * 为空时赋值
     */
    NULL,

    /**
     * 为空字符串时赋值
     */
    STR_EMPTY,

    /**
     * 为空格字符串时赋值
     */
    STR_BLANK,

    /**
     * 为<code>null<code/>或空字符串时赋值
     * @see StringUtils#isEmpty(CharSequence)
     */
    STR_IS_EMPTY,

    /**
     * 为<code>null<code/>或空字符串或空格字符串时赋值
     * @see StringUtils#isBlank(CharSequence)
     */
    STR_IS_BLANK,

    /**
     * 数值为{@code 0}时赋值
     */
    ZERO,

    /**
     * 任意情况赋值
     */
    ANY,

    /**
     * 本字段自匹配时赋值
     */
    SELF_MATCH,

    /**
     * 指定字段赋值
     */
    FIELD,
    ;

}
