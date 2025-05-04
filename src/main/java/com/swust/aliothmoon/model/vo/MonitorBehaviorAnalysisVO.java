package com.swust.aliothmoon.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考生行为分析记录VO类
 */
@Data
public class MonitorBehaviorAnalysisVO {
    
    /**
     * 主键ID
     */
    private Integer id;
    
    /**
     * 行为事件类型
     */
    private Integer eventType;
    
    /**
     * 行为内容
     */
    private String content;
    
    /**
     * 发生时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 发生时间(格式化字符串)
     */
    private String time;
    
    /**
     * 风险等级 info, warning, danger, success
     */
    private String level;
    
    /**
     * 相关截图ID
     */
    private Integer screenshotId;
    
    /**
     * 相关数据ID
     */
    private Integer relatedId;
    
    /**
     * 相关数据类型
     */
    private String relatedType;
    
    /**
     * 是否已处理
     */
    private Boolean isProcessed;
    
    /**
     * 处理结果
     */
    private String processResult;
    
    /**
     * 处理时间
     */
    private LocalDateTime processTime;
    
    /**
     * 处理人ID
     */
    private Integer processorId;
} 