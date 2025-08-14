package com.example.demo.common.context;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * MyBatis SQL 上下文
 */
public class SqlContext {

    private static final ThreadLocal<SqlInfo> SQL_CONTEXT = new ThreadLocal<>();

    public static void setCurrentSqlContext(SqlInfo sqlInfo) {
        SQL_CONTEXT.set(sqlInfo);
    }

    public static SqlInfo getCurrentSqlContext() {
        return SQL_CONTEXT.get();
    }

    public static void clear() {
        SQL_CONTEXT.remove();
    }

    @Data
    @Accessors(chain = true)
    public static class SqlInfo {

        private String methodName;

        private String sqlId;

        private SqlCommandType sqlCommandType;

        private String sql;

        private Long timestamp = System.currentTimeMillis();

    }
}
