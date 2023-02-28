package io.github.ponderyao.droc.strategy.snowflake;

import io.github.ponderyao.droc.bean.SnowflakePropertiesBean;
import io.github.ponderyao.droc.common.constant.CommonConstant;
import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.common.constant.SnowflakeConstant;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import io.github.ponderyao.droc.exception.properties.BadCustomPropertiesException;

/**
 * DynamicSnowflakeStrategy：动态雪花算法策略实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DynamicSnowflakeStrategy implements SnowflakeStrategy {
    
    @Override
    public boolean match(String type) {
        return DRocConstant.SNOWFLAKE_TYPE.DYNAMIC.equals(type);
    }

    @Override
    public SnowflakePropertiesBean execute(SnowflakeProperties properties) {
        checkCustomProperties(properties);
        return new SnowflakePropertiesBean(properties);
    }
    
    private void checkCustomProperties(SnowflakeProperties properties) {
        if (properties.getTwepoch().compareTo(SnowflakeConstant.DYNAMIC.TWEPOCH_LIMIT) < 0) {
            throw new BadCustomPropertiesException(SnowflakeProperties.PREFIX + ".twepoch", CommonConstant.NOT_LESS, SnowflakeConstant.DYNAMIC.TWEPOCH_LIMIT);
        }
        if (properties.getTimestampBits() > SnowflakeConstant.DYNAMIC.TIMESTAMP_BITS_LIMIT) {
            throw new BadCustomPropertiesException(SnowflakeProperties.PREFIX + ".timestamp-bits", CommonConstant.NOT_GREATER, SnowflakeConstant.DYNAMIC.TIMESTAMP_BITS_LIMIT);
        }
        if (properties.isEnableDataCenter() && properties.getDataCenterIdBits() > SnowflakeConstant.DYNAMIC.DATA_CENTER_ID_BITS_LIMIT) {
            throw new BadCustomPropertiesException(SnowflakeProperties.PREFIX + ".data-center-id-bits", CommonConstant.NOT_GREATER, SnowflakeConstant.DYNAMIC.DATA_CENTER_ID_BITS_LIMIT);
        }
        if (properties.getWorkerIdBits() > SnowflakeConstant.DYNAMIC.WORKER_ID_BITS_LIMIT) {
            throw new BadCustomPropertiesException(SnowflakeProperties.PREFIX + ".worker-id-bits", CommonConstant.NOT_GREATER, SnowflakeConstant.DYNAMIC.WORKER_ID_BITS_LIMIT);
        }
        if ((properties.getTimestampBits() + properties.getDataCenterIdBits() + properties.getWorkerIdBits() + properties.getSequenceBits()) > SnowflakeConstant.DYNAMIC.SNOWFLAKE_BITS_LIMIT) {
            throw new BadCustomPropertiesException("The configuration of properties for snowflake id's bits is bad. " +
                    "Please ensure the sum of bits is not greater than " + SnowflakeConstant.DYNAMIC.SNOWFLAKE_BITS_LIMIT);
        }
    }
    
}
