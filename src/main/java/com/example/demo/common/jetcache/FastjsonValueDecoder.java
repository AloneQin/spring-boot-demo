package com.example.demo.common.jetcache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alicp.jetcache.support.AbstractJsonDecoder;

import java.nio.charset.StandardCharsets;

/**
 * Fastjson 值域解码器
 */
public class FastjsonValueDecoder extends AbstractJsonDecoder {

    public static final FastjsonValueDecoder INSTANCE = new FastjsonValueDecoder(true);

    public FastjsonValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @SuppressWarnings(value = "unchecked")
    protected Object parseObject(byte[] buffer, int index, int len, Class clazz) {
        String s = new String(buffer, index, len, StandardCharsets.UTF_8);
        // 反序列化时进行类型自动转换：解决泛型类型丢失，自动转换为 JSONObject 的问题
        return JSON.parseObject(s, clazz, Feature.SupportAutoType);
    }
}
