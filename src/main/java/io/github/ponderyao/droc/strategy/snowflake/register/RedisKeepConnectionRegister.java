package io.github.ponderyao.droc.strategy.snowflake.register;

import io.github.ponderyao.droc.util.RedisUtils;

/**
 * RedisKeepConnectionRegister：Redis心跳连接注册器
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RedisKeepConnectionRegister extends Thread {
    
    private final String key;
    private final String value;
    private final long expiration;
    
    public RedisKeepConnectionRegister(String key, String value, long expiration) {
        this.key = key;
        this.value = value;
        this.expiration = expiration;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                keepRegister();
            } catch (Exception e) {
                break;
            }
        }
        throw new RuntimeException("Keep Redis heart-beat connection failed");
    }
    
    private void keepRegister() {
        try {
            RedisUtils.set(key, value, expiration);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
}
