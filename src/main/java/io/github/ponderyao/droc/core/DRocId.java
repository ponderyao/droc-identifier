package io.github.ponderyao.droc.core;

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
    
    public DRocId(String model) {
        this.value = DRocUtils.createDRocId(model);
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
    
    public String getStrategy() {
        return DRocUtils.getDRocStrategy();
    }
    
}
