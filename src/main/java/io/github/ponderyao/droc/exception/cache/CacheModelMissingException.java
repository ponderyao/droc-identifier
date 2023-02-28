package io.github.ponderyao.droc.exception.cache;

/**
 * CacheModelMissingException：缓存模型缺失异常
 *
 * @author PonderYao
 * @since 1.2.0
 */
public class CacheModelMissingException extends RuntimeException {
    
    public CacheModelMissingException(String message) {
        super(message);
    }
    
    public CacheModelMissingException(String cacheType, String cacheModel) {
        this("The configuration of model '" + cacheModel + "' is missing in cache " + cacheType + ". " +
                "Please ensure the configuration of models are complete before you use them in your program.");
    }
    
}
