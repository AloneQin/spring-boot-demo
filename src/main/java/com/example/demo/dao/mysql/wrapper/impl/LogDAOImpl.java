package com.example.demo.dao.mysql.wrapper.impl;

import com.example.demo.model.po.LogPO;
import com.example.demo.dao.mysql.mapper.LogMapper;
import com.example.demo.dao.mysql.wrapper.LogDAO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author alone
 * @since 2024-09-12
 */
@Service
public class LogDAOImpl extends ServiceImpl<LogMapper, LogPO> implements LogDAO {


}
