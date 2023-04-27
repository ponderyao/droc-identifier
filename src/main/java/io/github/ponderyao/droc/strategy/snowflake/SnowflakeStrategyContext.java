package io.github.ponderyao.droc.strategy.snowflake;

import java.util.ArrayList;
import java.util.List;

import io.github.ponderyao.droc.bean.SnowflakePropertiesBean;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import io.github.ponderyao.droc.strategy.StrategyContext;

/**
 * SnowflakeStrategyContext：雪花算法策略上下文
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class SnowflakeStrategyContext implements StrategyContext<SnowflakeProperties, SnowflakePropertiesBean> {
    
    private static final List<SnowflakeStrategy> STRATEGIES = new ArrayList<>();
    
    static {
        STRATEGIES.add(new DefaultSnowflakeStrategy());
        STRATEGIES.add(new NativeSnowflakeStrategy());
        STRATEGIES.add(new DynamicSnowflakeStrategy());
    }
    
    @Override
    public SnowflakePropertiesBean transferProperties(SnowflakeProperties properties) {
        for (SnowflakeStrategy strategy : STRATEGIES) {
            if (strategy.match(properties.getType())) {
                return strategy.execute(properties);
            }
        }
        return STRATEGIES.get(0).execute(properties);
    }
    
}
