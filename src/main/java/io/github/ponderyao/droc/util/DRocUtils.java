package io.github.ponderyao.droc.util;

import io.github.ponderyao.droc.core.DRocIdGenerator;

/**
 * DRocUtils：DRoc 核心工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DRocUtils {
    
    public static DRocIdGenerator dRocIdGenerator = SpringBeanUtils.getBean(DRocIdGenerator.class);
    
    public static long createDRocId() {
        return dRocIdGenerator.generateDRocId();
    }
    
    public static boolean checkDRocId(long id) {
        return dRocIdGenerator.validateDRocId(id);
    }
    
}
