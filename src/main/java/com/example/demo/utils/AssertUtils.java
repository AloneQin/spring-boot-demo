package com.example.demo.utils;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.response.DefaultResponse;
import com.example.demo.common.response.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 断言工具类
 * @param <E> {@link BaseException}
 * @param <T> {@link DefaultResponse#getContent()#getClass()}
 */
@Slf4j
public class AssertUtils<E extends BaseException, T> {

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
     * @param defaultResponse {@link DefaultResponse}
     */
    public static void doThrow(DefaultResponse defaultResponse) {
        throw new BaseException(defaultResponse);
    }

    /**
     * 抛出异常，并支持植入额外操作
     * @param defaultResponse {@link DefaultResponse}
     * @param runnable 需要植入的操作
     */
    public static void doThrow(DefaultResponse defaultResponse, Runnable runnable) {
        runnable.run();
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
     * 判断对象为<code>null</>，不是将抛出异常
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E> void isNull(Object object, E e) {
        if (Objects.nonNull(object)) {
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断对象为<code>null</>，不是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E> void isNull(Object object, E e, Runnable runnable) {
        if (Objects.nonNull(object)) {
            runnable.run();
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断对象为<code>null</>，不是将抛出异常
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse}
     */
    public static void isNull(Object object, DefaultResponse defaultResponse) {
        if (Objects.nonNull(object)) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象为<code>null</>，不是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse}
     * @param runnable 需要植入的操作
     */
    public static void isNull(Object object, DefaultResponse defaultResponse, Runnable runnable) {
        if (Objects.nonNull(object)) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象为<code>null</>，不是将抛出异常
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void isNull(Object object, ReturnCodeEnum returnCodeEnum) {
        if (Objects.nonNull(object)) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象为<code>null</>，不是将抛出异常，并支持植入额外操作
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
     * 判断对象为<code>null</>，不是将抛出异常
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
     * 判断对象为<code>null</>，不是将抛出异常，并支持植入额外操作
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
     * 判断对象不为<code>null</>，是将抛出异常
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E> void nonNull(Object object, E e) {
        if (Objects.isNull(object)) {
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断对象不为<code>null</>，是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E> void nonNull(Object object, E e, Runnable runnable) {
        if (Objects.isNull(object)) {
            runnable.run();
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断对象不为<code>null</>，是将抛出异常
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse}
     */
    public static void nonNull(Object object, DefaultResponse defaultResponse) {
        if (Objects.isNull(object)) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象不为<code>null</>，是将抛出异常，并支持植入额外操作
     * @param object 待判断对象
     * @param defaultResponse {@link DefaultResponse}
     * @param runnable 需要植入的操作
     */
    public static void nonNull(Object object, DefaultResponse defaultResponse, Runnable runnable) {
        if (Objects.isNull(object)) {
            runnable.run();
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断对象不为<code>null</>，是将抛出异常
     * @param object 待判断对象
     * @param returnCodeEnum {@link ReturnCodeEnum}
     */
    public static void nonNull(Object object, ReturnCodeEnum returnCodeEnum) {
        if (Objects.isNull(object)) {
            doThrow(returnCodeEnum);
        }
    }

    /**
     * 判断对象不为<code>null</>，是将抛出异常，并支持植入额外操作
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
     * 判断对象不为<code>null</>，是将抛出异常
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
     * 判断对象不为<code>null</>，是将抛出异常，并支持植入额外操作
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
     * 判断布尔表达式为<code>true</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E> void isTrue(boolean expression, E e) {
        if (!expression) {
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E> void isTrue(boolean expression, E e, Runnable runnable) {
        if (!expression) {
            runnable.run();
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse}
     */
    public static void isTrue(boolean expression, DefaultResponse defaultResponse) {
        if (!expression) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expression 待判断表达式
     * @param defaultResponse {@link DefaultResponse}
     * @param runnable 需要植入的操作
     */
    public static void isTrue(boolean expression, DefaultResponse defaultResponse, Runnable runnable) {
        if (!expression) {
            runnable.run();
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
     * @param <T> @param <T> {@link DefaultResponse#getContent()#getClass()}
     */
    public static <T> void isTrue(boolean expression, String code, String message, T content, Runnable runnable) {
        if (!expression) {
            runnable.run();
            doThrow(code, message, content);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常
     * @param expressionSupplier 布尔表达式提供者
     * @param e {@link BaseException}
     * @param <E> {@link BaseException}
     */
    public static <E> void state(Supplier<Boolean> expressionSupplier, E e) {
        if (!expressionSupplier.get()) {
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param e {@link BaseException}
     * @param runnable 需要植入的操作
     * @param <E> {@link BaseException}
     */
    public static <E> void state(Supplier<Boolean> expressionSupplier, E e, Runnable runnable) {
        if (!expressionSupplier.get()) {
            runnable.run();
            doThrow((BaseException) e);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常
     * @param expressionSupplier 布尔表达式提供者
     * @param defaultResponse {@link DefaultResponse}
     */
    public static void state(Supplier<Boolean> expressionSupplier, DefaultResponse defaultResponse) {
        if (!expressionSupplier.get()) {
            doThrow(defaultResponse);
        }
    }

    /**
     * 判断布尔表达式提供者为<code>true</code>，不是将抛出异常，并支持植入额外操作
     * @param expressionSupplier 布尔表达式提供者
     * @param defaultResponse {@link DefaultResponse}
     * @param runnable 需要植入的操作
     */
    public static void state(Supplier<Boolean> expressionSupplier, DefaultResponse defaultResponse, Runnable runnable) {
        if (!expressionSupplier.get()) {
            runnable.run();
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
}
