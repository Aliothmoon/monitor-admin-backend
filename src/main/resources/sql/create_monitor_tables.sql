-- 包含所有监控相关表的创建SQL

-- 截图表
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

-- 网站访问记录表
CREATE TABLE IF NOT EXISTS `monitor_website_visit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `examinee_account_id` int(11) NOT NULL COMMENT '考生账号ID',
  `student_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `url` varchar(500) NOT NULL COMMENT '访问URL',
  `title` varchar(255) DEFAULT NULL COMMENT '网站标题',
  `visit_time` datetime NOT NULL COMMENT '访问时间',
  `duration` int(11) DEFAULT NULL COMMENT '停留时长(秒)',
  `risk_level` int(11) NOT NULL DEFAULT '0' COMMENT '风险等级 0-低风险 1-中风险 2-高风险',
  `description` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `is_blacklist` tinyint(1) DEFAULT '0' COMMENT '是否黑名单网站 0-否 1-是',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_exam_id` (`exam_id`),
  KEY `idx_examinee_account_id` (`examinee_account_id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_visit_time` (`visit_time`),
  KEY `idx_risk_level` (`risk_level`),
  KEY `idx_is_blacklist` (`is_blacklist`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考生网站访问记录表';

-- 进程记录表
CREATE TABLE IF NOT EXISTS `monitor_process_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `examinee_account_id` int(11) NOT NULL COMMENT '考生账号ID',
  `student_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `process_name` varchar(255) NOT NULL COMMENT '进程名称',
  `process_path` varchar(500) DEFAULT NULL COMMENT '进程路径',
  `start_time` datetime NOT NULL COMMENT '启动时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `risk_level` int(11) NOT NULL DEFAULT '0' COMMENT '风险等级 0-低风险 1-中风险 2-高风险',
  `description` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_exam_id` (`exam_id`),
  KEY `idx_examinee_account_id` (`examinee_account_id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_risk_level` (`risk_level`),
  KEY `idx_process_name` (`process_name`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考生进程记录表';

-- 行为分析记录表
CREATE TABLE IF NOT EXISTS `monitor_behavior_analysis` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `exam_id` int(11) NOT NULL COMMENT '考试ID',
  `examinee_account_id` int(11) NOT NULL COMMENT '考生账号ID',
  `student_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `event_type` int(11) NOT NULL COMMENT '行为事件类型: 1-切屏, 2-切换程序, 3-异常操作, 4-违规软件, 5-截图异常, 6-网站异常, 7-其他',
  `content` varchar(500) NOT NULL COMMENT '行为内容',
  `event_time` datetime NOT NULL COMMENT '发生时间',
  `level` varchar(20) NOT NULL DEFAULT 'info' COMMENT '风险等级: info-信息, warning-警告, danger-严重, success-成功',
  `screenshot_id` int(11) DEFAULT NULL COMMENT '相关截图ID',
  `related_id` int(11) DEFAULT NULL COMMENT '相关数据ID',
  `related_type` varchar(50) DEFAULT NULL COMMENT '相关数据类型',
  `is_processed` tinyint(1) DEFAULT '0' COMMENT '是否已处理 0-否 1-是',
  `process_result` varchar(500) DEFAULT NULL COMMENT '处理结果',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `processor_id` int(11) DEFAULT NULL COMMENT '处理人ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_exam_id` (`exam_id`),
  KEY `idx_examinee_account_id` (`examinee_account_id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_event_time` (`event_time`),
  KEY `idx_event_type` (`event_type`),
  KEY `idx_level` (`level`),
  KEY `idx_is_processed` (`is_processed`),
  KEY `idx_screenshot_id` (`screenshot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考生行为分析记录表';