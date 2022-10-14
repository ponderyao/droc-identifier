package io.github.ponderyao.droc.core;

/**
 * DRocIdGenerator：
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface DRocIdGenerator {
    
    Long generateDRocId();
    
    boolean validateDRocId(long id);
    
}
