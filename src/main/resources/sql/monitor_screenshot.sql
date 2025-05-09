CREATE TABLE IF NOT EXISTS `monitor_screenshot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `exam_name` varchar(255) NOT NULL COMMENT '考试名称',
  `student_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `examinee_account_id` int(11) DEFAULT NULL COMMENT '考生账号ID',
  `student_name` varchar(100) DEFAULT NULL COMMENT '学生姓名',
  `capture_time` datetime NOT NULL COMMENT '截图时间',
  `image_url` varchar(500) DEFAULT NULL COMMENT '图片URL（旧版兼容）',
  `screenshot_url` varchar(500) DEFAULT NULL COMMENT '截图URL',
  `screenshot_data` text DEFAULT NULL COMMENT '截图Base64数据',
  `file_size` int(11) DEFAULT NULL COMMENT '文件大小(KB)',
  `risk_level` int(11) NOT NULL DEFAULT '0' COMMENT '风险等级 0-低风险 1-中风险 2-高风险',
  `has_warning` tinyint(1) DEFAULT '0' COMMENT '是否有异常：0-正常 1-异常',
  `analysis_result` text DEFAULT NULL COMMENT '分析结果',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `created_by` int(11) DEFAULT NULL COMMENT '创建者ID',
  `updated_by` int(11) DEFAULT NULL COMMENT '更新者ID',
  PRIMARY KEY (`id`),
  KEY `idx_exam_id` (`exam_id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_examinee_account_id` (`examinee_account_id`),
  KEY `idx_capture_time` (`capture_time`),
  KEY `idx_risk_level` (`risk_level`),
  KEY `idx_has_warning` (`has_warning`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考生屏幕截图表'; 