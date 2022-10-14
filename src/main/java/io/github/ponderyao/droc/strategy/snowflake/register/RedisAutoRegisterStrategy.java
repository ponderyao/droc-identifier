package io.github.ponderyao.droc.strategy.snowflake.register;

import io.github.ponderyao.droc.common.constant.DRocConstant;

/**
 * RedisAutoRegisterStrategy：基于Redis的自动注册策略实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RedisAutoRegisterStrategy implements AutoRegisterStrategy {
    
    @Override
    public boolean match(String type) {
        return DRocConstant.SNOWFLAKE_REGISTER_WAY.REDIS.equals(type);
    }

    @Override
    public long execute(String key) {
        return 1L;
    }
    
}
