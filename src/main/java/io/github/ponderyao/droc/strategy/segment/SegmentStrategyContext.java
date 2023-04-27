package io.github.ponderyao.droc.strategy.segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.ponderyao.droc.bean.SegmentPropertiesBean;
import io.github.ponderyao.droc.common.constant.CacheConstant;
import io.github.ponderyao.droc.common.constant.CommonConstant;
import io.github.ponderyao.droc.config.segment.SegmentModelProperties;
import io.github.ponderyao.droc.config.segment.SegmentProperties;
import io.github.ponderyao.droc.exception.properties.BadCustomPropertiesException;
import io.github.ponderyao.droc.strategy.StrategyContext;

/**
 * SegmentStrategyContext：号段模式策略上下文
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class SegmentStrategyContext implements StrategyContext<SegmentProperties, SegmentPropertiesBean> {

    private static final List<SegmentStrategy> STRATEGIES = new ArrayList<>();
    
    static {
        STRATEGIES.add(new MysqlSegmentStrategy());
    }

    @Override
    public SegmentPropertiesBean transferProperties(SegmentProperties properties) {
        beforeProcess(properties);
        for (SegmentStrategy strategy : STRATEGIES) {
            if (strategy.match(properties.getSource())) {
                return strategy.execute(properties);
            }
        }
        return STRATEGIES.get(0).execute(properties);
    }
    
    private void beforeProcess(SegmentProperties properties) {
        Map<String, SegmentModelProperties> models = properties.getModels();
        for (Map.Entry<String, SegmentModelProperties> entry : models.entrySet()){
            String modelName = entry.getKey();
            SegmentModelProperties modelProperties = entry.getValue();
            if (modelProperties.getOffset() < CacheConstant.DEFAULT_OFFSET) {
                throw new BadCustomPropertiesException(SegmentProperties.PREFIX + ".offset (of " + modelName + ")", CommonConstant.NOT_LESS, CacheConstant.DEFAULT_OFFSET);
            }
            if (modelProperties.getStep() < CacheConstant.DEFAULT_STEP) {
                throw new BadCustomPropertiesException(SegmentProperties.PREFIX + ".step (of " + modelName + ")",
                        CommonConstant.NOT_LESS, CacheConstant.DEFAULT_STEP);
            }
        }
    }
    
}
