package com.example.demo.common.mybatis;

import com.example.demo.common.context.SqlContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
@Slf4j
public class MyBatisSqlContextInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        String sqlId = ms.getId();
        SqlCommandType sqlCommandType = ms.getSqlCommandType();

        BoundSql boundSql = ms.getBoundSql(args[1]);
        String sql = boundSql.getSql();

        SqlContext.SqlInfo sqlInfo = new SqlContext.SqlInfo()
                .setMethodName(methodName)
                .setSqlId(sqlId)
                .setSqlCommandType(sqlCommandType)
                .setSql(sql);
        SqlContext.setCurrentSqlContext(sqlInfo);

        try {
            return invocation.proceed();
        } finally {
            SqlContext.clear();
        }
    }
}
