package io.github.ponderyao.droc.common.constant;

/**
 * DRocConstant：DRoc常量
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DRocConstant {
    
    public static final String DROC_SIGN = "DROC";
    
    public static class DROC_STRATEGY {
        public static final String SNOWFLAKE = "droc-snowflake";
        public static final String CACHE = "droc-cache";
        public static final String SEGMENT = "droc-segment";
        private DROC_STRATEGY() {}
    }
    
    public static class SNOWFLAKE_TYPE {
        public static final String DEFAULT = "default";
        public static final String NATIVE = "native";
        public static final String DYNAMIC = "dynamic";
        private SNOWFLAKE_TYPE() {}
    }
    
    public static class SNOWFLAKE_REGISTER_WAY {
        public static final String REDIS = "redis";
        private SNOWFLAKE_REGISTER_WAY() {}
    }
    
    public static class SNOWFLAKE_REGISTER_KEY {
        public static final String DATA_CENTER = "DATA_CENTER_";
        public static final String WORKER = "WORKER_";
        private SNOWFLAKE_REGISTER_KEY() {}
    }
    
    public static class CACHE_TYPE {
        public static final String REDIS = "redis";
        private CACHE_TYPE() {}
    }
    
}
