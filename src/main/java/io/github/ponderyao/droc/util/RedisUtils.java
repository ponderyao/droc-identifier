package io.github.ponderyao.droc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtils：Redis客户端工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Component("dRocRedisUtils")
public class RedisUtils {
    
    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;
    
    private static StringRedisTemplate redisTemplate;
    
    @PostConstruct
    public void init() {
        redisTemplate = stringRedisTemplate;
    }
    
    public static String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    public static void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    public static void set(String key, String value, long expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.SECONDS);
    }
    
    public static long incr(String key, long val) {
        return redisTemplate.boundValueOps(key).increment(val);
    }
    
    public static boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    public static boolean lock(String key, String value, long timeout) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS));
    }
    
    public static boolean unlock(String key, String value) {
        String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(lua, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
        return result != null && result == 1L;
    }
    
}
