package io.github.ponderyao.droc.common.constant;

/**
 * SnowflakeConstant：
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class SnowflakeConstant {
    
    public static class DEFAULT {
        public static final int TIMESTAMP_BITS = 41;
        public static final int WORKER_ID_BITS = 6;
        public static final int DATA_CENTER_ID_BITS = 6 - WORKER_ID_BITS;
        public static final int SEQUENCE_BITS = 6;
        public static final boolean ENABLE_DATA_CENTER = false;
        public static final String TWEPOCH = "2022-01-01 00:00:00";
        public static final boolean ENABLE_TIMESTAMP_MILLISECOND = true;
        public static final boolean ENABLE_AUTO_REGISTER_DATA_CENTER = true;
        public static final boolean ENABLE_AUTO_REGISTER_WORKER = true;
        public static final String AUTO_REGISTER_DATA_CENTER_WAY = DRocConstant.SNOWFLAKE_REGISTER_WAY.REDIS;
        public static final String AUTO_REGISTER_WORKER_WAY = DRocConstant.SNOWFLAKE_REGISTER_WAY.REDIS;
        public static final String AUTO_REGISTER_KEY_SUFFIX = "DEFAULT_KEY";
        public static final long DATA_CENTER_ID = 1L;
        public static final long WORKER_ID = 1L;
        public static final boolean HANDLE_REWIND_CLOCK = false;
        public static final long REWIND_CLOCK_RESERVE = 3L;
        private DEFAULT() {}
    }
    
    public static class NATIVE {
        public static final int TIMESTAMP_BITS = 41;
        public static final int WORKER_ID_BITS = 5;
        public static final int DATA_CENTER_ID_BITS = 10 - WORKER_ID_BITS;
        public static final int SEQUENCE_BITS = 12;
        public static final boolean ENABLE_DATA_CENTER = true;
        public static final String TWEPOCH = "2022-01-01 00:00:00";
        public static final boolean ENABLE_TIMESTAMP_MILLISECOND = true;
        public static final boolean ENABLE_AUTO_REGISTER_DATA_CENTER = true;
        public static final boolean ENABLE_AUTO_REGISTER_WORKER = true;
        public static final String AUTO_REGISTER_DATA_CENTER_WAY = DRocConstant.SNOWFLAKE_REGISTER_WAY.REDIS;
        public static final String AUTO_REGISTER_WORKER_WAY = DRocConstant.SNOWFLAKE_REGISTER_WAY.REDIS;
        public static final String AUTO_REGISTER_KEY_SUFFIX = "NATIVE_KEY";
        public static final long DATA_CENTER_ID = 1L;
        public static final long WORKER_ID = 1L;
        public static final boolean HANDLE_REWIND_CLOCK = false;
        public static final long REWIND_CLOCK_RESERVE = 3L;
        private NATIVE() {}
    }
    
    public static class DYNAMIC {
        public static final int SNOWFLAKE_BITS_LIMIT = 63;
        public static final int TIMESTAMP_BITS_LIMIT = 51;
        public static final int WORKER_ID_BITS_LIMIT = 30;
        public static final int DATA_CENTER_ID_BITS_LIMIT = 30;
        public static final String TWEPOCH_LIMIT = "2000-01-01 00:00:00";
    }
    
}
