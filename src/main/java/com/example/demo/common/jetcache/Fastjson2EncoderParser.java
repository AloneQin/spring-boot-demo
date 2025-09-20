package com.example.demo.common.jetcache;

import com.alicp.jetcache.anno.SerialPolicy;
import com.alicp.jetcache.anno.support.DefaultEncoderParser;
import com.alicp.jetcache.support.DecoderMap;
import com.alicp.jetcache.support.Fastjson2ValueDecoder;
import com.alicp.jetcache.support.Fastjson2ValueEncoder;

import java.util.function.Function;

/**
 * Fastjson2 编码解析器
 */
public class Fastjson2EncoderParser extends DefaultEncoderParser {

    public static final String FASTJSON2_ENCODER = "fastjson2";

    @Override
    public Function<Object, byte[]> parseEncoder(String valueEncoder) {
        if (FASTJSON2_ENCODER.equalsIgnoreCase(valueEncoder)) {
            return new Fastjson2ValueEncoder(true);
        }
        return super.parseEncoder(valueEncoder);
    }

    @Override
    public Function<byte[], Object> parseDecoder(String valueDecoder) {
        if (FASTJSON2_ENCODER.equalsIgnoreCase(valueDecoder)) {
            Fastjson2ValueDecoder fastjson2ValueDecoder = new Fastjson2ValueDecoder(true);
            DecoderMap decoderMap = DecoderMap.defaultInstance();
            decoderMap.register(SerialPolicy.IDENTITY_NUMBER_FASTJSON2, Fastjson2ValueDecoder.INSTANCE);
            fastjson2ValueDecoder.setDecoderMap(decoderMap);
            return fastjson2ValueDecoder;
        }
        return super.parseDecoder(valueDecoder);
    }
}
