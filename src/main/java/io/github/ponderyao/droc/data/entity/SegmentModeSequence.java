package io.github.ponderyao.droc.data.entity;

import java.sql.Timestamp;

/**
 * SegmentModeSequence：号段模式序列
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentModeSequence {

    /**
     * 序列ID
     */
    private Long seqId;

    /**
     * 业务模型
     */
    private String businessModel;

    /**
     * 最大阈值
     */
    private Long maxThreshold;

    /**
     * 增长步长
     */
    private Long stepSize;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
    
    public static SegmentModeSequenceBuilder builder() {
        return new SegmentModeSequenceBuilder();
    }
    
    public static class SegmentModeSequenceBuilder {
        private String businessModel;
        private Long maxThreshold;
        private Long stepSize;
        private Long version;
        
        public SegmentModeSequenceBuilder businessModel(String businessModel) {
            this.businessModel = businessModel;
            return this;
        }
        
        public SegmentModeSequenceBuilder maxThreshold(Long maxThreshold) {
            this.maxThreshold = maxThreshold;
            return this;
        }

        public SegmentModeSequenceBuilder stepSize(Long stepSize) {
            this.stepSize = stepSize;
            return this;
        }

        public SegmentModeSequenceBuilder version(Long version) {
            this.version = version;
            return this;
        }
        
        public SegmentModeSequence build() {
            SegmentModeSequence sequence = new SegmentModeSequence();
            sequence.setBusinessModel(this.businessModel);
            sequence.setMaxThreshold(this.maxThreshold);
            sequence.setStepSize(this.stepSize);
            sequence.setVersion(this.version);
            return sequence;
        }
    }

    
    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(String businessModel) {
        this.businessModel = businessModel;
    }

    public Long getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(Long maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public Long getStepSize() {
        return stepSize;
    }

    public void setStepSize(Long stepSize) {
        this.stepSize = stepSize;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
