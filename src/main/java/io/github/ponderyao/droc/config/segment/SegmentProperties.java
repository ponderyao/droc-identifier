package io.github.ponderyao.droc.config.segment;

import java.util.Collections;
import java.util.Map;

import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.common.constant.SegmentConstant;
import io.github.ponderyao.droc.config.DRocIdentifierProperties;

/**
 * SegmentProperties：号段模式可配置参数
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentProperties {

    public static final String PREFIX = DRocIdentifierProperties.PREFIX + ".segment";

    /**
     * 数据源配置，默认 mysql
     */
    private String source = DRocConstant.SEGMENT_SOURCE.MYSQL;
    /**
     * 是否支持默认匹配，默认是
     */
    private boolean enableDefault = SegmentConstant.DEFAULT_ENABLE_DEFAULT;
    /**
     * 默认初始偏移量，默认 1
     */
    private long defaultOffset = SegmentConstant.DEFAULT_OFFSET;
    /**
     * 默认增长步长，默认 1000
     */
    private long defaultStep = SegmentConstant.DEFAULT_STEP;
    /**
     * 默认数据模型，默认为 default
     */
    private String defaultModel = SegmentConstant.DEFAULT_BUSINESS_MODEL;
    /**
     * 数据模型，默认为空
     */
    private Map<String, SegmentModelProperties> models = Collections.emptyMap();

    
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
}
