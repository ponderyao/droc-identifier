package io.github.ponderyao.droc.strategy.snowflake;

import io.github.ponderyao.droc.bean.SnowflakePropertiesBean;
import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.common.constant.SnowflakeConstant;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import io.github.ponderyao.droc.util.ConstantUtils;

import java.util.Map;

/**
 * DefaultSnowflakeStrategy：默认雪花算法策略实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DefaultSnowflakeStrategy implements SnowflakeStrategy {
    
    @Override
    public boolean match(String type) {
        return DRocConstant.SNOWFLAKE_TYPE.DEFAULT.equals(type);
    }

    @Override
    public SnowflakePropertiesBean execute(SnowflakeProperties properties) {
        Map<String, Object> defaultProperties = ConstantUtils.constFieldsToMap(SnowflakeConstant.DEFAULT.class);
        properties.resetProperties(defaultProperties);
        return new SnowflakePropertiesBean(properties);
    }
    
}
