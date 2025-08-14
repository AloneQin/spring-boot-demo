package com.example.demo.common.config;

import com.example.demo.common.context.SqlContext;
import com.example.demo.utils.SmartStringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * p6spy 日志格式配置
 */
public class P6SpyLogFormatConfig implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        url = url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
        String sqlId = "";
        String sqlCommandType = "";
        SqlContext.SqlInfo sqlInfo = SqlContext.getCurrentSqlContext();
        if (Objects.nonNull(sqlInfo)) {
            sqlId = sqlInfo.getSqlId();
            sqlCommandType = Optional.ofNullable(sqlInfo.getSqlCommandType()).map(Enum::name).orElse("");
        }

        return multiLineLog(elapsed, sqlInfo.getTimestamp() + "", category, prepared, sql, url, sqlId, sqlCommandType);
    }

    private String singleLineLog(long elapsed, long timestamp, String category, String prepared, String sql, String url, String sqlId, String sqlCommandType) {
        String text = "sqlId: {}, 耗时: {} ms, 执行类型: {}, 操作分类: {}, 预编译语句: {}, 完整语句: {}, 连接信息: {}";
        return SmartStringUtils.format(sqlId, text, elapsed, sqlCommandType, category, SmartStringUtils.formatSql(prepared), SmartStringUtils.formatSql(sql), url);
    }

    private static String multiLineLog(long elapsed, String timestamp, String category, String prepared, String sql, String url, String sqlId, String sqlCommandType) {
        int maxLength = 6;
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(alignChineseLabel("路径", maxLength), sqlId);
        paramMap.put(alignChineseLabel("耗时", maxLength), elapsed + " ms");
        paramMap.put(alignChineseLabel("时间戳", maxLength), timestamp);
        paramMap.put(alignChineseLabel("执行类型", maxLength), sqlCommandType);
        paramMap.put(alignChineseLabel("操作分类", maxLength), category);
        paramMap.put(alignChineseLabel("预编译语句", maxLength), SmartStringUtils.formatSql(prepared));
        paramMap.put(alignChineseLabel("完整语句", maxLength), SmartStringUtils.formatSql(sql));
        paramMap.put(alignChineseLabel("连接信息",maxLength), url);

        StringBuilder sb = new StringBuilder();
        sb.append("\n--------------------------------------------------------------[p6spy]--------------------------------------------------------------");
        paramMap.entrySet()
                .stream()
                .filter(entry -> StringUtils.isNotEmpty(entry.getValue()))
                .forEach(entry -> {
                    sb.append(String.format("\n%s: %s", entry.getKey(), entry.getValue()));
                });
        sb.append("\n-----------------------------------------------------------------------------------------------------------------------------------");
        return sb.toString();
    }

    private static String alignChineseLabel(String label, int maxLength) {
        StringBuilder sb = new StringBuilder(label);
        int len = label.length();
        // 全角空格（注意不是普通空格）
        sb.append("　".repeat(Math.max(0, maxLength - len)));
        return sb.toString();
    }

}
