package com.example.demo.service;

import com.example.demo.dao.mysql.wrapper.LogDAO;
import com.example.demo.model.dto.LogDTO;
import com.example.demo.model.po.LogPO;
import com.example.demo.utils.SmartBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogDAO logDAO;

    public List<LogDTO> list() {
        List<LogPO> list = logDAO.list();
        return SmartBeanUtils.copyPropertiesList(list, LogDTO::new);
    }
}
