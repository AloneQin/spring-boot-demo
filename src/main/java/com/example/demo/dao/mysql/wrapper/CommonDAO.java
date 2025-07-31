package com.example.demo.dao.mysql.wrapper;

import com.example.demo.model.po.LogPO;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

public interface CommonDAO {

    /**
     * 根据表名、id 查询
     * @param tableName 表名
     * @param id id
     * @return 结果
     */
    LinkedHashMap<String, Object> findByTableNameAndId(String tableName, Integer id);

    /**
     * 根据 id 查询日志
     * @param id id
     * @return 结果
     */
    LogPO findLogById(Integer id);

    /**
     * 根据 id 查询（推荐）
     * @param clazz 类
     * @param id id
     * @return 结果
     */
    <T> T findById(Class<T> clazz, Integer id);

    /**
     * 根据 id 查询
     * @param supplier 供应者
     * @param id id
     * @return 结果
     */
    <T> T findById(Supplier<T> supplier, Integer id);
}
