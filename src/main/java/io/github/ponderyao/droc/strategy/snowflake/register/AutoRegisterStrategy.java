package io.github.ponderyao.droc.strategy.snowflake.register;

import io.github.ponderyao.droc.strategy.Strategy;

/**
 * AutoRegisterStrategy：
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface AutoRegisterStrategy extends Strategy {
    
    long execute(String key, long superiorLimit);
    
}
