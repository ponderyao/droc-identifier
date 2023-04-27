package io.github.ponderyao.droc.bean;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import io.github.ponderyao.droc.config.segment.SegmentModelProperties;
import io.github.ponderyao.droc.config.segment.SegmentProperties;
import io.github.ponderyao.droc.strategy.segment.operator.DbDataOperator;

/**
 * SegmentPropertiesBean：号段模式参数配置bean
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentPropertiesBean {

    /**
     * 数据源
     */
    private String source;
    /**
     * 是否支持默认匹配
     */
    private boolean enableDefault;
    /**
     * 默认初始偏移量
     */
    private long defaultOffset;
    /**
     * 默认增长步长
     */
    private long defaultStep;
    /**
     * 默认数据模型
     */
    private String defaultModel;
    /**
     * 数据模型映射集
     */
    private Map<String, SegmentModelProperties> models = Collections.emptyMap();
    /**
     * 数据库数据操作类
     */
    private DbDataOperator dbDataOperator;
    
    public SegmentPropertiesBean(SegmentProperties segmentProperties) {
        BeanUtils.copyProperties(segmentProperties, this);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isEnableDefault() {
        return enableDefault;
    }

    public void setEnableDefault(boolean enableDefault) {
        this.enableDefault = enableDefault;
    }

    public long getDefaultOffset() {
        return defaultOffset;
    }

    public void setDefaultOffset(long defaultOffset) {
        this.defaultOffset = defaultOffset;
    }

    public long getDefaultStep() {
        return defaultStep;
    }

    public void setDefaultStep(long defaultStep) {
        this.defaultStep = defaultStep;
    }

    public String getDefaultModel() {
        return defaultModel;
    }

    public void setDefaultModel(String defaultModel) {
        this.defaultModel = defaultModel;
    }

    public Map<String, SegmentModelProperties> getModels() {
        return models;
    }

    public void setModels(Map<String, SegmentModelProperties> models) {
        this.models = models;
    }

    public DbDataOperator getDbDataOperator() {
        return dbDataOperator;
    }

    public void setDbDataOperator(DbDataOperator dbDataOperator) {
        this.dbDataOperator = dbDataOperator;
    }
}
