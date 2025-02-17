package com.example.demo.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 反射工具类
 */
public class ReflectUtils {

    /**
     * 根据类全路径获取类型
     * @param fullPath 全路径
     * @return 类类型
     * @throws ClassNotFoundException 类不存在异常
     */
    public static Class<?> getClazz(String fullPath) throws ClassNotFoundException {
        return Class.forName(fullPath);
    }

    /**
     * 获取类所有属性
     * @param clazz 类类型
     * @return 属性数组
     */
    public static Field[] getAllField(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }

    /**
     * 根据属性名称获取属性
     * @param clazz 类类型
     * @param fieldName 属性名称
     * @return 属性
     * @throws NoSuchFieldException 字段不存在异常
     */
    public static Field getFieldByName(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    /**
     * 获取对象字段值
     * @param obj 目标对象
     * @param fieldName 字段名称
     * @return 字段值
     * @throws NoSuchMethodException 方法不存在异常
     * @throws InvocationTargetException 方法执行异常
     * @throws IllegalAccessException 方法访问异常
     * @throws NoSuchFieldException 字段不存在异常
     */
    public static Object getFieldValue(Object obj, String fieldName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Field field = getFieldByName(obj.getClass(), fieldName);
        String getMethodName = getFieldGetterMethodName(field);
        Method method = getMethodByName(obj.getClass(), getMethodName, null);
        return getMethodReturnValue(method, obj);
    }

    /**
     * 获取属性的 Getter 方法名称，以 IntelliJ IDEA 为准，如果属性是 Boolean，方法名会为 isXxx()
     * @param field 目标属性
     * @return 该属性的 Getter 方法名称
     */
    public static String getFieldGetterMethodName(Field field) {
        boolean isBoolean = field.getGenericType().getTypeName().equalsIgnoreCase(Boolean.class.getName());
        if (isBoolean) {
            return "is" + initialToUpperCase(field.getName());
        } else {
            return "get" + initialToUpperCase(field.getName());
        }
    }

    /**
     * 将字符串首字母大写
     * @param source 原字符串
     * @return 首字符大写后的字符串
     */
    private static String initialToUpperCase(String source) {
        if (Objects.isNull(source)) {
            return null;
        }
        if (source.isEmpty()) {
            return "";
        }
        byte[] bytes = source.getBytes();
        bytes[0] = (byte) (bytes[0] - 'a' + 'A');
        return new String(bytes);
    }

    /**
     * 获取类所有的方法
     * @param clazz 类类型
     * @return 方法数组
     */
    public static Method[] getAllMethod(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    /**
     * 根据方法名称获取方法
     * @param clazz 类类型
     * @param methodName 方法名称
     * @param parameterTypes 方法入参（null=无入参）
     * @return 方法
     * @throws NoSuchMethodException 方法不存在异常
     */
    public static Method getMethodByName(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getDeclaredMethod(methodName, parameterTypes);
    }

    /**
     * 获取方法返回值
     * @param method 方法
     * @param obj 目标对象
     * @return 方法返回值
     * @throws InvocationTargetException 方法执行异常
     * @throws IllegalAccessException 方法访问异常
     */
    public static Object getMethodReturnValue(Method method, Object obj) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj);
    }

    /**
     * 根据无参构造器创建对象
     * @param clazz 类类型
     * @return 对象
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 访问异常
     */
    public static Object newInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    /**
     * 根据有参构造器创建对象
     * @param clazz 类类型
     * @param parameterTypes 参数类型
     * @param initArgs 参数值
     * @return 目标对象
     * @throws NoSuchMethodException 方法不存在异常
     * @throws InvocationTargetException 方法执行异常
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 访问异常
     */
    public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object[] initArgs)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
        return constructor.newInstance(initArgs);
    }
}
