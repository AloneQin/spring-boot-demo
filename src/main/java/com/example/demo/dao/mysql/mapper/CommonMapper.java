package com.example.demo.dao.mysql.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 通用 Mapper 接口
 */
public interface CommonMapper {

    LinkedHashMap<String, Object> findByTableNameAndId(@Param("tableName") String tableName, @Param("id") Integer id);


}
