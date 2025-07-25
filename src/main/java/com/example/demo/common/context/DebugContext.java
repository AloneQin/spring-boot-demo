package com.example.demo.common.context;

/**
 * 调试上下文
 */
public class DebugContext {

    /**
     * 调试模式标识
     */
    public static final String S_DEBUG_MODE = "S-DEBUG-MODE";

    private static final ThreadLocal<Boolean> DEBUG_CONTEXT = new ThreadLocal<>();

    public static void setDebugContext(boolean debugContext) {
        DEBUG_CONTEXT.set(debugContext);
    }

    public static boolean getDebugContext() {
        return DEBUG_CONTEXT.get() != null && DEBUG_CONTEXT.get();
    }

    public static void clear() {
        DEBUG_CONTEXT.remove();
    }

}
