package com.example.demo.dao.wrapper.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.mapper.PhoneMapper;
import com.example.demo.dao.wrapper.PhoneDAO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 手机表 服务实现类
 * </p>
 *
 * @author alone
 * @since 2021-12-06
 */
@Service
public class PhoneDAOImpl extends ServiceImpl<PhoneMapper, PhonePO> implements PhoneDAO {

    @Autowired
    private PhoneMapper phoneMapper;

    @Override
    public Page<PhonePO> pageByCondition(Integer pageSize, Integer pageNum, String name, String brand, String remark) {
        LambdaQueryChainWrapper<PhonePO> wrapper = new LambdaQueryChainWrapper<>(phoneMapper);
        if (StringUtils.nonBlank(name)) {
            wrapper.like(PhonePO::getName, name);
        }
        if (StringUtils.nonBlank(brand)) {
            wrapper.eq(PhonePO::getBrand, brand);
        }
        if (StringUtils.nonBlank(remark)) {
            wrapper.like(PhonePO::getRemark, remark);
        }

        return wrapper.page(new Page<>(pageSize, pageNum));
    }
}
