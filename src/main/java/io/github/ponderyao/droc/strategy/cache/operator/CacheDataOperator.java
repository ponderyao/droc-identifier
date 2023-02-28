package io.github.ponderyao.droc.strategy.cache.operator;

/**
 * CacheDataOperator：缓存数据操作接口
 *
 * @author PonderYao
 * @since 1.2.0
 */
public interface CacheDataOperator {

    /**
     * 递增
     * 
     * @param key 数据键
     * @param step 递增步长
     * @return 数据值
     */
    long increase(String key, long step);

    /**
     * 判断是否有数据
     * 
     * @param key 数据键
     * @return 布尔值
     */
    boolean containsKey(String key);
    
}
