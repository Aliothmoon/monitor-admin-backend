package com.swust.aliothmoon.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考生网站访问记录VO类
 */
@Data
public class MonitorWebsiteVisitVO {
    
    /**
     * 主键ID
     */
    private Integer id;
    
    /**
     * 访问URL
     */
    private String url;
    
    /**
     * 网站标题
     */
    private String title;
    
    /**
     * 访问时间(格式化字符串)
     */
    private String time;
    
    /**
     * 访问时间
     */
    private LocalDateTime visitTime;
    
    /**
     * 停留时长(秒)
     */
    private Integer duration;
    
    /**
     * 风险等级 normal-正常 warning-风险
     */
    private String risk;
    
    /**
     * 风险等级值
     */
    private Integer riskLevel;
    
    /**
     * 描述信息
     */
    private String description;
    
    /**
     * 是否黑名单网站
     */
    private Boolean isBlacklist;
} 