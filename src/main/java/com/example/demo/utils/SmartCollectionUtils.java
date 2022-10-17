package com.example.demo.utils;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

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
     * @param map
     * @return
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

}
