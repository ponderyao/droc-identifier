package io.github.ponderyao.droc.core;

import io.github.ponderyao.droc.util.SpringBeanUtils;

/**
 * DRocUtils：DRoc核心工具类
 *
 * @author PonderYao
 * @since 1.1.0
 */
public class DRocUtils {
    
    public static DRocIdGenerator dRocIdGenerator = SpringBeanUtils.getBean(DRocIdGenerator.class);
    
    protected static long createDRocId() {
        return dRocIdGenerator.generateDRocId();
    }
    
    protected static long createDRocId(String model) {
        return dRocIdGenerator.generateDRocId(model);
    }

    protected static boolean checkDRocId(long id) {
        return dRocIdGenerator.validateDRocId(id);
    }
    
    public static String getDRocStrategy() {
        return dRocIdGenerator.getStrategyName();
    }
    
}
