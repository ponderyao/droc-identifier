package io.github.ponderyao.droc.strategy.snowflake.register;

import io.github.ponderyao.droc.strategy.Strategy;

/**
 * AutoRegisterStrategy：雪花算法-机房号/机器号-自动注册策略接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface AutoRegisterStrategy extends Strategy {
    
    long execute(String key, long superiorLimit);
    
}
