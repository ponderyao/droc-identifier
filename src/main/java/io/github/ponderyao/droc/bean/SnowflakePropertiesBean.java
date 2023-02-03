package io.github.ponderyao.droc.bean;

import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.config.snowflake.SnowflakeProperties;
import io.github.ponderyao.droc.strategy.snowflake.register.AutoRegisterStrategyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * SnowflakePropertiesBean：雪花算法配置参数整合bean
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class SnowflakePropertiesBean {

    public static final Logger log = LoggerFactory.getLogger(SnowflakePropertiesBean.class);

    /**
     * from snowflake-properties
     */
    private int timestampBits;
    private int dataCenterIdBits;
    private int workerIdBits;
    private int sequenceBits;
    private boolean enableDataCenter;
    private long twepoch;
    private boolean enableTimestampMillisecond;
    private boolean enableAutoRegisterDataCenter;
    private boolean enableAutoRegisterWorker;
    private String autoRegisterDataCenterWay;
    private String autoRegisterWorkerWay;
    private String autoRegisterKeySuffix;
    private long dataCenterId;
    private long workerId;
    private boolean handleRewindClock;
    private long rewindClockReserve;

    /**
     * 时间戳-左移位数
     */
    private int timestampShift;
    /**
     * 机房号-左移位数
     */
    private int dataCenterIdShift;
    /**
     * 机器号-左移位数
     */
    private int workerIdShift;
    /**
     * 时间戳-掩码
     */
    private long timestampMask;
    /**
     * 机房号-掩码
     */
    private long dataCenterIdMask;
    /**
     * 机器号-掩码
     */
    private long workerIdMask;
    /**
     * 序列号-掩码
     */
    private long sequenceMask;
    /**
     * 生成上一ID的时间戳
     */
    private long lastTimestamp = -1L;
    /**
     * 当前序列号
     */
    private long currentSequence = 0L;
    /**
     * 时间回拨-当前时间戳
     */
    private long rewindClockTimestamp = -1L;
    /**
     * 时间回拨-当前序列号
     */
    private long rewindClockSequence = 0L;
    
    public SnowflakePropertiesBean(SnowflakeProperties snowflakeProperties) {
        BeanUtils.copyProperties(snowflakeProperties, this);
        transferTwepoch(snowflakeProperties.getTwepoch());
        initShift();
        initMask();
        autoRegisterDataCenter();
        autoRegisterWorker();
    }
    
    private void transferTwepoch(String twepochStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            twepoch = simpleDateFormat.parse(twepochStr).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void initShift() {
        workerIdShift = sequenceBits;
        dataCenterIdShift = workerIdShift + workerIdBits;
        timestampShift = dataCenterIdShift + dataCenterIdBits;
    }
    
    private void initMask() {
        sequenceMask = ~(-1L << sequenceBits);
        workerIdMask = ~(-1L << workerIdBits);
        dataCenterIdMask = ~(-1L << dataCenterIdBits);
        timestampMask = ~(-1L << timestampBits);
    }

    private void autoRegisterDataCenter() {
        if (this.isEnableDataCenter() && this.isEnableAutoRegisterDataCenter()) {
            AutoRegisterStrategyContext autoRegisterStrategyContext = new AutoRegisterStrategyContext();
            long dataCenterId = autoRegisterStrategyContext.register(this.getAutoRegisterDataCenterWay(), 
                    DRocConstant.SNOWFLAKE_REGISTER_KEY.DATA_CENTER + autoRegisterKeySuffix, dataCenterIdMask);
            log.info("Register data-center successfully, data-center-id is {}", dataCenterId);
            this.setDataCenterId(dataCenterId);
        }
    }

    private void autoRegisterWorker() {
        if (this.isEnableAutoRegisterWorker()) {
            AutoRegisterStrategyContext autoRegisterStrategyContext = new AutoRegisterStrategyContext();
            long workerId = autoRegisterStrategyContext.register(this.getAutoRegisterWorkerWay(), 
                    DRocConstant.SNOWFLAKE_REGISTER_KEY.WORKER + autoRegisterKeySuffix, workerIdMask);
            log.info("Register worker successfully, worker-id is {}", workerId);
            this.setWorkerId(workerId);
        }
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

    public long getTwepoch() {
        return twepoch;
    }

    public void setTwepoch(long twepoch) {
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

    public int getTimestampShift() {
        return timestampShift;
    }

    public void setTimestampShift(int timestampShift) {
        this.timestampShift = timestampShift;
    }

    public int getDataCenterIdShift() {
        return dataCenterIdShift;
    }

    public void setDataCenterIdShift(int dataCenterIdShift) {
        this.dataCenterIdShift = dataCenterIdShift;
    }

    public int getWorkerIdShift() {
        return workerIdShift;
    }

    public void setWorkerIdShift(int workerIdShift) {
        this.workerIdShift = workerIdShift;
    }

    public long getTimestampMask() {
        return timestampMask;
    }

    public void setTimestampMask(long timestampMask) {
        this.timestampMask = timestampMask;
    }

    public long getDataCenterIdMask() {
        return dataCenterIdMask;
    }

    public void setDataCenterIdMask(long dataCenterIdMask) {
        this.dataCenterIdMask = dataCenterIdMask;
    }

    public long getWorkerIdMask() {
        return workerIdMask;
    }

    public void setWorkerIdMask(long workerIdMask) {
        this.workerIdMask = workerIdMask;
    }

    public long getSequenceMask() {
        return sequenceMask;
    }

    public void setSequenceMask(long sequenceMask) {
        this.sequenceMask = sequenceMask;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public long getCurrentSequence() {
        return currentSequence;
    }

    public void setCurrentSequence(long currentSequence) {
        this.currentSequence = currentSequence;
    }

    public long getRewindClockTimestamp() {
        return rewindClockTimestamp;
    }

    public void setRewindClockTimestamp(long rewindClockTimestamp) {
        this.rewindClockTimestamp = rewindClockTimestamp;
    }

    public long getRewindClockSequence() {
        return rewindClockSequence;
    }

    public void setRewindClockSequence(long rewindClockSequence) {
        this.rewindClockSequence = rewindClockSequence;
    }
}
