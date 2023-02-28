package io.github.ponderyao.droc.strategy.cache;

import io.github.ponderyao.droc.bean.CachePropertiesBean;
import io.github.ponderyao.droc.config.cache.CacheProperties;
import io.github.ponderyao.droc.strategy.Strategy;

/**
 * CacheStrategy：缓存自增策略接口
 *
 * @author PonderYao
 * @since 1.2.0
 */
public interface CacheStrategy extends Strategy {
    
    CachePropertiesBean execute(CacheProperties properties);
    
}
