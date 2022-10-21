package io.github.ponderyao.droc.strategy.snowflake.register;

import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.exception.snowflake.AutoRegisterFailException;
import io.github.ponderyao.droc.util.RedisUtils;

/**
 * RedisAutoRegisterStrategy：基于Redis的自动注册策略实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RedisAutoRegisterStrategy implements AutoRegisterStrategy {
    
    private static final int MAX_REGISTER_RETRY_ROUNDS = 5;
    private static final long MIN_REGISTER_INDEX = 1L;
    private static final long REGISTER_EXPIRATION = 60;
    private static final String REGISTER_OCCUPY_SIGN = "occupied";
    
    @Override
    public boolean match(String type) {
        return DRocConstant.SNOWFLAKE_REGISTER_WAY.REDIS.equals(type);
    }

    @Override
    public long execute(String key, long superiorLimit) {
        int round = 0;
        long registerIndex;
        String registerIndexKey;
        while (round < MAX_REGISTER_RETRY_ROUNDS) {
            registerIndex = RedisUtils.incr(key);
            if (registerIndex > superiorLimit) {
                round++;
                registerIndex = MIN_REGISTER_INDEX;
                RedisUtils.set(key, registerIndex);
            }
            registerIndexKey = key + "_" + registerIndex;
            if (!RedisUtils.exists(registerIndexKey)) {
                occupy(registerIndexKey);
                return registerIndex;
            }
        }
        String registerType = key.substring(0, key.length() - 1);
        throw new AutoRegisterFailException("Redis auto register snowflake-id " + registerType + " failed");
    }
    
    private void occupy(String key) {
        Thread thread = new RedisHeartBeatConnectionRegister(key, REGISTER_OCCUPY_SIGN, REGISTER_EXPIRATION);
        thread.start();
    }
    
}
