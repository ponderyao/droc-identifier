package io.github.ponderyao.droc.data.cache;

import java.util.Date;

import io.github.ponderyao.droc.data.entity.SegmentModeSequence;

/**
 * SegmentModeSequenceCache：号段模式数据模型序列段缓存
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentModeSequenceCache {

    /**
     * 序列ID
     */
    private Long seqId;
    
    /**
     * 数据模型
     */
    private String model;
    
    /**
     * 增长步长
     */
    private Long step;

    /**
     * 最大阈值
     */
    private Long maxThreshold;

    /**
     * 当前序列
     */
    private Long currSeq;

    /**
     * 缓存注册时间
     */
    private Date registerTime;
    
    public SegmentModeSequenceCache(SegmentModeSequence sequence) {
        this.seqId = sequence.getSeqId();
        this.model = sequence.getBusinessModel();
        this.step = sequence.getStepSize();
        this.maxThreshold = sequence.getMaxThreshold();
        this.currSeq = this.maxThreshold - step;
        this.registerTime = new Date();
    }

    
    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Long getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(Long maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public Long getCurrSeq() {
        return currSeq;
    }

    public void setCurrSeq(Long currSeq) {
        this.currSeq = currSeq;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
