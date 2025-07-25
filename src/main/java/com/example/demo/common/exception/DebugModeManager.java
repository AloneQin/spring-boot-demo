package com.example.demo.common.exception;

import com.example.demo.common.context.DebugContext;
import com.example.demo.common.context.SpringContextHolder;

/**
 * 调试模式管理器
 */
public class DebugModeManager {

    public static boolean isDebug() {
        // 线程级 or 系统级 任意满足一个即可
        return DebugContext.getDebugContext() || SpringContextHolder.getBean(SystemProperties.class).getDebugMode();
    }

}
