package com.example.demo.common.sensitive.valueconvert;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.common.sensitive.SensitiveEnum;
import com.example.demo.utils.SmartBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 值域转换者工具类
 */
@Slf4j
public class ValueConverter {

    /**
     * 类型转换器
     */
    private static final TypeConverter TYPE_CONVERTER = new SimpleTypeConverter();

    /**
     * 可处理的字段类型列表
     */
    private static final List<? extends Class<? extends Serializable>> HANDLE_TYPE_LIST = Arrays.asList(
            Boolean.class,
            Character.class,
            Number.class,
            String.class
    );

    /**
     * 对象属性转换
     * @param t 输入对象
     * @param <T> 对象泛型
     */
    public static <T> void convert(T t) {
        if (Objects.isNull(t)) {
            return;
        }
        boolean classAnnotationExistsFlag = t.getClass().isAnnotationPresent(ValueConvert.class);
        ValueConvert classAnnotation = t.getClass().getAnnotation(ValueConvert.class);

        // 默认取本类字段，如果有类注解且作用于父类字段，则取所有字段
        List<Field> fields = (classAnnotationExistsFlag && classAnnotation.includeSuperClass())
                ? FieldUtils.getAllFieldsList(t.getClass())
                : Arrays.stream(t.getClass().getDeclaredFields()).collect(Collectors.toList());

        for (Field field : fields) {
            // 如果字段本身有注解，则优先使用字段注解
            if (field.isAnnotationPresent(ValueConvert.class)) {
                ValueConvert fieldAnnotation = field.getAnnotation(ValueConvert.class);
                handleField(t, field, fields, fieldAnnotation, "field");
                continue;
            }

            // 如果没有字段注解，则使用类注解
            if (classAnnotationExistsFlag) {
                handleField(t, field, fields, classAnnotation, "class");
            }
        }
    }

    /**
     * 拷贝对象，并转换字段
     * 对象属性为基本类型及{@link String}时为深拷贝，为引用类型时为浅拷贝，效率高，但只能用于简单对象的拷贝（非嵌套对象）
     * @param t 输入对象
     * @param targetSupplier 目标供应者
     * @return 转换后的对象
     * @param <T> 对象泛型
     */
    public static <T> T copyAndConvert(T t, Supplier<T> targetSupplier) {
        T newT = SmartBeanUtils.copyProperties(t, targetSupplier);
        convert(newT);
        return newT;
    }


    /**
     * 拷贝对象，并转换字段
     * 效率低，深拷贝，用于复杂对象拷贝
     * @param source 目标对象
     * @param typeReference 目标泛型类型引用
     * @return 转换后的对象
     * @param <T> 对象泛型
     */
    public static <T> T copyAndConvert(Object source, TypeReference<T> typeReference) {
        T t = SmartBeanUtils.copyPropertiesGeneric(source, typeReference);
        convert(t);
        return t;
    }

    /**
     * 处理字段
     * @param t 目标对象
     * @param field 字段
     * @param fields 类的所有字段
     * @param annotation 注解
     * @param annotationType 注解类型
     * @param <T> 对象泛型
     */
    private static <T> void handleField(T t, Field field, List<Field> fields, ValueConvert annotation, String annotationType) {
        try {
            boolean needConvert = HANDLE_TYPE_LIST.stream()
                    .anyMatch(e -> e.isAssignableFrom(field.getType()));
            if (!needConvert) {
                return;
            }
            handleSetValue(t, field, fields, annotation);
            handleSensitive(t, field, annotation);
        } catch (Exception e) {
            log.error("#对象属性转换出错, 类: {}, 字段: {}, 注解类型: {}, 赋值类型: {}, 字段类型: {}, 赋值: {}", t.getClass().getName(), field.getName(), annotationType, annotation.type(), field.getType(), annotation.value());
            throw new RuntimeException(e);
        }
    }

    private static <T> void handleSetValue(T t, Field field, List<Field> fields, ValueConvert annotation) throws IllegalAccessException {
        // 获取注解信息
        SetValueTypeEnum setValueTypeEnum = annotation.type();
        String matchValue = annotation.matchValue();
        String targetField = annotation.targetField();
        String value = annotation.value();

        // 获取字段相关信息
        field.setAccessible(true);
        Object fieldValue = field.get(t);
        Class<?> fieldType = field.getType();
        switch (setValueTypeEnum) {
            case NULL:
                if (Objects.isNull(fieldValue)) {
                    field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                }
                break;
            case STR_EMPTY:
                if (String.class.isAssignableFrom(fieldType) && Objects.equals(fieldValue, "")) {
                    field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                }
                break;
            case STR_BLANK:
                if (String.class.isAssignableFrom(fieldType) && StringUtils.isNotEmpty((CharSequence) fieldValue) && StringUtils.isBlank((CharSequence) fieldValue)) {
                    field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                }
                break;
            case STR_IS_EMPTY:
                if (String.class.isAssignableFrom(fieldType) && StringUtils.isEmpty((CharSequence) fieldValue)) {
                    field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                }
                break;
            case STR_IS_BLANK:
                if (String.class.isAssignableFrom(fieldType) && StringUtils.isBlank((CharSequence) fieldValue)) {
                    field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                }
                break;
            case ZERO:
                if (Number.class.isAssignableFrom(fieldType) && Objects.equals(fieldValue, TYPE_CONVERTER.convertIfNecessary(0, fieldType))) {
                    field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                }
                break;
            case ANY:
                field.set(t, value);
                break;
            case SELF_MATCH:
                if (Objects.nonNull(fieldValue) && Objects.equals(fieldValue.toString(), matchValue)) {
                    field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                }
                break;
            case FIELD:
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (!Objects.equals(f.getName(), targetField)) {
                        continue;
                    }
                    Object targetFieldValue = f.get(t);
                    if (Objects.nonNull(targetFieldValue) && Objects.equals(targetFieldValue.toString(), matchValue)) {
                        field.setAccessible(true);
                        field.set(t, TYPE_CONVERTER.convertIfNecessary(value, fieldType));
                    }
                }
                break;
        }
    }

    private static <T> void handleSensitive(T t, Field field, ValueConvert annotation) throws IllegalAccessException {
        SensitiveEnum sensitiveEnum = annotation.sensitive();
        field.setAccessible(true);
        Class<?> fieldType = field.getType();
        Object fieldValue = field.get(t);
        if (!Objects.equals(SensitiveEnum.NONE, sensitiveEnum)) {
            if (Objects.equals(String.class, fieldType)) {
                field.set(t, sensitiveEnum.strategy.apply((String) fieldValue));
            }
        }
    }
}
