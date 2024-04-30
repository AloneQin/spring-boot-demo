package com.example.demo.common.lisenser;

import com.example.demo.common.exception.BaseException;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.common.websocket.WebSocketReqMappingInfo;
import com.example.demo.common.websocket.WebSocketRequestMapping;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局事件监听器
 */
@Slf4j
@Component
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

    public static ConcurrentHashMap<String, WebSocketReqMappingInfo> webSocketReqMappingInfoMap = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("#onApplicationEvent, listen event: [{}]", event.getClass().getName());
        if (event instanceof ApplicationStartedEvent) {
            // 应用启动完成
            log.info("#onApplicationEvent, application init completed.");
            ConfigurableApplicationContext applicationContext = ((ApplicationStartedEvent) event).getApplicationContext();
            // 扫描上下文，加载 WebSocket 注解信息
            scanWebSocketAnnotationInfo(applicationContext);
        }
    }

    /**
     * 扫描 WebSocket 注解信息
     * @param applicationContext 上下文
     */
    private void scanWebSocketAnnotationInfo(ConfigurableApplicationContext applicationContext) {
        // 获取所有 bean 名称
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Class<?> clazz = bean.getClass();
            // 得到类上的注解信息
            WebSocketRequestMapping classAnnotation = clazz.getAnnotation(WebSocketRequestMapping.class);
            String[] classValues = new String[]{""};
            if (classAnnotation != null) {
                classValues = classAnnotation.value();
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                // 得到方法上的注解信息
                WebSocketRequestMapping methodAnnotation = method.getAnnotation(WebSocketRequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValues = methodAnnotation.value();
                    for (String classValue : classValues) {
                        for (String methodValue : methodValues) {
                            String path = classValue + methodValue;
                            if (webSocketReqMappingInfoMap.get(path) != null) {
                                log.error("#annotation @WebSocketRequestMapping duplicate paths: [{}], please check the code.", path);
                                log.error("#path-1: class: {}, method: {}()", webSocketReqMappingInfoMap.get(path).getClazz().getName(), webSocketReqMappingInfoMap.get(path).getMethod().getName());
                                log.error("#path-2: class: {}, method: {}()", clazz.getName(), method.getName());
                                throw new BaseException(ReturnCodeEnum.SERVER_ERROR, StringUtils.format("web socket url [{}] duplicate paths", path));
                            }
                            webSocketReqMappingInfoMap.put(path, new WebSocketReqMappingInfo(clazz, method, classValue + methodValue, bean));
                            log.info("#annotation @WebSocketRequestMapping, path: [{}], class: {}, method: {}()", path, clazz.getName(), method.getName());
                        }
                    }
                }
            }
        }
    }

    /**
     * 扫描 WebSocket 注解信息，仅扫描使用注解的类，即注解必须先定义在类上才能被扫描到
     * @param applicationContext 上下文
     */
    private void scanWebSocketAnnotationInfoOnlyClass(ConfigurableApplicationContext applicationContext) {
        // 获取使用注解的所有 bean
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(WebSocketRequestMapping.class);
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            // 根据 bean 名称得到 bean 的类信息
            Class<?> clazz = applicationContext.getType(entry.getKey());
            String[] classValues = new String[0];
            if (clazz == null) {
                continue;
            }
            // 得到类上的注解信息
            WebSocketRequestMapping classAnnotation = clazz.getAnnotation(WebSocketRequestMapping.class);
            if (classAnnotation != null) {
                classValues = classAnnotation.value();
            }
            // 获取类的所有方法
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                // 得到方法上的注解信息
                WebSocketRequestMapping methodAnnotation = method.getAnnotation(WebSocketRequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValues = methodAnnotation.value();
                    for (String classValue : classValues) {
                        for (String methodValue : methodValues) {
                            String path = classValue + methodValue;
                            if (webSocketReqMappingInfoMap.get(path) != null) {
                                log.error("#annotation @WebSocketRequestMapping duplicate paths: [{}], please check the code.", path);
                                log.error("#path-1: class: {}, method: {}()", webSocketReqMappingInfoMap.get(path).getClazz().getName(), webSocketReqMappingInfoMap.get(path).getMethod().getName());
                                log.error("#path-2: class: {}, method: {}()", clazz.getName(), method.getName());
                                throw new BaseException(ReturnCodeEnum.SERVER_ERROR, StringUtils.format("web socket url [{}] duplicate paths", path));
                            }
                            webSocketReqMappingInfoMap.put(path, new WebSocketReqMappingInfo(clazz, method, classValue + methodValue, entry.getValue()));
                            log.info("#annotation @WebSocketRequestMapping, path: [{}], class: {}, method: {}()", path, clazz.getName(), method.getName());
                        }
                    }
                }
            }
        }
    }
}
