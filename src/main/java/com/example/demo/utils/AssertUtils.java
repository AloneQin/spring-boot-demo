package com.example.demo.utils;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 断言工具类
 * @param <E> {@link BaseException}
 * @param <T> {@link DefaultResponse#getContent()#getClass()}
 */
@Slf4j
public class AssertUtils<E extends BaseException, T, P> {

    /**
     * 抛出异常
     * @param baseException {@link BaseException}
     */
    public static void doThrow(BaseException baseException) {
        throw baseException;
    }

    /**
     * 抛出异常，并支持植入额外操作
     * @param baseException {@link BaseException}
     * @param runnable 需要植入的操作
     */
    public static void doThrow(BaseException baseException, Runnable runnable) {
        runnable.run();
        throw baseException;
    }

    /**
     * 抛出异常
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void doThrow(DefaultResponse<T> defaultResponse) {
        throw new BaseException(defaultResponse);
    }

    /**
     * 抛出异常，并支持植入额外操作
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void doThrow(DefaultResponse<T> defaultResponse, Runnable runnable) {
        runnable.run();
        throw new BaseException(defaultResponse);
    }

    /**
     * 抛出异常，并支持植入传递可变参数的额外操作
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void doThrow(DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        consumer.accept(params);
        throw new BaseException(defaultResponse);
    }

    /**
     * 抛出异常
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void doThrow(ReturnCodeEnum returnCodeEnum) {
        throw new BaseException(returnCodeEnum);
    }

    /**
     * 抛出异常，并支持植入额外操作
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void doThrow(ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        runnable.run();
        throw new BaseException(returnCodeEnum);
    }

    /**
     * 抛出异常，并支持植入传递可变参数的额外操作
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void doThrow(ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        consumer.accept(params);
        throw new BaseException(returnCodeEnum);
    }

    /**
     * 抛出异常
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void doThrow(String code, String message, T content) {
        throw new BaseException(code, message, content);
    }

    /**
     * 抛出异常，并支持植入额外操作
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void doThrow(String code, String message, T content, Runnable runnable) {
        runnable.run();
        throw new BaseException(code, message, content);
    }

    /**
     * 抛出异常，并支持植入传递可变参数的额外操作
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void doThrow(String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        consumer.accept(params);
        throw new BaseException(code, message, content);
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isNull(Object object, E e) {
        if (Objects.nonNull(object)) {
            doThrow(e);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isNull(Object object, E e, Runnable runnable) {
        if (Objects.nonNull(object)) {
            runnable.run();
            doThrow(e);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void isNull(Object object, E e, MultiConsumer<P> consumer, P... params) {
        if (Objects.nonNull(object)) {
            consumer.accept(params);
            doThrow(e);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isNull(Object object, DefaultResponse<T> defaultResponse) {
        if (Objects.nonNull(object)) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isNull(Object object, DefaultResponse<T> defaultResponse, Runnable runnable) {
        if (Objects.nonNull(object)) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isNull(Object object, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        if (Objects.nonNull(object)) {
            consumer.accept(params);
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void isNull(Object object, ReturnCodeEnum returnCodeEnum) {
        if (Objects.nonNull(object)) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void isNull(Object object, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        if (Objects.nonNull(object)) {
            runnable.run();
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void isNull(Object object, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        if (Objects.nonNull(object)) {
            consumer.accept(params);
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常
     * @param object 待判断对象
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isNull(Object object, String code, String message, T content) {
        if (Objects.nonNull(object)) {
            doThrow(code, message, content);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isNull(Object object, String code, String message, T content, Runnable runnable) {
        if (Objects.nonNull(object)) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断对象为<code>null</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isNull(Object object, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        if (Objects.nonNull(object)) {
            consumer.accept(params);
            doThrow(code, message, content);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void nonNull(Object object, E e) {
        if (Objects.isNull(object)) {
            doThrow(e);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void nonNull(Object object, E e, Runnable runnable) {
        if (Objects.isNull(object)) {
            runnable.run();
            doThrow(e);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void nonNull(Object object, E e, MultiConsumer<P> consumer, P... params) {
        if (Objects.isNull(object)) {
            consumer.accept(params);
            doThrow(e);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonNull(Object object, DefaultResponse<T> defaultResponse) {
        if (Objects.isNull(object)) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonNull(Object object, DefaultResponse<T> defaultResponse, Runnable runnable) {
        if (Objects.isNull(object)) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void nonNull(Object object, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        if (Objects.isNull(object)) {
            consumer.accept(params);
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void nonNull(Object object, ReturnCodeEnum returnCodeEnum) {
        if (Objects.isNull(object)) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void nonNull(Object object, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        if (Objects.isNull(object)) {
            runnable.run();
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void nonNull(Object object, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        if (Objects.isNull(object)) {
            consumer.accept(params);
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常
     * @param object 待判断对象
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonNull(Object object, String code, String message, T content) {
        if (Objects.isNull(object)) {
            doThrow(code, message, content);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonNull(Object object, String code, String message, T content, Runnable runnable) {
        if (Objects.isNull(object)) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断对象不为<code>null</code>，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param object 待判断对象
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void nonNull(Object object, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        if (Objects.isNull(object)) {
            consumer.accept(params);
            doThrow(code, message, content);
        }
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常
     * @param str 待判断字符串
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isEmpty(String str, E e) {
        isTrue(StringUtils.isEmpty(str), e);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isEmpty(String str, E e, Runnable runnable) {
        isTrue(StringUtils.isEmpty(str), e, runnable);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void isEmpty(String str, E e, MultiConsumer<P> consumer, P... params) {
        isTrue(StringUtils.isEmpty(str), e, consumer, params);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常
     * @param str 待判断字符串
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(String str, DefaultResponse<T> defaultResponse) {
        isTrue(StringUtils.isEmpty(str), defaultResponse);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(String str, DefaultResponse<T> defaultResponse, Runnable runnable) {
        isTrue(StringUtils.isEmpty(str), defaultResponse, runnable);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isEmpty(String str, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        isTrue(StringUtils.isEmpty(str), defaultResponse, consumer, params);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常
     * @param str 待判断字符串
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void isEmpty(String str, ReturnCodeEnum returnCodeEnum) {
        isTrue(StringUtils.isEmpty(str), returnCodeEnum);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void isEmpty(String str, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        isTrue(StringUtils.isEmpty(str), returnCodeEnum, runnable);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void isEmpty(String str, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        isTrue(StringUtils.isEmpty(str), returnCodeEnum, consumer, params);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常
     * @param str 待判断字符串
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(String str, String code, String message, T content) {
        isTrue(StringUtils.isEmpty(str), code, message, content);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(String str, String code, String message, T content, Runnable runnable) {
        isTrue(StringUtils.isEmpty(str), code, message, content, runnable);
    }

    /**
     * 判断字符串为<code>null</code>或空串，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isEmpty(String str, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        isTrue(StringUtils.isEmpty(str), code, message, content, consumer, params);
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常
     * @param str 待判断字符串
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void nonEmpty(String str, E e) {
        if (StringUtils.isEmpty(str)) {
            doThrow(e);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void nonEmpty(String str, E e, Runnable runnable) {
        if (StringUtils.isEmpty(str)) {
            runnable.run();
            doThrow(e);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void nonEmpty(String str, E e, MultiConsumer<P> consumer, P... params) {
        if (StringUtils.isEmpty(str)) {
            consumer.accept(params);
            doThrow(e);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常
     * @param str 待判断字符串
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(String str, DefaultResponse<T> defaultResponse) {
        if (StringUtils.isEmpty(str)) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(String str, DefaultResponse<T> defaultResponse, Runnable runnable) {
        if (StringUtils.isEmpty(str)) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void nonEmpty(String str, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        if (StringUtils.isEmpty(str)) {
            consumer.accept(params);
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常
     * @param str 待判断字符串
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void nonEmpty(String str, ReturnCodeEnum returnCodeEnum) {
        if (StringUtils.isEmpty(str)) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void nonEmpty(String str, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        if (StringUtils.isEmpty(str)) {
            runnable.run();
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void nonEmpty(String str, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        if (StringUtils.isEmpty(str)) {
            consumer.accept(params);
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常
     * @param str 待判断字符串
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(String str, String code, String message, T content) {
        if (StringUtils.isEmpty(str)) {
            doThrow(code, message, content);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入额外操作
     * @param str 待判断字符串
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(String str, String code, String message, T content, Runnable runnable) {
        if (StringUtils.isEmpty(str)) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断字符串不为<code>null</code>或空串，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param str 待判断字符串
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void nonEmpty(String str, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        if (StringUtils.isEmpty(str)) {
            consumer.accept(params);
            doThrow(code, message, content);
        }
    }

    /**
     * 判断集合为空，不是将抛出异常
     * @param collection 待判断集合
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isEmpty(Collection<?> collection, E e) {
        isTrue(CollectionUtils.isEmpty(collection), e);
    }

    /**
     * 判断集合为空，不是将抛出异常，并支持植入额外操作
     * @param collection 待判断集合
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isEmpty(Collection<?> collection, E e, Runnable runnable) {
        isTrue(CollectionUtils.isEmpty(collection), e, runnable);
    }

    /**
     * 判断集合为空，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void isEmpty(Collection<?> collection, E e, MultiConsumer<P> consumer, P... params) {
        isTrue(CollectionUtils.isEmpty(collection), e, consumer, params);
    }

    /**
     * 判断集合为空，不是将抛出异常
     * @param collection 待判断集合
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(Collection<?> collection, DefaultResponse<T> defaultResponse) {
        isTrue(CollectionUtils.isEmpty(collection), defaultResponse);
    }

    /**
     * 判断集合为空，不是将抛出异常，并支持植入额外操作
     * @param collection 待判断集合
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(Collection<?> collection, DefaultResponse<T> defaultResponse, Runnable runnable) {
        isTrue(CollectionUtils.isEmpty(collection), defaultResponse, runnable);
    }

    /**
     * 判断集合为空，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isEmpty(Collection<?> collection, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        isTrue(CollectionUtils.isEmpty(collection), defaultResponse, consumer, params);
    }

    /**
     * 判断集合为空，不是将抛出异常
     * @param collection 待判断集合
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void isEmpty(Collection<?> collection, ReturnCodeEnum returnCodeEnum) {
        isTrue(CollectionUtils.isEmpty(collection), returnCodeEnum);
    }

    /**
     * 判断集合为空，不是将抛出异常
     * @param collection 待判断集合
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void isEmpty(Collection<?> collection, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        isTrue(CollectionUtils.isEmpty(collection), returnCodeEnum, runnable);
    }

    /**
     * 判断集合为空，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void isEmpty(Collection<?> collection, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        isTrue(CollectionUtils.isEmpty(collection), returnCodeEnum, consumer, params);
    }

    /**
     * 判断集合为空，不是将抛出异常
     * @param collection 待判断集合
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(Collection<?> collection, String code, String message, T content) {
        isTrue(CollectionUtils.isEmpty(collection), code, message, content);
    }

    /**
     * 判断集合为空，不是将抛出异常
     * @param collection 待判断集合
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isEmpty(Collection<?> collection, String code, String message, T content, Runnable runnable) {
        isTrue(CollectionUtils.isEmpty(collection), code, message, content, runnable);
    }

    /**
     * 判断集合为空，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isEmpty(Collection<?> collection, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        isTrue(CollectionUtils.isEmpty(collection), code, message, content, consumer, params);
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void nonEmpty(Collection<?> collection, E e) {
        if (CollectionUtils.isEmpty(collection)) {
            doThrow(e);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void nonEmpty(Collection<?> collection, E e, Runnable runnable) {
        if (CollectionUtils.isEmpty(collection)) {
            runnable.run();
            doThrow(e);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void nonEmpty(Collection<?> collection, E e, MultiConsumer<P> consumer, P... params) {
        if (CollectionUtils.isEmpty(collection)) {
            consumer.accept(params);
            doThrow(e);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(Collection<?> collection, DefaultResponse<T> defaultResponse) {
        if (CollectionUtils.isEmpty(collection)) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(Collection<?> collection, DefaultResponse<T> defaultResponse, Runnable runnable) {
        if (CollectionUtils.isEmpty(collection)) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void nonEmpty(Collection<?> collection, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        if (CollectionUtils.isEmpty(collection)) {
            consumer.accept(params);
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void nonEmpty(Collection<?> collection, ReturnCodeEnum returnCodeEnum) {
        if (CollectionUtils.isEmpty(collection)) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void nonEmpty(Collection<?> collection, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        if (CollectionUtils.isEmpty(collection)) {
            runnable.run();
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void nonEmpty(Collection<?> collection, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        if (CollectionUtils.isEmpty(collection)) {
            consumer.accept(params);
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(Collection<?> collection, String code, String message, T content) {
        if (CollectionUtils.isEmpty(collection)) {
            doThrow(code, message, content);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常
     * @param collection 待判断集合
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void nonEmpty(Collection<?> collection, String code, String message, T content, Runnable runnable) {
        if (CollectionUtils.isEmpty(collection)) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断集合不为空，是将抛出异常，并支持植入传递可变参数的额外操作
     * @param collection 待判断集合
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void nonEmpty(Collection<?> collection, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        if (CollectionUtils.isEmpty(collection)) {
            consumer.accept(params);
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isTrue(boolean expression, E e) {
        if (!expression) {
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isTrue(boolean expression, E e, Runnable runnable) {
        if (!expression) {
            runnable.run();
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void isTrue(boolean expression, E e, MultiConsumer<P> consumer, P... params) {
        if (!expression) {
            consumer.accept(params);
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse<T>}
     */
    public static <T> void isTrue(boolean expression, DefaultResponse<T> defaultResponse) {
        if (!expression) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isTrue(boolean expression, DefaultResponse<T> defaultResponse, Runnable runnable) {
        if (!expression) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isTrue(boolean expression, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        if (!expression) {
            consumer.accept(params);
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void isTrue(boolean expression, ReturnCodeEnum returnCodeEnum) {
        if (!expression) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void isTrue(boolean expression, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        if (!expression) {
            runnable.run();
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void isTrue(boolean expression, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        if (!expression) {
            consumer.accept(params);
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isTrue(boolean expression, String code, String message, T content) {
        if (!expression) {
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isTrue(boolean expression, String code, String message, T content, Runnable runnable) {
        if (!expression) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isTrue(boolean expression, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        if (!expression) {
            consumer.accept(params);
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isFalse(boolean expression, E e) {
        if (expression) {
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void isFalse(boolean expression, E e, Runnable runnable) {
        if (expression) {
            runnable.run();
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void isFalse(boolean expression, E e, MultiConsumer<P> consumer, P... params) {
        if (expression) {
            consumer.accept(params);
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isFalse(boolean expression, DefaultResponse<T> defaultResponse) {
        if (expression) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isFalse(boolean expression, DefaultResponse<T> defaultResponse, Runnable runnable) {
        if (expression) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isFalse(boolean expression, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        if (expression) {
            consumer.accept(params);
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void isFalse(boolean expression, ReturnCodeEnum returnCodeEnum) {
        if (expression) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void isFalse(boolean expression, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        if (expression) {
            runnable.run();
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void isFalse(boolean expression, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        if (expression) {
            consumer.accept(params);
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isFalse(boolean expression, String code, String message, T content) {
        if (expression) {
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isFalse(boolean expression, String code, String message, T content, Runnable runnable) {
        if (expression) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式为<code>false</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expression 待判断表达式
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void isFalse(boolean expression, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        if (expression) {
            consumer.accept(params);
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常
     * @param expressionSupplier 布尔表达式提供者
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void state(Supplier<Boolean> expressionSupplier, E e) {
        if (!expressionSupplier.get()) {
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E extends BaseException> void state(Supplier<Boolean> expressionSupplier, E e, Runnable runnable) {
        if (!expressionSupplier.get()) {
            runnable.run();
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param e {@link BaseException}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <E> {@link BaseException}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <E extends BaseException, P> void state(Supplier<Boolean> expressionSupplier, E e, MultiConsumer<P> consumer, P... params) {
        if (!expressionSupplier.get()) {
            consumer.accept(params);
            doThrow(e);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常
     * @param expressionSupplier 布尔表达式提供者
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void state(Supplier<Boolean> expressionSupplier, DefaultResponse<T> defaultResponse) {
        if (!expressionSupplier.get()) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void state(Supplier<Boolean> expressionSupplier, DefaultResponse<T> defaultResponse, Runnable runnable) {
        if (!expressionSupplier.get()) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param defaultResponse {@link DefaultResponse<T>}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void state(Supplier<Boolean> expressionSupplier, DefaultResponse<T> defaultResponse, MultiConsumer<P> consumer, P... params) {
        if (!expressionSupplier.get()) {
            consumer.accept(params);
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常
     * @param expressionSupplier 布尔表达式提供者
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void state(Supplier<Boolean> expressionSupplier, ReturnCodeEnum returnCodeEnum) {
        if (!expressionSupplier.get()) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param runnable 需要植入的操作
     */
    public static void state(Supplier<Boolean> expressionSupplier, ReturnCodeEnum returnCodeEnum, Runnable runnable) {
        if (!expressionSupplier.get()) {
            runnable.run();
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param returnCodeEnum {@link ReturnCodeEnum}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <P> void state(Supplier<Boolean> expressionSupplier, ReturnCodeEnum returnCodeEnum, MultiConsumer<P> consumer, P... params) {
        if (!expressionSupplier.get()) {
            consumer.accept(params);
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常
     * @param expressionSupplier 布尔表达式提供者
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void state(Supplier<Boolean> expressionSupplier, String code, String message, T content) {
        if (!expressionSupplier.get()) {
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param runnable 需要植入的操作
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void state(Supplier<Boolean> expressionSupplier, String code, String message, T content, Runnable runnable) {
        if (!expressionSupplier.get()) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入传递可变参数的额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param code {@link DefaultResponse#getCode()}
     * @param message {@link DefaultResponse#getMessage()}
     * @param content {@link DefaultResponse#getContent()}
     * @param consumer 需要植入传递可变参数额外操作
     * @param params 可变参数
     * @param <T> {@link DefaultResponse#getContent()#getClass()}
     * @param <P> 参数类型
     */
    @SafeVarargs
    public static <T, P> void state(Supplier<Boolean> expressionSupplier, String code, String message, T content, MultiConsumer<P> consumer, P... params) {
        if (!expressionSupplier.get()) {
            consumer.accept(params);
            doThrow(code, message, content);
        }
    }

    /**
     * 支持可变参数的函数式接口
     * @param <P> 参数类型
     */
    @FunctionalInterface
    public interface MultiConsumer<P> {

        void accept(P... params);

    }
}
