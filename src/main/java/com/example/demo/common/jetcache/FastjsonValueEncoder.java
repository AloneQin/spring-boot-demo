package com.example.demo.common.jetcache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alicp.jetcache.support.AbstractJsonEncoder;

import java.nio.charset.StandardCharsets;

/**
 * Fastjson 值域编码器
 */
public class FastjsonValueEncoder extends AbstractJsonEncoder {

    public static final FastjsonValueEncoder INSTANCE = new FastjsonValueEncoder(true);

    public FastjsonValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    protected byte[] encodeSingleValue(Object value) {
        // 序列化时添加类型信息：解决泛型类型丢失，自动转换为 JSONObject 的问题
        return JSON.toJSONString(value, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
    }

}
