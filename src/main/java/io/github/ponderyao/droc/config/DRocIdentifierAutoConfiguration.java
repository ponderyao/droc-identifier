package io.github.ponderyao.droc.config;

import io.github.ponderyao.droc.bean.CachePropertiesBean;
import io.github.ponderyao.droc.bean.SnowflakePropertiesBean;
import io.github.ponderyao.droc.config.cache.CacheProperties;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import io.github.ponderyao.droc.core.CacheDRocIdGenerator;
import io.github.ponderyao.droc.core.DRocIdGenerator;
import io.github.ponderyao.droc.core.SnowflakeDRocIdGenerator;
import io.github.ponderyao.droc.strategy.cache.CacheStrategyContext;
import io.github.ponderyao.droc.strategy.snowflake.SnowflakeStrategyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DRocIdentifierAutoConfiguration：DRoc分布式 ID自动装配配置类
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(DRocIdentifierProperties.class)
public class DRocIdentifierAutoConfiguration {

    public static final Logger log = LoggerFactory.getLogger(DRocIdentifierAutoConfiguration.class);
    
    private final DRocIdentifierProperties properties;
    
    public DRocIdentifierAutoConfiguration(DRocIdentifierProperties properties) {
        this.properties = properties;
    }
    
    @Bean(name = "snowflakePropertiesBean")
    @ConditionalOnProperty(prefix = DRocIdentifierProperties.PREFIX, name = "topic", havingValue = "snowflake")
    public SnowflakePropertiesBean snowflakePropertiesBean() {
        SnowflakeProperties snowflakeProperties = properties.getSnowflake();
        SnowflakeStrategyContext strategyManager = new SnowflakeStrategyContext();
        return strategyManager.transferProperties(snowflakeProperties);
    }
    
    @Bean(name = "cachePropertiesBean")
    @ConditionalOnProperty(prefix = DRocIdentifierProperties.PREFIX, name = "topic", havingValue = "cache")
    public CachePropertiesBean cachePropertiesBean() {
        CacheProperties cacheProperties = properties.getCache();
        CacheStrategyContext cacheManager = new CacheStrategyContext();
        return cacheManager.transferProperties(cacheProperties);
    }

    /**
     * Bean: 雪花算法分布式ID生成器
     * 
     * @return DRocIdGenerator
     */
    @Bean(name = "snowflakeDRocIdGenerator")
    @ConditionalOnProperty(prefix = DRocIdentifierProperties.PREFIX, name = "topic", havingValue = "snowflake")
    public DRocIdGenerator snowflakeDRocIdGenerator(SnowflakePropertiesBean snowflakePropertiesBean) {
        log.info("Registering DRocId generator with droc-snowflake-algorithm pattern...");
        return new SnowflakeDRocIdGenerator(snowflakePropertiesBean);
    }

    /**
     * Bean: 号段模式分布式ID生成器
     *
     * @return DRocIdGenerator
     */
    @Bean(name = "segmentDRocIdGenerator")
    @ConditionalOnProperty(prefix = DRocIdentifierProperties.PREFIX, name = "topic", havingValue = "segment")
    public DRocIdGenerator segmentDRocIdGenerator() {
        log.info("Registering DRocId generator with database-segment-model pattern...");
        return null;
    }

    /**
     * Bean: 缓存自增分布式ID生成器
     *
     * @return DRocIdGenerator
     */
    @Bean(name = "cacheDRocIdGenerator")
    @ConditionalOnProperty(prefix = DRocIdentifierProperties.PREFIX, name = "topic", havingValue = "cache")
    public DRocIdGenerator cacheDRocIdGenerator(CachePropertiesBean cachePropertiesBean) {
        log.info("Registering DRocId generator with cache-auto-increment pattern...");
        return new CacheDRocIdGenerator(cachePropertiesBean);
    }
    
}
