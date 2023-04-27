package io.github.ponderyao.droc.core;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import io.github.ponderyao.droc.bean.SegmentPropertiesBean;
import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.common.constant.SegmentConstant;
import io.github.ponderyao.droc.config.segment.SegmentModelProperties;
import io.github.ponderyao.droc.data.cache.SegmentModeSequenceCache;
import io.github.ponderyao.droc.data.entity.SegmentModeSequence;
import io.github.ponderyao.droc.exception.segment.SegmentModelMissingException;
import io.github.ponderyao.droc.strategy.segment.operator.DbDataOperator;
import io.github.ponderyao.droc.strategy.segment.rule.DynamicStepRule;
import io.github.ponderyao.droc.util.CaffeineUtils;

/**
 * SegmentDRocIdGenerator：号段模式ID生成器
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentDRocIdGenerator implements DRocIdGenerator {

    public static final Logger log = LoggerFactory.getLogger(SegmentDRocIdGenerator.class);
    
    public static final String DROC_STRATEGY = DRocConstant.DROC_STRATEGY.SEGMENT;
    
    private final SegmentPropertiesBean propertiesBean;
    
    @Override
    public String getStrategyName() {
        return DROC_STRATEGY;
    }

    @Override
    public Long generateDRocId() {
        if (!propertiesBean.isEnableDefault()) {
            throw new SegmentModelMissingException();
        }
        log.warn("It is suggested to define business model better than totally using default business key!");
        return nextId(propertiesBean.getDefaultModel());
    }

    @Override
    public Long generateDRocId(String model) {
        Map<String, SegmentModelProperties> models = propertiesBean.getModels();
        if (models.containsKey(model)) {
            SegmentModelProperties modelProperties = models.get(model);
            SegmentModelProperties tempProperties = new SegmentModelProperties();
            BeanUtils.copyProperties(modelProperties, tempProperties);
            return nextId(model, tempProperties);
        }
        if (!propertiesBean.isEnableDefault()) {
            throw new SegmentModelMissingException(model);
        }
        return nextId(model);
    }

    @Override
    public boolean validateDRocId(long id) {
        return true;
    }
    
    public SegmentDRocIdGenerator(SegmentPropertiesBean segmentPropertiesBean) {
        this.propertiesBean = segmentPropertiesBean;
    }
    
    private Long nextId(String key) {
        SegmentModelProperties tempProperties = new SegmentModelProperties();
        tempProperties.setOffset(propertiesBean.getDefaultOffset());
        tempProperties.setStep(propertiesBean.getDefaultStep());
        return nextId(key, tempProperties);
    }
    
    private Long nextId(String key, long offset, long step) {
        SegmentModeSequenceCache sequenceCache = CaffeineUtils.getCache(key);
        if (Objects.isNull(sequenceCache) || Objects.equals(sequenceCache.getCurrSeq(), sequenceCache.getMaxThreshold())) {
            SegmentModeSequence sequence = registerSequence(key, offset, step);
            sequenceCache = new SegmentModeSequenceCache(sequence);
        }
        Long id = sequenceCache.getCurrSeq();
        sequenceCache.setCurrSeq(id++);
        CaffeineUtils.setCache(key, sequenceCache);
        return id;
    }
    
    private Long nextId(String key, SegmentModelProperties modelProperties) {
        SegmentModeSequenceCache sequenceCache = CaffeineUtils.getCache(key);
        if (Objects.isNull(sequenceCache) || Objects.equals(sequenceCache.getCurrSeq(), sequenceCache.getMaxThreshold())) {
            processBeforeRegister(key, modelProperties, sequenceCache);
            SegmentModeSequence sequence = registerSequence(key, modelProperties.getOffset(), modelProperties.getStep());
            sequenceCache = new SegmentModeSequenceCache(sequence);
        }
        Long id = sequenceCache.getCurrSeq();
        sequenceCache.setCurrSeq(++id);
        CaffeineUtils.setCache(key, sequenceCache);
        return id;
    }
    
    private SegmentModeSequence registerSequence(String key, long offset, long step) {
        DbDataOperator dbDataOperator = propertiesBean.getDbDataOperator();
        SegmentModeSequence sequence;
        while (true) {
            sequence = dbDataOperator.selectSequence(key);
            if (sequence == null) {
                // try to create a new sequence and save it.
                sequence = createSequence(key, step + offset, step, SegmentConstant.DEFAULT_VERSION);
                if (dbDataOperator.insertSequence(sequence) > 0) {
                    break;
                }
                // model crash, which means that the unique sequence of target model has been registered 
                // by another service before executing the insert-command.
                continue;
            }
            Long currVersion = sequence.getVersion();
            // try to update the sequence by CAS pattern.
            sequence = createSequence(key, sequence.getMaxThreshold() + step, step, currVersion);
            if (dbDataOperator.updateSequence(sequence) > 0) {
                break;
            }
        }
        return sequence;
    }

    private void processBeforeRegister(String model, SegmentModelProperties modelProperties, SegmentModeSequenceCache sequenceCache) {
        if (modelProperties.isEnableDynamicStep()) {
            processDynamicStep(model, modelProperties, sequenceCache);
        }
    }
    
    private void processDynamicStep(String model, SegmentModelProperties modelProperties, SegmentModeSequenceCache sequenceCache) {
        if (Objects.isNull(sequenceCache)) {
            DbDataOperator dbDataOperator = propertiesBean.getDbDataOperator();
            SegmentModeSequence sequence = dbDataOperator.selectSequence(model);
            if (Objects.nonNull(sequence)) {
                modelProperties.setStep(DynamicStepRule.execute(sequence.getStepSize(), null, 
                    modelProperties.getMinStep(), modelProperties.getMaxStep(), modelProperties.getTps()));
            }
        } else {
            modelProperties.setStep(DynamicStepRule.execute(sequenceCache.getStep(), sequenceCache.getRegisterTime(), 
                modelProperties.getMinStep(), modelProperties.getMaxStep(), modelProperties.getTps()));
        }
    }

    private SegmentModeSequence createSequence(String model, long maxThreshold, long step, long version) {
        return SegmentModeSequence.builder()
            .businessModel(model)
            .maxThreshold(maxThreshold)
            .stepSize(step)
            .version(version)
            .build();
    }
    
}
