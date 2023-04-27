package io.github.ponderyao.droc.config.segment;

import io.github.ponderyao.droc.common.constant.SegmentConstant;

/**
 * SegmentModelProperties：号段模式数据模型可配置参数
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentModelProperties {
    
    public static final String PREFIX = SegmentProperties.PREFIX + ".models";
    
    /**
     * 初始偏移量，默认为 1
     */
    private long offset = SegmentConstant.DEFAULT_OFFSET;
    /**
     * 增长步长
     */
    private long step = SegmentConstant.DEFAULT_STEP;
    /**
     * 是否支持动态步长，默认否
     */
    private boolean enableDynamicStep = SegmentConstant.DEFAULT_ENABLE_DYNAMIC_STEP;
    /**
     * 最小步长阈值
     */
    private long minStep = SegmentConstant.DEFAULT_MIN_STEP;
    /**
     * 最大步长阈值
     */
    private long maxStep = SegmentConstant.DEFAULT_MAX_STEP;
    /**
     * 秒级吞吐量
     */
    private double tps = SegmentConstant.DEFAULT_TPS;


    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    public boolean isEnableDynamicStep() {
        return enableDynamicStep;
    }

    public void setEnableDynamicStep(boolean enableDynamicStep) {
        this.enableDynamicStep = enableDynamicStep;
    }

    public long getMinStep() {
        return minStep;
    }

    public void setMinStep(long minStep) {
        this.minStep = minStep;
    }

    public long getMaxStep() {
        return maxStep;
    }

    public void setMaxStep(long maxStep) {
        this.maxStep = maxStep;
    }

    public double getTps() {
        return tps;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }
}
