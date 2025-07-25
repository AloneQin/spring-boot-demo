package com.example.demo.common.sensitive.valueconvert;

import com.example.demo.common.sensitive.SensitiveEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 值域转换注解
 * 对属性值域进行增强处理, 包括：赋值、脱敏等等
 * 当注解同时使用在类和属性上时，优先级属性大于类
 * 注意：用于类上时，注解会应用于所有属性，必须保证属性类型与赋值类型一致，慎用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ValueConvert {

    /**
     * 是否包含父类属性，仅适用于类注解
     * @return boolean
     */
    boolean includeSuperClass() default false;

    /**
     * 赋值类型
     * @return SetValueTypeEnum
     */
    SetValueTypeEnum type() default SetValueTypeEnum.NULL;

    /**
     * 匹配某值时赋值
     * 当{@link ValueConvert#type()}取值为{@link SetValueTypeEnum#SELF_MATCH}使，表示以本属性值作为匹配条件
     * 当{@link ValueConvert#type()}取值为{@link SetValueTypeEnum#FIELD}使，表示以目标属性值作为匹配条件
     * @return String
     */
    String matchValue() default "";

    /**
     * 根据目标字段来赋值, 必须结合{@link ValueConvert#type()}取值为{@link SetValueTypeEnum#FIELD}使用
     * @return String
     */
    String targetField() default "";

    /**
     * 赋予什么值
     * @return String
     */
    String value() default "";

    /**
     * 脱敏策略
     * 脱敏的优先级晚于赋值, 当同时存在时, 先赋值, 再脱敏
     * @return SensitiveEnum
     */
    SensitiveEnum sensitive() default SensitiveEnum.NONE;

}
