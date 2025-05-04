package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考生后台进程记录实体类
 *
 * @author Aliothmoon
 */
@Data
@Table("monitor_process_record")
public class MonitorProcessRecord {
    
    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;
    
    /**
     * 考生账号ID
     */
    private Integer examineeAccountId;
    
    /**
     * 考试ID
     */
    private Integer examId;
    
    /**
     * 进程名称
     */
    private String processName;
    
    /**
     * 进程路径
     */
    private String processPath;
    
    /**
     * 启动时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 风险等级：0-正常 1-可疑 2-违规
     */
    private Integer riskLevel;
    
    /**
     * 描述信息
     */
    private String description;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 