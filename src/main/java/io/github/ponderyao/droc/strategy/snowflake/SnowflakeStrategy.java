package io.github.ponderyao.droc.strategy.snowflake;

import io.github.ponderyao.droc.bean.SnowflakePropertiesBean;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import io.github.ponderyao.droc.strategy.Strategy;

/**
 * SnowflakeStrategy：雪花算法策略接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface SnowflakeStrategy extends Strategy {
    
    SnowflakePropertiesBean execute(SnowflakeProperties properties);
    
}
