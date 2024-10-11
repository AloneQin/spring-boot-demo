package com.example.demo.common.config;

import com.example.demo.utils.SmartStringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * p6spy 日志格式配置
 */
public class P6SpyLogFormatConfig implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String text = "耗时: {} ms, 操作类型: {}, 预编译语句: {}, 完整语句: {}, 连接: {}";
        url = url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
        return SmartStringUtils.format(text, elapsed, category, SmartStringUtils.formatSql(prepared), SmartStringUtils.formatSql(sql), url);
    }

}
