CREATE TABLE IF NOT EXISTS `monitor_risk_image_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL COMMENT '模板名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `category` varchar(50) NOT NULL COMMENT '分类（公式、答案、小抄、参考表等）',
  `image_url` varchar(1000) NOT NULL COMMENT '图片URL',
  `similarity` int(11) NOT NULL DEFAULT 80 COMMENT '相似度阈值（1-100）',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` int(11) DEFAULT NULL COMMENT '创建者ID',
  `updated_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='风险图片模板表'; 