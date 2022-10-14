package io.github.ponderyao.droc.config;

import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * DRocProperties：DRoc分布式 ID 属性配置
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
}
