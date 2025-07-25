package com.example.demo.common.sid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.AllArgsConstructor;
import org.springframework.util.NumberUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 数字转字符串反序列化器
 */
@AllArgsConstructor
public class StringToNumberDeserializer extends JsonDeserializer<Number> {

    private JavaType targetType;

    @Override
    public Number deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        if (Objects.isNull(value)) {
            return null;
        }

        Class<?> rawClass = targetType.getRawClass();
        try {
            if (rawClass == Byte.class || rawClass == byte.class) {
                return NumberUtils.parseNumber(value, Byte.class);
            } else if (rawClass == Short.class || rawClass == short.class) {
                return NumberUtils.parseNumber(value, Short.class);
            } else if (rawClass == Integer.class || rawClass == int.class) {
                return NumberUtils.parseNumber(value, Integer.class);
            } else if (rawClass == Long.class || rawClass == long.class) {
                return NumberUtils.parseNumber(value, Long.class);
            } else if (rawClass == Float.class || rawClass == float.class) {
                return NumberUtils.parseNumber(value, Float.class);
            } else if (rawClass == Double.class || rawClass == double.class) {
                return NumberUtils.parseNumber(value, Double.class);
            } else if (Number.class.isAssignableFrom(rawClass)) {
                // 未知的 Number 子类，默认用 BigDecimal
                return NumberUtils.parseNumber(value, Number.class);
            } else {
                throw new IllegalArgumentException("Unsupported number type: " + rawClass.getName());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for field of type [" + rawClass.getSimpleName() + "]: " + value, e);
        }
    }
}
