package com.example.demo.dao.mysql.wrapper.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.example.demo.model.po.SysUserPO;
import com.example.demo.dao.mysql.mapper.SysUserMapper;
import com.example.demo.dao.mysql.wrapper.SysUserDAO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.SmartStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author alone
 * @since 2025-07-24
 */
@Service
@RequiredArgsConstructor
public class SysUserDAOImpl extends ServiceImpl<SysUserMapper, SysUserPO> implements SysUserDAO {

    private final SysUserMapper sysUserMapper;


    @Override
    public String findResultColumnByConditionColumn(String conditionColumn, String conditionValue, String resultColumn) {
        QueryWrapper<SysUserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(resultColumn);
        queryWrapper.eq(conditionColumn, conditionValue);
        SysUserPO sysUserPO = sysUserMapper.selectOne(queryWrapper);
        if (sysUserPO == null) {
            return null;
        }
        Object fieldValue = ReflectionKit.getFieldValue(sysUserPO, SmartStringUtils.underline2Camel(resultColumn));
        return fieldValue == null ? null : fieldValue.toString();
    }

}
