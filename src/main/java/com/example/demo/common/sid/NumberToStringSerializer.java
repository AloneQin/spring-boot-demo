package com.example.demo.common.sid;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * 数字转字符串序列化器
 */
public class NumberToStringSerializer extends JsonSerializer<Number> {
    @Override
    public void serialize(Number value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.nonNull(value)) {
            jsonGenerator.writeString(value.toString());
        } else {
            jsonGenerator.writeNull();
        }
    }
}
