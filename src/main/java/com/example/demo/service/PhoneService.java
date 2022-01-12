package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.BaseException;
import com.example.demo.common.exception.ParamError;
import com.example.demo.common.exception.ParamValidatedException;
import com.example.demo.common.metadata.constant.Constant;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.dao.wrapper.impl.PhoneDAOImpl;
import com.example.demo.model.dto.PhoneDTO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
public class PhoneService {

    @Autowired
    private PhoneDAOImpl phoneDAO;

    public Page<PhoneDTO> getPhoneList(Integer pageSize, Integer pageNum, String name, String brand, String remark) {
        Page<PhonePO> phonePOPage = phoneDAO.pageByCondition(pageSize, pageNum, name, brand, remark);
        return SmartBeanUtils.copyPropertiesPage(phonePOPage, PhoneDTO::new);
    }

    public PhoneDTO getPhoneById(Integer id) {
        PhonePO phonePO = phoneDAO.getById(id);
        return SmartBeanUtils.copyProperties(phonePO, PhoneDTO::new);
    }

    @Transactional
    public void addPhone(PhoneDTO phoneDTO) {
        if (Objects.nonNull(phoneDTO.getId())) {
            throw new ParamValidatedException(Arrays.asList(new ParamError("id", Constant.MUST_NULL)));
        }

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.save(phonePO);
        if (!result) {
            log.error("failed to add phone, phonePO: {}", FastjsonUtils.toString(phonePO));
            throw new BaseException(ReturnCodeEnum.FAIL);
        }
    }

    @Transactional
    public void modifyPhone(PhoneDTO phoneDTO) {
        if (Objects.isNull(phoneDTO.getId())) {
            throw new ParamValidatedException(Arrays.asList(new ParamError("id", Constant.MUST_NULL)));
        }

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.updateById(phonePO);
        if (!result) {
            log.error("failed to modify phone, phonePO: {}", FastjsonUtils.toString(phonePO));
            throw new BaseException(ReturnCodeEnum.FAIL);
        }
    }

    @Transactional
    public void removePhone(Integer id) {
        boolean result = phoneDAO.removeById(id);
        if (!result) {
            log.error("failed to remove phone, id: {}", id);
            throw new BaseException(ReturnCodeEnum.FAIL);
        }
    }
}
