package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.ParamError;
import com.example.demo.common.exception.ParamValidatedException;
import com.example.demo.common.metadata.constant.MsgConst;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.dao.wrapper.impl.PhoneDAOImpl;
import com.example.demo.model.dto.PhoneDTO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.AssertUtils;
import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PhoneService {

    private final PhoneDAOImpl phoneDAO;

    public PhoneService(PhoneDAOImpl phoneDAO) {
        this.phoneDAO = phoneDAO;
    }

    public Page<PhoneDTO> getPhoneList(Integer pageSize, Integer pageNum, String name, String brand, String remark) {
        Page<PhonePO> phonePOPage = phoneDAO.pageByCondition(pageSize, pageNum, name, brand, remark);
        return SmartBeanUtils.copyPropertiesPage(phonePOPage, PhoneDTO::new);
    }

    public PhoneDTO getPhoneById(Integer id) {
        PhonePO phonePO = phoneDAO.getById(id);
        return SmartBeanUtils.copyProperties(phonePO, PhoneDTO::new);
    }

    public List<PhoneDTO> getPhoneByName(String name) {
        List<PhonePO> phonePOList = phoneDAO.findByName(name);
        return SmartBeanUtils.copyPropertiesList(phonePOList, PhoneDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addPhone(PhoneDTO phoneDTO) {
        AssertUtils.isNull(phoneDTO.getId(), new ParamValidatedException(Arrays.asList(new ParamError("id", MsgConst.MUST_NULL))));

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.save(phonePO);

        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to add phone, phonePO: {}", FastjsonUtils.toString(phonePO)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyPhone(PhoneDTO phoneDTO) {
        AssertUtils.nonNull(phoneDTO.getId(), new ParamValidatedException(Arrays.asList(new ParamError("id", MsgConst.MUST_NULL))));

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.updateById(phonePO);

        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to modify phone, phonePO: {}", FastjsonUtils.toString(phonePO)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removePhone(Integer id) {
        boolean result = phoneDAO.removeById(id);
        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to remove phone, id: {}", id));
    }
}
