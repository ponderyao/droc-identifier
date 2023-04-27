package io.github.ponderyao.droc.strategy.segment;

import io.github.ponderyao.droc.bean.SegmentPropertiesBean;
import io.github.ponderyao.droc.common.constant.DRocConstant;
import io.github.ponderyao.droc.config.segment.SegmentProperties;
import io.github.ponderyao.droc.strategy.segment.operator.MysqlDbDataOperator;

/**
 * MysqlSegmentStrategy：MySQL号段模式策略实现
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class MysqlSegmentStrategy implements SegmentStrategy {
    
    @Override
    public boolean match(String source) {
        return DRocConstant.SEGMENT_SOURCE.MYSQL.equals(source);
    }

    @Override
    public SegmentPropertiesBean execute(SegmentProperties properties) {
        SegmentPropertiesBean propertiesBean = new SegmentPropertiesBean(properties);
        propertiesBean.setDbDataOperator(new MysqlDbDataOperator());
        return propertiesBean;
    }
}
