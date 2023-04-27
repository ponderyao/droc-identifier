package io.github.ponderyao.droc.config.segment;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * CaffeineConfig：Caffeine本地缓存配置
 *
 * @author PonderYao
 * @since 1.3.0
 */
@Configuration
public class DRocCaffeineConfig {
    
    @Bean("localCache")
    public Cache<String, Object> localCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .initialCapacity(100)
                .maximumSize(1000)
                .recordStats()
                .build();
    }
}
