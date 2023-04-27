package io.github.ponderyao.droc.strategy.segment;

import io.github.ponderyao.droc.bean.SegmentPropertiesBean;
import io.github.ponderyao.droc.config.segment.SegmentProperties;
import io.github.ponderyao.droc.strategy.Strategy;

/**
 * SegmentStrategy：号段模式策略接口
 *
 * @author PonderYao
 * @since 1.3.0
 */
public interface SegmentStrategy extends Strategy {
    
    SegmentPropertiesBean execute(SegmentProperties properties);
    
}
