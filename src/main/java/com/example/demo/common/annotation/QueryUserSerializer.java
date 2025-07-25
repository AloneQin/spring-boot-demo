package com.example.demo.common.annotation;

import com.example.demo.common.context.SpringContextHolder;
import com.example.demo.dao.mysql.wrapper.SysUserDAO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * 查询用户 JSON 序列化器
 */
@NoArgsConstructor
@AllArgsConstructor
public class QueryUserSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private String condition;

    private String result;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(value)) {
            jsonGenerator.writeNull();
            return;
        }
        value = queryUser(value);
        if (Objects.isNull(value)) {
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeString(value);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (Objects.isNull(beanProperty)) {
            return serializerProvider.findValueSerializer(String.class);
        }

        QueryUser annotation = beanProperty.getAnnotation(QueryUser.class);
        if (Objects.isNull(annotation)) {
            annotation = beanProperty.getContextAnnotation(QueryUser.class);
        }

        if (Objects.isNull(annotation)) {
            return serializerProvider.findValueSerializer(beanProperty.getType());
        }

        return new QueryUserSerializer(annotation.condition(), annotation.result());
    }

    public String queryUser(String value) {
        SysUserDAO sysUserDAO = SpringContextHolder.getBean(SysUserDAO.class);
        // 先查缓存
        String resultValue = UserMemoryCacheManager.getInstance().get(value);
        if (Objects.isNull(resultValue)) {
            // 缓存不存在查数据库
            resultValue = sysUserDAO.findResultColumnByConditionColumn(condition, value, result);
            if (Objects.isNull(resultValue)) {
                return null;
            }
            UserMemoryCacheManager.getInstance().put(value, resultValue);
        }
        return resultValue;
    }
}
