package io.github.ponderyao.droc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import io.github.ponderyao.droc.config.cache.CacheProperties;
import io.github.ponderyao.droc.config.segment.SegmentProperties;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;

/**
 * DRocProperties：DRoc分布式ID属性配置
 *
 * @author PonderYao
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = DRocIdentifierProperties.PREFIX)
public class DRocIdentifierProperties {
    
    public static final String PREFIX = "ponder.droc";

    /**
     * 分布式ID选型，默认雪花算法
     */
    private String topic = "snowflake";
    
    @NestedConfigurationProperty
    private SnowflakeProperties snowflake = new SnowflakeProperties();
    
    @NestedConfigurationProperty
    private CacheProperties cache = new CacheProperties();
    
    @NestedConfigurationProperty
    private SegmentProperties segment = new SegmentProperties();
    

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public SnowflakeProperties getSnowflake() {
        return snowflake;
    }

    public void setSnowflake(SnowflakeProperties snowflake) {
        this.snowflake = snowflake;
    }

    public CacheProperties getCache() {
        return cache;
    }

    public void setCache(CacheProperties cache) {
        this.cache = cache;
    }

    public SegmentProperties getSegment() {
        return segment;
    }

    public void setSegment(SegmentProperties segment) {
        this.segment = segment;
    }
}
