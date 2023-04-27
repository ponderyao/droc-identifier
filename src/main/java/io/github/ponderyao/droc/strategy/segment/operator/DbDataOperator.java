package io.github.ponderyao.droc.strategy.segment.operator;

import io.github.ponderyao.droc.data.entity.SegmentModeSequence;

/**
 * DbDataOperator：数据库数据操作接口
 *
 * @author PonderYao
 * @since 1.3.0
 */
public interface DbDataOperator {

    /**
     * 新增号段序列
     * 
     * @param entity 号段模式序列
     * @return 新增行数
     */
    int insertSequence(SegmentModeSequence entity);

    /**
     * 更新号段序列
     * 
     * @param entity 号段模式序列
     * @return 更新行数
     */
    int updateSequence(SegmentModeSequence entity);

    /**
     * 号段序列查询
     * 
     * @param model 数据模型
     * @return 号段序列
     */
    SegmentModeSequence selectSequence(String model);
    
}
