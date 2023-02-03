package io.github.ponderyao.droc.strategy.snowflake.register;

import io.github.ponderyao.droc.strategy.StrategyContext;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoRegisterStrategyManager：雪花算法-机房号/机器号-自动注册策略上下文
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class AutoRegisterStrategyContext implements StrategyContext {
    
    public static final List<AutoRegisterStrategy> STRATEGIES = new ArrayList<>();
    
    static {
        STRATEGIES.add(new RedisAutoRegisterStrategy());
    }
    
    public long register(String type, String key, long superiorLimit) {
        for (AutoRegisterStrategy strategy : STRATEGIES) {
            if (strategy.match(type)) {
                return strategy.execute(key, superiorLimit);
            }
        }
        return STRATEGIES.get(0).execute(key, superiorLimit);
    }
    
}
