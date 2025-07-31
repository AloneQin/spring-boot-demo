package com.example.demo.utils;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合工具类
 */
public class SmartCollectionUtils extends CollectionUtils {

    /**
     * 集合是否不为空
     * @param collection 待校验集合
     * @return true=不为空、false=空
     */
    public static boolean nonEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Map 是否不为空
     * @param map 待校验 Map
     * @return true=不为空、false=空
     */
    public static boolean nonEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 查找集合第一个元素
     * @param collection 待查找集合
     * @return 第一个元素 or null
     */
    public static Object findFirst(Collection<?> collection) {
        return collection.stream().findFirst().orElse(null);
    }

    /**
     * 查找集合第最后一个元素
     * @param collection 待查找集合
     * @return 最后一个元素 or null
     */
    public static Object findLast(Collection<?> collection) {
        return collection.stream().reduce((first, second) -> second).orElse(null);
    }

    /**
     * 初始化集合<code>List<code/>
     * @param elements 待初始化的元素数组
     * @return 初始化后的集合
     */
    @SafeVarargs
    public static <T> List<T> initList(T... elements) {
        if (Objects.isNull(elements)) {
            return Collections.emptyList();
        }
        return Stream.of(elements).collect(Collectors.toList());
    }

    /**
     * 初始化集合<code>Set<code/>
     * @param elements 待初始化的元素数组
     * @return 集合
     */
    @SafeVarargs
    public static <T> Set<T> initSet(T... elements) {
        if (Objects.isNull(elements)) {
            return Collections.emptySet();
        }
        return Stream.of(elements).collect(Collectors.toSet());
    }

    /**
     * 初始化集合<code>Map<code/>
     * @param entries 待初始化的元素数组
     * @return 集合
     */
    @SafeVarargs
    public static <K, V> Map<K, V> initMap(Map.Entry<? extends K, ? extends V>... entries) {
        if (Objects.isNull(entries)) {
            return Collections.emptyMap();
        }
        return Stream.of(entries).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
