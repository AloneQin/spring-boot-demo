package com.example.demo.common.jetcache;

import com.alicp.jetcache.anno.support.EncoderParser;
import com.alicp.jetcache.anno.support.JetCacheBaseBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jetcache 配置
 */
@Configuration
public class JetCacheConfig {

    /**
     * 自定义编码解析器，用以替换默认的编码解析器，详见{@link JetCacheBaseBeans}
     * @return 自定义编码解析器
     */
    @Bean
    public EncoderParser encoderParser() {
        return new FastjsonEncoderParser();
    }

}


