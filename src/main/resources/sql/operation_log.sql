CREATE TABLE IF NOT EXISTS `operation_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) NOT NULL COMMENT '操作类型',
  `method` varchar(100) DEFAULT NULL COMMENT '请求方法',
  `params` text DEFAULT NULL COMMENT '请求参数',
  `path` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `browser` varchar(100) DEFAULT NULL COMMENT '浏览器信息',
  `os` varchar(100) DEFAULT NULL COMMENT '操作系统',
  `status` int(4) DEFAULT NULL COMMENT '状态码',
  `error_msg` text DEFAULT NULL COMMENT '错误消息',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `duration` bigint(20) DEFAULT NULL COMMENT '耗时(毫秒)',
  PRIMARY KEY (`log_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_operation_time` (`operation_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表'; 