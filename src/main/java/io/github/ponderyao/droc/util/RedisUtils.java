package io.github.ponderyao.droc.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * RedisUtils：Redis 客户端工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class RedisUtils {
    
    private static final RedissonClient redissonClient = SpringBeanUtils.getBean(RedissonClient.class);
    
    public static String get(String key) {
        return (String) redissonClient.getBucket(key).get();
    }
    
    public static void set(String key, String value) {
        redissonClient.getBucket(key).set(value);
    }
    
    public static void set(String key, long value) {
        redissonClient.getAtomicLong(key).set(value);
    }
    
    public static void set(String key, String value, long expiration) {
        redissonClient.getBucket(key).set(value, expiration, TimeUnit.SECONDS);
    }
    
    public static long incr(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }
    
    public static boolean exists(String key) {
        return redissonClient.getBucket(key).isExists();
    }
    
    public static RLock getLock(String key) {
        return redissonClient.getLock(key);
    }
    
    public static boolean lock(RLock lock) throws InterruptedException {
        return lock.tryLock(10, 10, TimeUnit.SECONDS);
    }
    
    public static void unlock(RLock lock) {
        lock.unlock();
    }
    
}
