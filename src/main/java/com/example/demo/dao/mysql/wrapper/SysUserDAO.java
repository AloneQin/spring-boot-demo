package com.example.demo.dao.mysql.wrapper;

import com.example.demo.model.po.SysUserPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author alone
 * @since 2025-07-24
 */
public interface SysUserDAO extends IService<SysUserPO> {

    /**
     * 根据条件字段查询结果字段
     * @param conditionColumn 条件字段
     * @param conditionValue 条件值
     * @param resultColumn 结果字段
     * @return 结果字段值
     */
    String findResultColumnByConditionColumn(String conditionColumn, String conditionValue, String resultColumn);

}
