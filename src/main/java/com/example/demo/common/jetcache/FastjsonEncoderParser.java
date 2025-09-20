package com.example.demo.common.jetcache;

import com.alicp.jetcache.anno.SerialPolicy;
import com.alicp.jetcache.anno.support.DefaultEncoderParser;
import com.alicp.jetcache.support.DecoderMap;

import java.util.function.Function;

/**
 * Fastjson2 编码解析器
 */
public class FastjsonEncoderParser extends DefaultEncoderParser {

    public static final String FASTJSON_ENCODER = "fastjson";

    @Override
    public Function<Object, byte[]> parseEncoder(String valueEncoder) {
        if (FASTJSON_ENCODER.equalsIgnoreCase(valueEncoder)) {
            return new FastjsonValueEncoder(true);
        }
        return super.parseEncoder(valueEncoder);
    }

    @Override
    public Function<byte[], Object> parseDecoder(String valueDecoder) {
        if (FASTJSON_ENCODER.equalsIgnoreCase(valueDecoder)) {
            FastjsonValueDecoder fastjsonValueDecoder = new FastjsonValueDecoder(true);
            DecoderMap decoderMap = DecoderMap.defaultInstance();
            decoderMap.register(SerialPolicy.IDENTITY_NUMBER_FASTJSON2, FastjsonValueDecoder.INSTANCE);
            fastjsonValueDecoder.setDecoderMap(decoderMap);
            return fastjsonValueDecoder;
        }
        return super.parseDecoder(valueDecoder);
    }
}
