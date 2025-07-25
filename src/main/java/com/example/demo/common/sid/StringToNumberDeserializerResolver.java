package com.example.demo.common.sid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * 字符串转数字序列化器解析器
 */
public class StringToNumberDeserializerResolver extends StdDeserializer<Number> implements ContextualDeserializer {

    protected StringToNumberDeserializerResolver() {
        this(null);
    }

    protected StringToNumberDeserializerResolver(Class<? extends Number> vc) {
        super(vc);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            return new StringToNumberDeserializer(property.getType());
        }
        return new StringToNumberDeserializer(context.constructType(Number.class));
    }

    @Override
    public Number deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        // 此处不执行反序列化逻辑，具体的逻辑由 StringToNumberDeserializer 实现
        return null;
    }
}
