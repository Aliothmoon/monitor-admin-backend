CREATE TABLE IF NOT EXISTS `examinee_account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考生账号ID',
  `examinee_info_id` int(11) NOT NULL COMMENT '考生信息ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态（0-禁用, 1-启用）',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` int(11) DEFAULT NULL COMMENT '创建者ID',
  `updated_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `uk_account_exam` (`account`, `exam_id`),
  KEY `idx_examinee_info_id` (`examinee_info_id`),
  KEY `idx_exam_id` (`exam_id`),
  CONSTRAINT `fk_examinee_account_info` FOREIGN KEY (`examinee_info_id`) REFERENCES `examinee_info` (`examinee_info_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考生账号表'; 