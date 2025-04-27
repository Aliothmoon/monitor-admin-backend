CREATE TABLE IF NOT EXISTS `monitor_user_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `college` varchar(50) DEFAULT NULL COMMENT '所在学院',
  `department` varchar(50) DEFAULT NULL COMMENT '所在系/部门',
  `title` varchar(50) DEFAULT NULL COMMENT '职称/职位',
  `employee_id` varchar(50) DEFAULT NULL COMMENT '工号',
  `profile` varchar(500) DEFAULT NULL COMMENT '个人简介',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` int(11) DEFAULT NULL COMMENT '创建者ID',
  `updated_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户个人信息表'; 