package io.github.ponderyao.droc.core;

import io.github.ponderyao.droc.util.DRocUtils;

/**
 * DRocId：DRoc分布式ID
 *
 * @author PonderYao
 * @since 1.0.0
 */
public abstract class DRocId {
    
    private final Long value;
    
    public DRocId() {
        this.value = DRocUtils.createDRocId();
    }
    
    public DRocId(Long value) {
        this.value = value;
    }
    
    public Long getValue() {
        return value;
    }
    
    public boolean validateValue() {
        return DRocUtils.checkDRocId(value);
    }
    
}
