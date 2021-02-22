package com.example.demo.service;

import com.example.demo.common.constant.Constant;
import com.example.demo.common.exception.BaseException;
import com.example.demo.common.exception.ParamError;
import com.example.demo.common.exception.ParamValidatedException;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.dao.PhoneMapper;
import com.example.demo.domain.entity.Phone;
import com.example.demo.domain.vo.PhoneVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PhoneService {

    @Autowired
    private PhoneMapper phoneMapper;

    public PageInfo<PhoneVo> getPhoneList(Integer pageSize, Integer pageNum, String name, String brand, String remark) {

        // 获取分页数据
        PageHelper.startPage(pageNum, pageSize);
        List<Phone> phoneList = phoneMapper.findByCondition(name, brand, remark);
        PageInfo<Phone> phonePI = PageInfo.of(phoneList);

        // 拷贝外层
        PageInfo<PhoneVo> phoneVoPI = new PageInfo<>();
        BeanUtils.copyProperties(phonePI, phoneVoPI);

        // 拷贝内层
        List<PhoneVo> phoneVoList = phoneList.stream().map(p -> {
            PhoneVo pv = new PhoneVo();
            BeanUtils.copyProperties(p, pv);
            return pv;
        }).collect(Collectors.toList());
        phoneVoPI.setList(phoneVoList);

        return phoneVoPI;
    }

    @Transactional
    public void addPhone(PhoneVo phoneVo) {
        Phone phone = new Phone();
        BeanUtils.copyProperties(phoneVo, phone);

        if (phone.getId() != null) {
            throw new ParamValidatedException(Arrays.asList(new ParamError("id", Constant.MUST_NULL)));
        }

        int rows = phoneMapper.insertSelective(phone);
        if (rows != 1) {
            log.error("failed to add phone, affected rows: {}", rows);
            throw new BaseException(ReturnCodeEnum.FAIL);
        }
    }

    @Transactional
    public void modifyPhone(PhoneVo phoneVo) {
        Phone phone = new Phone();
        BeanUtils.copyProperties(phoneVo, phone);

        if (phone.getId() == null) {
            throw new ParamValidatedException(Arrays.asList(new ParamError("id", Constant.NOT_NULL_MSG)));
        }

        int rows = phoneMapper.updateByPrimaryKeySelective(phone);
        if (rows != 1) {
            log.error("failed to modify phone, affected rows: {}", rows);
            throw new BaseException(ReturnCodeEnum.FAIL);
        }
    }

    @Transactional
    public void removePhone(Integer id) {
        int rows = phoneMapper.deleteByPrimaryKey(id);
        if (rows != 1) {
            log.error("failed to remove phone, affected rows: {}", rows);
            throw new BaseException(ReturnCodeEnum.FAIL);
        }
    }
}
