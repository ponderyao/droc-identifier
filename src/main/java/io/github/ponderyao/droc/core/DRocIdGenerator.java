package io.github.ponderyao.droc.core;

/**
 * DRocIdGenerator：DRoc分布式ID生成器接口定义
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface DRocIdGenerator {
    
    String getStrategyName();
    
    Long generateDRocId();
    
    boolean validateDRocId(long id);
    
}
