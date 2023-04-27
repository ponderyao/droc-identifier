package io.github.ponderyao.droc.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;

/**
 * CaffeineUtils：Caffeine缓存工具类
 *
 * @author PonderYao
 * @since 1.3.0
 */
@Component(value = "dRocCaffeineUtils")
public class CaffeineUtils {
    
    @Autowired
    private Cache<String, Object> caffeineCache;
    
    private static Cache<String, Object> localCache;

    @PostConstruct
    public void init() {
        localCache = caffeineCache;
    }

    /**
     * 添加或更新缓存
     *
     * @param key 缓存键
     * @param value 缓存值
     */
    public static void setCache(String key, Object value) {
        localCache.put(key, value);
    }

    /**
     * 获取对象缓存
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public static <T> T getCache(String key) {
        localCache.getIfPresent(key);
        return (T) localCache.asMap().get(key);
    }

    /**
     * 删除缓存
     * 
     * @param key 缓存键
     */
    public static void removeCache(String key) {
        localCache.asMap().remove(key);
    }
}
