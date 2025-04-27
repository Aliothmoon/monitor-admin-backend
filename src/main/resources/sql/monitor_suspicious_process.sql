CREATE TABLE IF NOT EXISTS `monitor_suspicious_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `process_name` varchar(255) NOT NULL COMMENT '进程名称',
  `description` varchar(500) DEFAULT NULL COMMENT '进程描述',
  `risk_level` int(11) NOT NULL DEFAULT '1' COMMENT '风险等级 1-低 2-中 3-高',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` int(11) DEFAULT NULL COMMENT '创建者ID',
  `updated_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  PRIMARY KEY (`id`),
  KEY `idx_process_name` (`process_name`),
  KEY `idx_risk_level` (`risk_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='可疑进程名单表'; 