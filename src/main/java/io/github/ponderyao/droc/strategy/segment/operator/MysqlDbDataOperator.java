package io.github.ponderyao.droc.strategy.segment.operator;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import io.github.ponderyao.droc.data.entity.SegmentModeSequence;
import io.github.ponderyao.droc.util.JdbcUtils;

/**
 * MysqlDbDataOperator：Mysql数据库数据操作类
 *
 * @author PonderYao
 * @since 1.3.0
 */
public class MysqlDbDataOperator implements DbDataOperator {

    public static final Logger log = LoggerFactory.getLogger(MysqlDbDataOperator.class);

    @Override
    public int insertSequence(SegmentModeSequence entity) {
        String sql = "INSERT INTO segment_mode_sequence(business_model, max_threshold, step_size, version, create_time, update_time) VALUES(?, ?, ?, ?, ?, ?)";
        Date currDateTime = new Date();
        try {
            return JdbcUtils.update(sql, entity.getBusinessModel(), entity.getMaxThreshold(), entity.getStepSize(), entity.getVersion(),
                currDateTime, currDateTime);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public int updateSequence(SegmentModeSequence entity) {
        String sql = "UPDATE segment_mode_sequence SET max_threshold = ?, step_size = ?, version = ?, update_time = ? WHERE business_model = ? AND version = ?";
        return JdbcUtils.update(sql, entity.getMaxThreshold(), entity.getStepSize(), entity.getVersion() + 1, new Date(), entity.getBusinessModel(), entity.getVersion());
    }
    
    public SegmentModeSequence selectSequence(String model) {
        String sql = "SELECT seq_id, business_model, max_threshold, step_size, version, create_time, update_time FROM segment_mode_sequence WHERE business_model = ? LIMIT 1";
        try {
            return JdbcUtils.select(sql, SegmentModeSequence.class, model);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
}
