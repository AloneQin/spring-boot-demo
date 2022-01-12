package com.example.demo.dao.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.po.PhonePO;

/**
 * <p>
 * 手机表 服务类
 * </p>
 *
 * @author alone
 * @since 2021-12-06
 */
public interface PhoneDAO extends IService<PhonePO> {

    Page<PhonePO> pageByCondition(Integer pageSize, Integer pageNum, String name, String brand, String remark);


}
