-- --------------------------------------
-- segment-mode sequence
-- --------------------------------------
CREATE TABLE SEGMENT_MODE_SEQUENCE (
    `seq_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'sequence-id',
    `business_model` VARCHAR(255) NOT NULL COMMENT 'business-data-model',
    `max_threshold` BIGINT(20) NOT NULL COMMENT 'maximum-threshold',
    `step_size` BIGINT(20) NOT NULL COMMENT 'incremental-step-size',
    `version` BIGINT(20) NOT NULL COMMENT 'version',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create-timestamp',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'modify-timestamp',
    PRIMARY KEY (`seq_id`),
    UNIQUE KEY `UK_SEGMENT_MODE_SEQUENCE_BUSINESS_MODEL` (`business_model`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='segment-mode-sequence';