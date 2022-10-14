package io.github.ponderyao.droc.strategy.snowflake;

import io.github.ponderyao.droc.bean.SnowflakePropertiesBean;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import io.github.ponderyao.droc.strategy.Strategy;

/**
 * SnowflakeStrategy：
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface SnowflakeStrategy extends Strategy {
    
    SnowflakePropertiesBean execute(SnowflakeProperties properties);
    
}
