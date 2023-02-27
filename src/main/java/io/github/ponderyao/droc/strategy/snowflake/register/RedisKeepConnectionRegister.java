package io.github.ponderyao.droc.strategy.snowflake.register;

import io.github.ponderyao.droc.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RedisKeepConnectionRegister：Redis心跳连接注册器
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RedisKeepConnectionRegister {
    
    public static final Logger log = LoggerFactory.getLogger(RedisKeepConnectionRegister.class);
    
    private static final int retryLimit = 5;
    
    private static final long heartbeatInterval = 10000;
    
    private final String key;
    private final String value;
    private final long expiration;
    
    public RedisKeepConnectionRegister(String key, String value, long expiration) {
        this.key = key;
        this.value = value;
        this.expiration = expiration;
    }
    
    public void run() {
        int retry = retryLimit;
        while (retry > 0) {
            try {
                if (retry < retryLimit) {
                    Thread.sleep(heartbeatInterval);
                }
                keepRegister();
                retry = retryLimit;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                retry--;
            }
        }
        throw new RuntimeException("Keep Redis heartbeat connection failed");
    }
    
    private void keepRegister() {
        try {
            RedisUtils.set(key, value, expiration);
            Thread.sleep(heartbeatInterval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public long getExpiration() {
        return expiration;
    }
}
