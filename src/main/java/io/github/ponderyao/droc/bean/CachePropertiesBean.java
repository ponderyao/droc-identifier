package io.github.ponderyao.droc.bean;

import io.github.ponderyao.droc.config.cache.CacheModelProperties;
import io.github.ponderyao.droc.config.cache.CacheProperties;
import io.github.ponderyao.droc.strategy.cache.operator.CacheDataOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CachePropertiesBean：缓存自增参数配置bean
 *
 * @author PonderYao
 * @since 1.2.0
 */
public class CachePropertiesBean {

    public static final Logger log = LoggerFactory.getLogger(CachePropertiesBean.class);

    /**
     * from cache-properties
     */
    private String type;

    /**
     * 缓存模型映射集
     */
    private Map<String, CacheModelProperties> models = Collections.emptyMap();
    /**
     * 缓存数据操作类
     */
    private CacheDataOperator cacheDataOperator;
    
    public CachePropertiesBean(CacheProperties cacheProperties) {
        BeanUtils.copyProperties(cacheProperties, this);
        this.buildCacheModels(cacheProperties);
    }
    
    private void buildCacheModels(CacheProperties cacheProperties) {
        if (!CollectionUtils.isEmpty(cacheProperties.getModels())) {
            this.models = cacheProperties.getModels().stream().collect(Collectors.toMap(CacheModelProperties::getName, Function.identity(), (k1, k2) -> k2));
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, CacheModelProperties> getModels() {
        return models;
    }

    public void setModels(Map<String, CacheModelProperties> models) {
        this.models = models;
    }

    public CacheDataOperator getCacheDataOperator() {
        return cacheDataOperator;
    }

    public void setCacheDataOperator(CacheDataOperator cacheDataOperator) {
        this.cacheDataOperator = cacheDataOperator;
    }
}
