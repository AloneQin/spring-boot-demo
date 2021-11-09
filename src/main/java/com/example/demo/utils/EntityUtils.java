package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 实体工具类
 */
@Slf4j
public class EntityUtils {

    /**
     * 拷贝对象
     * @param source 源对象
     * @param targetSupplier 目标对象提供者
     * @param <T> 对象类型
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, Supplier<T> targetSupplier) {
        if (Objects.isNull(source) || Objects.isNull(targetSupplier)) {
            return null;
        }
        T target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 拷贝List
     * @param sourceList 源List
     * @param targetSupplier 目标对象提供者
     * @param <T> 对象类型
     * @return 目标List
     */
    public static <T> List<T> copyList(List<?> sourceList, Supplier<T> targetSupplier) {
        if (CollectionUtils.isEmpty(sourceList) || Objects.isNull(targetSupplier)) {
            return Collections.emptyList();
        }
        return sourceList.stream()
                .map(source -> copyProperties(source, targetSupplier))
                .collect(Collectors.toList());
    }

    /**
     * 复制源对象非空的值到目标对象
     * @param fromObject 源对象
     * @param toObject 目标对象
     * @param <T> 对象类型
     */
    public static <T> void copyValueNonNull(T fromObject, T toObject) {
        if (Objects.isNull(fromObject) || Objects.isNull(toObject)) {
            return;
        }

        Class<?> aClass = fromObject.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            field.setAccessible(true);
            try {
                Object newValue = field.get(fromObject);
                if (Objects.nonNull(newValue)) {
                    field.set(toObject, newValue);
                }
            } catch (IllegalAccessException e) {
                log.error("copyValueNonNull catch", e);
            }
        });
    }
}
