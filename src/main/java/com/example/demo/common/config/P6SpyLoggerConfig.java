package com.example.demo.common.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import lombok.extern.slf4j.Slf4j;

/**
 * p6spy 日志策略配置
 */
@Slf4j
public class P6SpyLoggerConfig extends FormattedLogger {
    @Override
    public void logException(Exception e) {
        log.error("#logException", e);
    }

    @Override
    public void logText(String text) {
        log.info("#logText: {}", text);
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        final String msg = strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url);
        log.debug("#logSQL: {}", msg);
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        return log.isInfoEnabled();
    }
}
