package com.example.demo.common.context;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartBeanUtils;
import com.example.demo.utils.SmartStringUtils;
import lombok.AllArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统上下文
 */
public class SystemContext {

    private static final ThreadLocal<ConcurrentHashMap<String, Object>> SYSTEM_CONTEXT = ThreadLocal.withInitial(ConcurrentHashMap::new);

    /**
     * 获取当前线程上下文的大小
     * @return 上下文中键值对的数量
     */
    public static int size() {
        return SYSTEM_CONTEXT.get().size();
    }

    /**
     * 检查上下文中是否包含指定的键
     * @param kvEnum 键值对枚举
     * @return 是否包含
     */
    public static boolean containsKey(SystemContextKVEnum kvEnum) {
        return SYSTEM_CONTEXT.get().containsKey(kvEnum.key);
    }

    /**
     * 设置上下文中的值
     *
     * @param kvEnum 键值对枚举
     * @param value 值
     * @param <T> 值类型
     */
    public static <T> void set(SystemContextKVEnum kvEnum, T value) {
        if (kvEnum == null || value == null) {
            throw new IllegalArgumentException(SmartStringUtils.format("kvEnum or value is null, kvEnum: {}, value: {}", kvEnum, value));
        }
        if (!kvEnum.type.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("value type does not match the expected type for key: " + kvEnum.key);
        }
        SYSTEM_CONTEXT.get().put(kvEnum.key, value);
    }

    /**
     * 获取上下文中的值
     * @param kvEnum 键值对枚举
     * @return 值
     * @param <T> 值类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(SystemContextKVEnum kvEnum) {
        return (T) SYSTEM_CONTEXT.get().get(kvEnum.key);
    }

    /**
     * 获取上下文中的值，如果不存在则返回默认值
     * @param kvEnum 键值对枚举
     * @param defaultValue 默认值
     * @return 值
     * @param <T> 值类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(SystemContextKVEnum kvEnum, T defaultValue) {
        return (T) SYSTEM_CONTEXT.get().get(kvEnum.key);
    }

    /**
     * 获取当前线程上下文的深拷贝
     * @return 深拷贝后的上下文
     */
    public static ConcurrentHashMap<String, Object> getDeepCopyOfContextMap() {
        return SmartBeanUtils.copyPropertiesGeneric(SYSTEM_CONTEXT.get(), new TypeReference<>() {});
    }

    /**
     * 设置当前线程上下文的内容
     * @param map 上下文
     */
    public static void setContextMap(ConcurrentHashMap<String, Object> map) {
        SYSTEM_CONTEXT.get().putAll(map);
    }

    /**
     * 移除上下文中指定键的值
     * @param kvEnum 键值对枚举
     */
    public static void remove(SystemContextKVEnum kvEnum) {
        SYSTEM_CONTEXT.get().remove(kvEnum.key);
    }

    /**
     * 清空当前线程的上下文
     */
    public static void clear() {
        SYSTEM_CONTEXT.remove();
    }

    /**
     * 系统上下文键值对枚举
     */
    @AllArgsConstructor
    public enum SystemContextKVEnum {

        /**
         * 调试模式
         */
        DEBUG_MODE("S-DEBUG-MODE", Boolean.class),

        /**
         * 请求 URI
         */
        REQ_URI("REQ-URI", String.class),
        ;

        /**
         * 键名称
         */
        public final String key;

        /**
         * 值类型
         */
        public final Class<?> type;

    }

}
