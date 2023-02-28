package io.github.ponderyao.droc.strategy.cache.operator;

import io.github.ponderyao.droc.util.RedisUtils;

/**
 * RedisCacheDataOperator：Redis缓存数据操作类
 *
 * @author PonderYao
 * @since 1.2.0
 */
public class RedisCacheDataOperator implements CacheDataOperator {
    
    @Override
    public long increase(String key, long step) {
        return RedisUtils.incr(key, step);
    }

    @Override
    public boolean containsKey(String key) {
        return RedisUtils.exists(key);
    }

}
