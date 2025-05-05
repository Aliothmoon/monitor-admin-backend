package com.swust.aliothmoon.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考试详情VO
 * 用于考生登录后获取考试信息
 *
 * @author Aliothmoon
 */
@Data
public class ExamDetailsVO {
    /**
     * 考试ID
     */
    private Integer id;
    
    /**
     * 考试名称
     */
    private String name;
    
    /**
     * 考试描述
     */
    private String description;
    
    /**
     * 考试开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 考试结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 考试时长(分钟)
     */
    private Integer duration;
    
    /**
     * 考试地点
     */
    private String location;
    
    /**
     * 考试状态 0-未开始 1-进行中 2-已结束
     */
    private Integer status;
    
    /**
     * 当前服务器时间
     */
    private LocalDateTime serverTime;
    
    /**
     * 考试剩余时间(秒)
     */
    private Long remainingTime;
} 