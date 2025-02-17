package com.example.demo.common.prop;

import com.example.demo.utils.FastjsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties("custom.attr")
public class CustomProperties {

    private List<String> testList1;

    private List<String> testList2;

    private Map<String, String> testMap1;

    private Map<String, String> testMap2;

    @Override
    public String toString() {
        return FastjsonUtils.toString(this);
    }

}
