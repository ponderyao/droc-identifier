package io.github.ponderyao.droc.core;

import io.github.ponderyao.droc.bean.SnowflakePropertiesBean;
import io.github.ponderyao.droc.common.constant.CommonConstant;
import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.exception.snowflake.SystemClockRewindException;
import io.github.ponderyao.droc.util.ReflectionUtils;
import io.github.ponderyao.droc.util.SpringBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SnowflakeDRocIdGenerator：雪花算法ID生成器
 * 
 * 维护一个单例静态内部类 SnowflakeIdentifierCreator，作为雪花算法ID生成的核心实现，
 * 其调度权由且仅由 SnowflakeDRocIdGenerator 管理。
 * 
 * 同时，将雪花算法ID的生成与校验功能分离：SnowflakeIdentifierCreator 仅负责ID的生
 * 成，校验则由 SnowflakeDRocIdGenerator 负责。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class SnowflakeDRocIdGenerator implements DRocIdGenerator {

    public static final Logger log = LoggerFactory.getLogger(SnowflakeDRocIdGenerator.class);
    
    public static final String DROC_STRATEGY = DRocConstant.DROC_STRATEGY.SNOWFLAKE;
    
    private final SnowflakePropertiesBean propertiesBean;
    
    public SnowflakeDRocIdGenerator(SnowflakePropertiesBean snowflakePropertiesBean) {
        this.propertiesBean = snowflakePropertiesBean;
        SnowflakeIdentifierCreator.getInstance();
    }
    
    @Override
    public String getStrategyName() {
        return DROC_STRATEGY;
    }
    
    @Override
    public Long generateDRocId() {
        return SnowflakeIdentifierCreator.getInstance().nextId();
    }
    
    @Override
    public Long generateDRocId(String model) {
        log.warn("The DRoc-Snowflake ID generator does not support model differentiation temporarily!");
        return generateDRocId();
    }
    
    @Override
    public boolean validateDRocId(long id) {
        long timestamp = getTimestamp(id);
        long dataCenterId = getDataCenterId(id);
        long workerId = getWorkerId(id);
        return timestamp > propertiesBean.getTwepoch()
                && dataCenterId == propertiesBean.getDataCenterId()
                && workerId == propertiesBean.getWorkerId()
                && (id >> 63) == 0;
    }
    
    private long getTimestamp(long id) {
        return ((id >> propertiesBean.getTimestampShift()) & propertiesBean.getTimestampMask()) + propertiesBean.getTwepoch();
    }
    
    private long getDataCenterId(long id) {
        if (propertiesBean.isEnableDataCenter()) {
            return (id >> propertiesBean.getDataCenterIdShift()) & propertiesBean.getDataCenterIdMask();
        }
        return 1L;
    }
    
    private long getWorkerId(long id) {
        return (id >> propertiesBean.getWorkerIdShift()) & propertiesBean.getWorkerIdMask();
    }
    
    private static class SnowflakeIdentifierCreator {
        private static final Logger log = LoggerFactory.getLogger(SnowflakeIdentifierCreator.class);
        
        private static volatile SnowflakeIdentifierCreator INSTANCE;
        
        private static long twepoch;
        private static boolean enableDataCenter;
        private static boolean enableTimestampMillisecond;
        private static long dataCenterId;
        private static long workerId;
        private static boolean handleRewindClock;
        private static long rewindClockReserve;
        private static int timestampShift;
        private static int dataCenterIdShift;
        private static int workerIdShift;
        private static long timestampMask;
        private static long dataCenterIdMask;
        private static long workerIdMask;
        private static long sequenceMask;
        private static long lastTimestamp;
        private static long currentSequence;
        private static long rewindClockTimestamp;
        private static long rewindClockSequence;
        
        private SnowflakeIdentifierCreator() {}
        
        public static synchronized SnowflakeIdentifierCreator getInstance() {
            if (INSTANCE == null) {
                synchronized (SnowflakeIdentifierCreator.class) {
                    if (INSTANCE == null) {
                        INSTANCE = initInstance();
                    }
                }
            }
            return INSTANCE;
        }
        
        private static SnowflakeIdentifierCreator initInstance() {
            SnowflakePropertiesBean propertiesBean = SpringBeanUtils.getBean(SnowflakePropertiesBean.class);
            
            List<String> ignoreFields = new ArrayList<>();
            ignoreFields.add("INSTANCE");
            ignoreFields.add("log");
            ReflectionUtils.copyPropertiesAsStatics(SnowflakeIdentifierCreator.class, propertiesBean, ignoreFields);

            SnowflakeIdentifierCreator instance = new SnowflakeIdentifierCreator();
            instance.correctBeginningSequence();
            log.info("Init DRoc snowflake identifier: " + instance.propertiesToString(ignoreFields));
            return instance;
        }
        
        public synchronized long nextId() {
            long currentTimestamp = getCurrentTime();
            if (currentTimestamp < lastTimestamp) {
                // 时钟回拨问题处理
                return processRewindClock();
            }
            if (currentTimestamp == lastTimestamp) {
                currentSequence = (currentSequence + 1) & sequenceMask;
                if (currentSequence == 0L) {
                    currentTimestamp = tilNextTimeUnit();
                }
            } else {
                currentSequence = 0L;
            }
            lastTimestamp = currentTimestamp;
            beforeFillIdentifier();
            return fillIdentifier(lastTimestamp, currentSequence);
        }

        /**
         * 获取当前时间戳
         * @return 秒级/毫秒级时间戳
         */
        private long getCurrentTime() {
            long currentTime = System.currentTimeMillis();
            return enableTimestampMillisecond ? currentTime : currentTime / CommonConstant.MILLISECOND_SIZE;
        }

        /**
         * 处理时钟回拨
         * @return ID
         */
        private long processRewindClock() {
            if (!handleRewindClock) {
                // 不处理时钟回拨问题，直接抛异常
                throw new SystemClockRewindException();
            }
            if (rewindClockTimestamp < 0) {
                rewindClockTimestamp = lastTimestamp - 1;
                rewindClockSequence++;
                if (rewindClockSequence > rewindClockReserve) {
                    rewindClockSequence = 0L;
                }
            }
            long id = fillIdentifier(rewindClockTimestamp, rewindClockSequence);
            rewindClockTimestamp--;
            return id;
        }

        /**
         * 时间阻塞直到下一时间单位
         * @return 时间戳
         */
        private long tilNextTimeUnit() {
            long currentTimestamp = getCurrentTime();
            while (currentTimestamp == lastTimestamp) {
                currentTimestamp = getCurrentTime();
            }
            return currentTimestamp;
        }

        /**
         * 矫正序列号
         */
        private void correctBeginningSequence() {
            if (currentSequence == 0L && handleRewindClock) {
                // 需处理时间回拨，空出时钟回拨预留数
                currentSequence += rewindClockReserve;
            }
        }

        /**
         * 填充生成 ID 前处理
         */
        private void beforeFillIdentifier() {
            correctBeginningSequence();
            if (handleRewindClock) {
                rewindClockTimestamp = -1L;
            }
        }

        /**
         * ID 填充生成
         * @param timestamp 时间戳
         * @param sequence 序列号
         * @return ID
         */
        private long fillIdentifier(long timestamp, long sequence) {
            return ((timestamp - twepoch) << timestampShift)
                    | (dataCenterId << dataCenterIdShift)
                    | (workerId << workerIdShift)
                    | sequence;
        }

        private String propertiesToString(List<String> ignoreFields) {
            Map<String, Object> propertiesMap = ReflectionUtils.staticPropertiesToMap(SnowflakeIdentifierCreator.class, ignoreFields);
            StringBuffer buffer = new StringBuffer();
            propertiesMap.forEach((key, value) -> buffer.append(key).append("[").append(value).append("]").append(","));
            String str = buffer.toString();
            return str.substring(0, str.length() - 1);
        }
        
    }
    
}
