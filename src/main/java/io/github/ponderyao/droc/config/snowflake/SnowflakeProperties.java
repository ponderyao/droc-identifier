package io.github.ponderyao.droc.config.snowflake;

import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.common.constant.SnowflakeConstant;
import io.github.ponderyao.droc.config.DRocIdentifierProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * SnowflakeProperties：雪花算法可配置参数
 * 
 * 雪花算法ID可使用默认配置或自定义重载配置，通过type参数控制。<p>
 * 
 * DRoc-snowflake雪花算法的特性如下：
 * 1. 默认配置可避免64位long类型ID传到前端受js最大number类型53位限制的问题；
 * 2. 支持设置机房号，用于区分不同集群中心（即机房），适用于区块链等应用；
 * 3. 同时支持机房号与机器号的自动注册或自定义（自定义默认为1，需人为控制避免冲突）；
 * 4. 机房号与机器号的自动注册方式可选、可扩展，默认基于redis缓存；
 * 5. 支持机器时钟回拨识别与处理，通过预留数来避免ID冲突。
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class SnowflakeProperties {

    public static final Logger log = LoggerFactory.getLogger(SnowflakeProperties.class);
    
    public static final String PREFIX = DRocIdentifierProperties.PREFIX + ".snowflake";
    
    
    /**
     * 雪花算法配置，默认使用默认参数
     */
    private String type = DRocConstant.SNOWFLAKE_TYPE.DEFAULT;
    /**
     * 时间戳-比特位数，默认41位
     */
    private int timestampBits = SnowflakeConstant.DEFAULT.TIMESTAMP_BITS;
    /**
     * 机房号-比特位数，默认空
     * 当且仅当enabledDataCenter为true时配置有效
     */
    private int dataCenterIdBits = SnowflakeConstant.DEFAULT.DATA_CENTER_ID_BITS;
    /**
     * 机器号-比特位数，默认6位
     */
    private int workerIdBits = SnowflakeConstant.DEFAULT.WORKER_ID_BITS;
    /**
     * 序列号-比特位数，默认6位
     */
    private int sequenceBits = SnowflakeConstant.DEFAULT.SEQUENCE_BITS;
    /**
     * 是否启用机房号，默认否
     */
    private boolean enableDataCenter = SnowflakeConstant.DEFAULT.ENABLE_DATA_CENTER;
    /**
     * 起始时间，[yyyy-MM-dd HH:mm:ss(:SSS)]格式
     */
    private String twepoch = SnowflakeConstant.DEFAULT.TWEPOCH;
    /**
     * 是否使用毫秒级时间戳，默认为是
     */
    private boolean enableTimestampMillisecond = SnowflakeConstant.DEFAULT.ENABLE_TIMESTAMP_MILLISECOND;
    /**
     * 是否自动注册机房号，默认为是
     */
    private boolean enableAutoRegisterDataCenter = SnowflakeConstant.DEFAULT.ENABLE_AUTO_REGISTER_DATA_CENTER;
    /**
     * 是否自动注册机器号，默认为是
     */
    private boolean enableAutoRegisterWorker = SnowflakeConstant.DEFAULT.ENABLE_AUTO_REGISTER_WORKER;
    /**
     * 自动注册机房号方式，默认为 redis
     */
    private String autoRegisterDataCenterWay = SnowflakeConstant.DEFAULT.AUTO_REGISTER_DATA_CENTER_WAY;
    /**
     * 自动注册机器号方式，默认为 redis
     */
    private String autoRegisterWorkerWay = SnowflakeConstant.DEFAULT.AUTO_REGISTER_WORKER_WAY;
    /**
     * 自动注册的键后缀，默认为 DEFAULT_KEY
     */
    private String autoRegisterKeySuffix = SnowflakeConstant.DEFAULT.AUTO_REGISTER_KEY_SUFFIX;
    /**
     * 自定义机房号，默认为 1
     * 当且仅当enableDataCenter和enableAutoRegisterDataCenter同时为true时配置有效
     */
    private long dataCenterId = SnowflakeConstant.DEFAULT.DATA_CENTER_ID;
    /**
     * 自定义机器号，默认为 1
     * 当且仅当enableAutoRegisterWorker为ture时配置有效
     */
    private long workerId = SnowflakeConstant.DEFAULT.WORKER_ID;
    /**
     * 是否处理时间回拨，默认否
     */
    private boolean handleRewindClock = SnowflakeConstant.DEFAULT.HANDLE_REWIND_CLOCK;
    /**
     * 时间回拨预留数，默认为 3
     */
    private long rewindClockReserve = SnowflakeConstant.DEFAULT.REWIND_CLOCK_RESERVE;
    
    public void resetProperties(Map<String, Object> defaultProperties) {
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(this);
                Object targetValue = defaultProperties.get(fieldName);
                if (targetValue == null) {
                    continue;
                }
                if (!fieldValue.equals(defaultProperties.get(fieldName))) {
                    log.warn("The configuration of properties \"{}.{}\" is invalid. " +
                            "If it is necessary, please set the properties \"{}.type\" to \"dynamic\"", PREFIX, fieldName, PREFIX);
                }
                field.set(this, targetValue);
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimestampBits() {
        return timestampBits;
    }

    public void setTimestampBits(int timestampBits) {
        this.timestampBits = timestampBits;
    }

    public int getDataCenterIdBits() {
        return dataCenterIdBits;
    }

    public void setDataCenterIdBits(int dataCenterIdBits) {
        this.dataCenterIdBits = dataCenterIdBits;
    }

    public int getWorkerIdBits() {
        return workerIdBits;
    }

    public void setWorkerIdBits(int workerIdBits) {
        this.workerIdBits = workerIdBits;
    }

    public int getSequenceBits() {
        return sequenceBits;
    }

    public void setSequenceBits(int sequenceBits) {
        this.sequenceBits = sequenceBits;
    }

    public boolean isEnableDataCenter() {
        return enableDataCenter;
    }

    public void setEnableDataCenter(boolean enableDataCenter) {
        this.enableDataCenter = enableDataCenter;
    }

    public String getTwepoch() {
        return twepoch;
    }

    public void setTwepoch(String twepoch) {
        this.twepoch = twepoch;
    }

    public boolean isEnableTimestampMillisecond() {
        return enableTimestampMillisecond;
    }

    public void setEnableTimestampMillisecond(boolean enableTimestampMillisecond) {
        this.enableTimestampMillisecond = enableTimestampMillisecond;
    }

    public boolean isEnableAutoRegisterDataCenter() {
        return enableAutoRegisterDataCenter;
    }

    public void setEnableAutoRegisterDataCenter(boolean enableAutoRegisterDataCenter) {
        this.enableAutoRegisterDataCenter = enableAutoRegisterDataCenter;
    }

    public boolean isEnableAutoRegisterWorker() {
        return enableAutoRegisterWorker;
    }

    public void setEnableAutoRegisterWorker(boolean enableAutoRegisterWorker) {
        this.enableAutoRegisterWorker = enableAutoRegisterWorker;
    }

    public String getAutoRegisterDataCenterWay() {
        return autoRegisterDataCenterWay;
    }

    public void setAutoRegisterDataCenterWay(String autoRegisterDataCenterWay) {
        this.autoRegisterDataCenterWay = autoRegisterDataCenterWay;
    }

    public String getAutoRegisterWorkerWay() {
        return autoRegisterWorkerWay;
    }

    public void setAutoRegisterWorkerWay(String autoRegisterWorkerWay) {
        this.autoRegisterWorkerWay = autoRegisterWorkerWay;
    }

    public String getAutoRegisterKeySuffix() {
        return autoRegisterKeySuffix;
    }

    public void setAutoRegisterKeySuffix(String autoRegisterKeySuffix) {
        this.autoRegisterKeySuffix = autoRegisterKeySuffix;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public boolean isHandleRewindClock() {
        return handleRewindClock;
    }

    public void setHandleRewindClock(boolean handleRewindClock) {
        this.handleRewindClock = handleRewindClock;
    }

    public long getRewindClockReserve() {
        return rewindClockReserve;
    }

    public void setRewindClockReserve(long rewindClockReserve) {
        this.rewindClockReserve = rewindClockReserve;
    }
}
