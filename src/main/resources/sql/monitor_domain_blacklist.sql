CREATE TABLE IF NOT EXISTS `monitor_domain_blacklist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `domain` varchar(255) NOT NULL COMMENT '域名',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category` varchar(50) NOT NULL COMMENT '分类',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` int(11) DEFAULT NULL COMMENT '创建者ID',
  `updated_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  PRIMARY KEY (`id`),
  KEY `idx_domain` (`domain`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='域名黑名单表'; 