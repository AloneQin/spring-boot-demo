package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.exception.ParamError;
import com.example.demo.common.exception.ParamValidatedException;
import com.example.demo.common.metadata.constant.MsgConst;
import com.example.demo.common.response.ReturnCodeEnum;
import com.example.demo.common.trace.TraceManager;
import com.example.demo.dao.mysql.wrapper.PhoneDAO;
import com.example.demo.model.dto.PhoneDTO;
import com.example.demo.model.po.PhonePO;
import com.example.demo.utils.AssertUtils;
import com.example.demo.utils.FastjsonUtils;
import com.example.demo.utils.SmartBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PhoneService {

    private final PhoneDAO phoneDAO;

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
        checkPhoneExists(null, phoneDTO.getName());

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.save(phonePO);

        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to add phone, phonePO: {}", FastjsonUtils.toString(phonePO)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyPhone(PhoneDTO phoneDTO) {
        AssertUtils.nonNull(phoneDTO.getId(), new ParamValidatedException(Arrays.asList(new ParamError("id", MsgConst.MUST_NULL))));
        checkPhoneExists(phoneDTO.getId(), phoneDTO.getName());

        PhonePO phonePO = SmartBeanUtils.copyProperties(phoneDTO, PhonePO::new);
        boolean result = phoneDAO.updateById(phonePO);

        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to modify phone, phonePO: {}", FastjsonUtils.toString(phonePO)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void removePhone(Integer id) {
        boolean result = phoneDAO.removeById(id);
        AssertUtils.isTrue(result, ReturnCodeEnum.FAIL, () -> log.warn("failed to remove phone, id: {}", id));
    }

    public void checkPhoneExists(Integer id, String name) {
        List<PhonePO> phonePOList = phoneDAO.findByIdAndName(id, name);
        AssertUtils.isTrue(CollectionUtils.isEmpty(phonePOList), ReturnCodeEnum.PHONE_ALREADY_EXISTS,
                () -> log.warn("the phone already exists, id: {}, name: {}", id, name)
        );
    }

    public void testTransactionLapse() {
        // 事务不会生效
        testModifyPhone();

        // 显式使用代理对象调用，事务生效
//        ((PhoneService)AopContext.currentProxy()).testModifyPhone();
    }

    @Transactional(rollbackFor = Exception.class)
    public void testModifyPhone() {
        PhonePO phonePO = new PhonePO();
        phonePO.setId(29);
        phonePO.setPhoneCode("test2");
        phonePO.setName("test2");
        phonePO.setBrand("test2");
        phonePO.setProdDate(LocalDate.now());
        phonePO.setPrice(new BigDecimal("100.00"));
        if (phoneDAO.getById(phonePO.getId()) == null) {
            phoneDAO.save(phonePO);
        }
        phonePO.setRemark(TraceManager.getTraceId());
        phoneDAO.updateById(phonePO);

        // 发生异常 update 数据并不会回滚，因为调用 testModifyPhone() 方法的对象 this 始终是原始实例，不是代理对象，所以事务不会生效
        Arrays.asList().get(100);
    }
}
