-- 考试-可疑进程关联表
CREATE TABLE IF NOT EXISTS `monitor_exam_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `process_id` int(11) NOT NULL COMMENT '可疑进程ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_exam_process` (`exam_id`, `process_id`),
  KEY `idx_exam_id` (`exam_id`),
  KEY `idx_process_id` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试-可疑进程关联表';

-- 考试-域名黑名单关联表
CREATE TABLE IF NOT EXISTS `monitor_exam_domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `domain_id` int(11) NOT NULL COMMENT '域名黑名单ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_exam_domain` (`exam_id`, `domain_id`),
  KEY `idx_exam_id` (`exam_id`),
  KEY `idx_domain_id` (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试-域名黑名单关联表';

-- 考试-风险图片模板关联表
CREATE TABLE IF NOT EXISTS `monitor_exam_risk_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `risk_image_id` int(11) NOT NULL COMMENT '风险图片模板ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_exam_risk_image` (`exam_id`, `risk_image_id`),
  KEY `idx_exam_id` (`exam_id`),
  KEY `idx_risk_image_id` (`risk_image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试-风险图片模板关联表'; 