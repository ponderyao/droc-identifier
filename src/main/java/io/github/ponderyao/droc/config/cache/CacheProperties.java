package io.github.ponderyao.droc.config.cache;

import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.config.DRocIdentifierProperties;

import java.util.Collections;
import java.util.List;

/**
 * CacheProperties：缓存自增可配置参数
 * 
 * 缓存自增型分布式ID无法自定义缓存类型，仅能使用可选的缓存中间件，默认采用Redis. <p>
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class CacheProperties {

    public static final String PREFIX = DRocIdentifierProperties.PREFIX + ".cache";

    /**
     * 缓存中间件选型，默认采用Redis
     */
    private String type = DRocConstant.CACHE_TYPE.REDIS;
    /**
     * 缓存模型，默认为空
     */
    private List<CacheModelProperties> models = Collections.emptyList();
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CacheModelProperties> getModels() {
        return models;
    }

    public void setModels(List<CacheModelProperties> models) {
        this.models = models;
    }
}
