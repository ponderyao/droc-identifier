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
    private static final String REGISTER_LOCK_KEY = DRocConstant.DROC_SIGN + "_REGISTER_LOCK";
    
    @Override
    public boolean match(String type) {
        return DRocConstant.SNOWFLAKE_REGISTER_WAY.REDIS.equals(type);
    }

    @Override
    public long execute(String key, long superiorLimit) {
        String systemSign = System.getProperty("user.dir");
        String registerType = key.substring(0, key.length() - 1);
        String registerErrorMsg = "Redis auto register snowflake-id " + registerType + " failed";
        try {
            int restTryLockTimes = 10;
            while (restTryLockTimes > 0 && !RedisUtils.lock(REGISTER_LOCK_KEY, systemSign, 30)) {
                restTryLockTimes--;
                Thread.sleep(1000);
            }
            if (restTryLockTimes <= 0) {
                throw new RuntimeException(registerErrorMsg + ": cannot get the redis lock");
            }
            return tryRegister(key, superiorLimit, registerErrorMsg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            RedisUtils.unlock(REGISTER_LOCK_KEY, systemSign);
        }
    }
    
    private long tryRegister(String key, long superiorLimit, String registerErrorMsg) {
        int round = 0;
        long registerIndex;
        String registerIndexKey;
        while (round < MAX_REGISTER_RETRY_ROUNDS) {
            registerIndex = RedisUtils.incr(key, 1);
            if (registerIndex > superiorLimit) {
                round++;
                registerIndex = MIN_REGISTER_INDEX;
                RedisUtils.set(key, String.valueOf(registerIndex));
            }
            registerIndexKey = key + "_" + registerIndex;
            if (!RedisUtils.exists(registerIndexKey)) {
                occupy(registerIndexKey);
                return registerIndex;
            }
        }
        throw new AutoRegisterFailException(registerErrorMsg);
    }
    
    private void occupy(String key) {
        Thread thread = new RedisAutoRegisterThread(key, REGISTER_OCCUPY_SIGN, REGISTER_EXPIRATION);
        thread.start();
    }
}
