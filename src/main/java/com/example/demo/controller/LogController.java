package com.example.demo.controller;

import com.example.demo.common.response.DefaultResponse;
import com.example.demo.model.dto.LogDTO;
import com.example.demo.model.vo.LogVO;
import com.example.demo.service.LogService;
import com.example.demo.utils.SmartBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {

    private final LogService logService;

    @GetMapping("/list")
    public DefaultResponse<List<LogVO>> list() {
        List<LogDTO> list = logService.list();
        return DefaultResponse.success(SmartBeanUtils.copyPropertiesList(list, LogVO::new));
    }

}
