package io.github.ponderyao.droc.config.cache;

import io.github.ponderyao.droc.common.constant.CacheConstant;

/**
 * CacheModelProperties：缓存模型可配置参数
 *
 * @author PonderYao
 * @since 1.2.0
 */
public class CacheModelProperties {
    
    public static final String PREFIX = CacheProperties.PREFIX + ".models";

    /**
     * 缓存模型名称
     */
    private String name;
    /**
     * 自增初始值，默认为 1
     */
    private long offset = CacheConstant.DEFAULT_OFFSET;
    /**
     * 缓存递增间隔，默认为 1
     */
    private long interval = CacheConstant.DEFAULT_INTERVAL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
