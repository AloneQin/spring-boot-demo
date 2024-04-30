package com.example.demo.dao.mysql.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dao.mysql.wrapper.CommonDAO;

import java.util.List;

public class CommonDAOImpl<T> implements CommonDAO {

    private IService<T> service;

    public List<T> find(T enity, QueryWrapper<T> queryWrapper) {
//        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
//        queryWrapper.

        // TODO
        return service.list(queryWrapper);
    }

}
