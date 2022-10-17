package com.example.demo.dao.wrapper.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.mapper.PhoneMapper;
import com.example.demo.dao.wrapper.PhoneDAO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 手机表 服务实现类
 * </p>
 *
 * @author alone
 * @since 2021-12-06
 */
@Service
@AllArgsConstructor
public class PhoneDAOImpl extends ServiceImpl<PhoneMapper, PhonePO> implements PhoneDAO {

    private final PhoneMapper phoneMapper;

    @Override
    public Page<PhonePO> pageByCondition(Integer pageSize, Integer pageNum, String name, String brand, String remark) {
        LambdaQueryChainWrapper<PhonePO> wrapper = new LambdaQueryChainWrapper<>(phoneMapper)
                .like(StringUtils.nonBlank(name), PhonePO::getName, name)
                .eq(StringUtils.nonBlank(brand), PhonePO::getBrand, brand)
                .like(StringUtils.nonBlank(remark), PhonePO::getRemark, remark);

        return wrapper.page(new Page<>(pageSize, pageNum));
    }

    @Override
    public List<PhonePO> findByName(String name) {
        return phoneMapper.selectByName(name);
    }

    @Override
    public List<PhonePO> findByIdAndName(Integer id, String name) {
        return new LambdaQueryChainWrapper<>(phoneMapper)
                .eq(Objects.nonNull(id), PhonePO::getId, id)
                .eq(Objects.nonNull(name), PhonePO::getName, name)
                .list();
    }
}
