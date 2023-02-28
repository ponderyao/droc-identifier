package io.github.ponderyao.droc.strategy.cache;

import io.github.ponderyao.droc.bean.CachePropertiesBean;
import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.config.cache.CacheProperties;
import io.github.ponderyao.droc.strategy.cache.operator.RedisCacheDataOperator;

/**
 * RedisCacheStrategy：Redis缓存自增策略实现
 *
 * @author PonderYao
 * @since 1.2.0
 */
public class RedisCacheStrategy implements CacheStrategy {
    
    @Override
    public boolean match(String type) {
        return DRocConstant.CACHE_TYPE.REDIS.equals(type);
    }
    
    @Override
    public CachePropertiesBean execute(CacheProperties properties) {
        CachePropertiesBean cachePropertiesBean = new CachePropertiesBean(properties);
        cachePropertiesBean.setCacheDataOperator(new RedisCacheDataOperator());
        return cachePropertiesBean;
    }
    
}
