package com.swust.aliothmoon.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考生进程记录VO类
 */
@Data
public class MonitorProcessRecordVO {
    
    /**
     * 主键ID
     */
    private Integer id;
    
    /**
     * 进程名称
     */
    private String name;
    
    /**
     * 进程ID
     */
    private Integer pid;
    
    /**
     * 启动时间
     */
    private LocalDateTime startTime;
    
    /**
     * 启动时间(格式化字符串)
     */
    private String startTimeStr;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 结束时间(格式化字符串)
     */
    private String endTimeStr;
    
    /**
     * 运行状态 normal-正常 warning-风险
     */
    private String status;
    
    /**
     * 风险等级值
     */
    private Integer riskLevel;
    
    /**
     * 描述信息
     */
    private String description;
    
    /**
     * 内存占用(MB)
     */
    private Double memoryUsage;
    
    /**
     * CPU占用(%)
     */
    private Double cpuUsage;
    
    /**
     * 是否黑名单进程
     */
    private Boolean isBlacklist;
} 