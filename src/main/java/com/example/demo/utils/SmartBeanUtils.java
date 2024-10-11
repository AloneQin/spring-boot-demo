package com.example.demo.utils;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 对象工具类
 */
@Slf4j
public class SmartBeanUtils {

    /**
     * 拷贝对象属性<br/>
     * 返回新对象，对象属性为基本类型及{@link String}时为深拷贝，为引用类型时为浅拷贝
     * @param source 源对象
     * @param targetSupplier 目标供应者
     * @param <T> 目标泛型
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
     * 拷贝集合中对象属性<br/>
     * 返回新集合、新集合元素，元素对象属性为基本类型及{@link String}时为深拷贝，为引用类型时为浅拷贝
     * @param sourceList 源集合
     * @param targetSupplier 目标供应者
     * @param <T> 目标泛型
     * @return 目标对象集合
     */
    public static <T> List<T> copyPropertiesList(List<?> sourceList, Supplier<T> targetSupplier) {
        if (CollectionUtils.isEmpty(sourceList) || Objects.isNull(targetSupplier)) {
            return Collections.emptyList();
        }
        return sourceList.stream()
                .map(source -> copyProperties(source, targetSupplier))
                .collect(Collectors.toList());
    }

    /**
     * 拷贝分页对象中的对象属性<br/>
     * 返回新分页对象、新集合、新集合元素，元素对象属性为基本类型及{@link String}时为深拷贝，为引用类型时为浅拷贝
     * @param sourcePage 源分页对象
     * @param targetSupplier 目标供应者
     * @param <T> 目标泛型
     * @return 目标分页对象
     */
    public static <T> Page<T> copyPropertiesPage(Page<?> sourcePage, Supplier<T> targetSupplier) {
        if (Objects.isNull(sourcePage) || Objects.isNull(targetSupplier)) {
            return null;
        }
        return Objects.requireNonNull(copyProperties(sourcePage, Page<T>::new))
                .setRecords(copyPropertiesList(sourcePage.getRecords(), targetSupplier));
    }

    /**
     * 拷贝泛型对象属性<br/>
     * 支持对象泛型多层嵌套，并且均为深拷贝
     * @param source 目标对象
     * @param typeReference 目标泛型类型引用
     * @param <T> 目标泛型
     * @return 目标对象
     */
    public static <T> T copyPropertiesGeneric(Object source, TypeReference<T> typeReference) {
        return FastjsonUtils.toObject(FastjsonUtils.toString(source), typeReference);
    }

    /**
     * 拷贝非空的对象属性<br/>
     * 返回新对象，浅拷贝
     * @param source 源对象
     * @param target 目标对象
     * @param <T> 对象类型
     */
    public static <T> void copyValueNonNull(T source, T target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        Class<?> aClass = source.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            try {
                field.setAccessible(true);
                if (Modifier.isFinal(field.getModifiers())) {
                    return;
                }
                Object newValue = field.get(source);
                if (Objects.nonNull(newValue)) {
                    field.set(target, newValue);
                }
            } catch (IllegalAccessException e) {
                log.error("#copyValueNonNull, catch exception", e);
            }
        });
    }
}