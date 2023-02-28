package io.github.ponderyao.droc.core;

import io.github.ponderyao.droc.bean.CachePropertiesBean;
import io.github.ponderyao.droc.common.constant.CacheConstant;
import io.github.ponderyao.droc.common.constant.CommonConstant;
import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.config.cache.CacheModelProperties;
import io.github.ponderyao.droc.exception.cache.CacheModelMissingException;
import io.github.ponderyao.droc.strategy.cache.operator.CacheDataOperator;
import io.github.ponderyao.droc.util.ConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CacheDRocIdGenerator： 缓存自增ID生成器
 * 
 * 缓存自增ID生成器依赖于 CachePropertiesBean 对象，通过 bean 配置实现分布式
 * ID的生成工作. <p>
 * 
 * 缓存自增ID生成器支持并推荐基于 model 模型的形式来区分不同数据表的ID生成序列，
 * 不仅可以单独定义模型的初始偏移量与递增间隔，还能避免不同模型之间使统一默认序列
 * 造成的数据序列混乱、序列指数爆炸增长等问题.
 *
 * @author PonderYao
 * @since 1.2.0
 */
public class CacheDRocIdGenerator implements DRocIdGenerator {

    public static final Logger log = LoggerFactory.getLogger(CacheDRocIdGenerator.class);

    public static final String DROC_STRATEGY = DRocConstant.DROC_STRATEGY.CACHE;
    
    private final CachePropertiesBean propertiesBean;

    @Override
    public String getStrategyName() {
        return DROC_STRATEGY;
    }

    @Override
    public Long generateDRocId() {
        log.warn("It is suggested to define cache model better than totally using default cache key!");
        return nextId("", CacheConstant.DEFAULT_INTERVAL, CacheConstant.DEFAULT_OFFSET);
    }

    @Override
    public Long generateDRocId(String model) {
        if (propertiesBean.getModels().containsKey(model)) {
            CacheModelProperties cacheModelProperties = propertiesBean.getModels().get(model);
            return nextId(ConstantUtils.humpToUnderline(model) + CommonConstant.UNDERLINE, 
                    cacheModelProperties.getInterval(), cacheModelProperties.getOffset());
        }
        throw new CacheModelMissingException(propertiesBean.getType(), model);
    }

    @Override
    public boolean validateDRocId(long id) {
        return true;
    }
    
    public CacheDRocIdGenerator(CachePropertiesBean cachePropertiesBean) {
        this.propertiesBean = cachePropertiesBean;
    }
    
    private Long nextId(String key, long interval, long offset) {
        CacheDataOperator cacheDataOperator = propertiesBean.getCacheDataOperator();
        String cacheKey = key + CacheConstant.CACHE_KEY;
        return cacheDataOperator.increase(cacheKey, cacheDataOperator.containsKey(cacheKey) ? interval : offset);
    }
}
