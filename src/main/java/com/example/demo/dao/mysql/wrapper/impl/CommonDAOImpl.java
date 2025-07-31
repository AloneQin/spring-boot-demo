package com.example.demo.dao.mysql.wrapper.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.dao.mysql.mapper.CommonMapper;
import com.example.demo.dao.mysql.wrapper.CommonDAO;
import com.example.demo.model.po.LogPO;
import com.example.demo.utils.AssertUtils;
import com.example.demo.utils.FastjsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonDAOImpl implements CommonDAO {

    private final CommonMapper commonMapper;

    @Override
    public LinkedHashMap<String, Object> findByTableNameAndId(String tableName, Integer id) {
        return commonMapper.findByTableNameAndId(tableName, id);
    }

    @Override
    public LogPO findLogById(Integer id) {
        LinkedHashMap<String, Object> map = commonMapper.findByTableNameAndId("log", id);
        return FastjsonUtils.toObject(FastjsonUtils.toString(map), LogPO.class);
    }

    @Override
    public <T> T findById(Class<T> clazz, Integer id) {
        String tableName = getTableNameFromAnnotation(clazz);
        LinkedHashMap<String, Object> map = commonMapper.findByTableNameAndId(tableName, id);
        return FastjsonUtils.toObject(FastjsonUtils.toString(map), clazz);
    }

    @Override
    public <T> T findById(Supplier<T> supplier, Integer id) {
        AssertUtils.nonNull(supplier, ReturnCodeEnum.FAIL, () -> log.error("supplier is null"));
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) supplier.get().getClass();
        String tableName = getTableNameFromAnnotation(clazz);
        LinkedHashMap<String, Object> map = commonMapper.findByTableNameAndId(tableName, id);
        return FastjsonUtils.toObject(FastjsonUtils.toString(map), clazz);
    }

    private String getTableNameFromAnnotation(Class<?> clazz) {
        TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
        AssertUtils.nonNull(tableNameAnnotation, ReturnCodeEnum.FAIL, () -> log.error("@TableName annotation not found"));

        String tableName = tableNameAnnotation.value();
        AssertUtils.nonEmpty(tableName, ReturnCodeEnum.FAIL, () -> log.error("@TableName annotation value is empty"));

        return tableName;
    }
}
