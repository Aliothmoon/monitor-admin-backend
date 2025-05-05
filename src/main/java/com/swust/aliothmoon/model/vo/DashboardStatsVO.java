package com.swust.aliothmoon.model.vo;

import lombok.Data;

/**
 * 仪表板统计数据VO
 * 用于首页展示的统计数据
 *
 * @author Aliothmoon
 */
@Data
public class DashboardStatsVO {
    /**
     * 当前活跃考试数量
     */
    private Integer activeExams;
    
    /**
     * 今日考试总数
     */
    private Integer totalExams;
    
    /**
     * 异常事件总数
     */
    private int anomalies;
    
    /**
     * 待处理异常事件数
     */
    private int pendingAnomalies;
    
    /**
     * 在线监考员数量
     */
    private Integer onlineProctors;
    
    /**
     * 监考员总数
     */
    private Integer totalProctors;
    
    /**
     * 在线考生数量
     */
    private Integer onlineCandidates;
} 