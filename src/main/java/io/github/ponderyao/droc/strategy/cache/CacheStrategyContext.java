package io.github.ponderyao.droc.strategy.cache;

import java.util.ArrayList;
import java.util.List;

import io.github.ponderyao.droc.bean.CachePropertiesBean;
import io.github.ponderyao.droc.common.constant.CacheConstant;
import io.github.ponderyao.droc.common.constant.CommonConstant;
import io.github.ponderyao.droc.config.cache.CacheModelProperties;
import io.github.ponderyao.droc.config.cache.CacheProperties;
import io.github.ponderyao.droc.exception.properties.BadCustomPropertiesException;
import io.github.ponderyao.droc.strategy.StrategyContext;

/**
 * CacheStrategyContext：缓存自增策略上下文
 *
 * @author PonderYao
 * @since 1.2.0
 */
public class CacheStrategyContext implements StrategyContext<CacheProperties, CachePropertiesBean> {
    
    private static final List<CacheStrategy> STRATEGIES = new ArrayList<>();
    
    static {
        STRATEGIES.add(new RedisCacheStrategy());
    }
    
    @Override
    public CachePropertiesBean transferProperties(CacheProperties cacheProperties) {
        beforeProcess(cacheProperties);
        for (CacheStrategy strategy : STRATEGIES) {
            if (strategy.match(cacheProperties.getType())) {
                return strategy.execute(cacheProperties);
            }
        }
        return STRATEGIES.get(0).execute(cacheProperties);
    }
    
    private void beforeProcess(CacheProperties cacheProperties) {
        for (CacheModelProperties modelProperties : cacheProperties.getModels()) {
            if (modelProperties.getOffset() < CacheConstant.DEFAULT_OFFSET) {
                throw new BadCustomPropertiesException(CacheProperties.PREFIX + ".offset (of " + modelProperties.getName() + ")", CommonConstant.NOT_LESS, CacheConstant.DEFAULT_OFFSET);
            }
            if (modelProperties.getStep() < CacheConstant.DEFAULT_STEP) {
                throw new BadCustomPropertiesException(CacheModelProperties.PREFIX + ".step (of " + modelProperties.getName() + ")",
                        CommonConstant.NOT_LESS, CacheConstant.DEFAULT_STEP);
            }
        }
    }
    
}
