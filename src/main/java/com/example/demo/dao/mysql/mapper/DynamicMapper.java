package com.example.demo.dao.mysql.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface DynamicMapper {

    @Select("SELECT * FROM ${tableName} where id = ${id}")
    Map<String, Object> selectById(@Param("tableName") String tableName, @Param("id") Object id);

}
