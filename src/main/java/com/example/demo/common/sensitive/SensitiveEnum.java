package com.example.demo.common.sensitive;

import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

/**
 * 敏感信息枚举
 */
@AllArgsConstructor
public enum SensitiveEnum {

    /**
     * 默认策略
     * 不做任何脱敏处理
     */
    NONE(s -> s),

    /**
     * 手机编号
     * 保留 4 位尾号，其他以 "*" 代替
     */
    PHONE_CODE(s -> {
        if (Objects.isNull(s) || s.length() <= 4) {
            return s;
        }
        String[] letterArr = s.split("");
        for (int i = 0; i < letterArr.length - 4; i++) {
            letterArr[i] = "*";
        }
        return String.join("", letterArr);
    });

    /**
     * 脱敏策略
     */
    public final Function<String, String> strategy;

    public static void main(String[] args) {
        String apply = SensitiveEnum.PHONE_CODE.strategy.apply("13812345678");
        System.out.println(apply);
    }
}
