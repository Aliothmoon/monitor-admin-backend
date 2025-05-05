package com.swust.aliothmoon.model.vo;

import lombok.Data;

/**
 * 考试日程VO
 * 用于首页展示的考试日程列表
 *
 * @author Aliothmoon
 */
@Data
public class ExamScheduleVO {
    /**
     * 考试名称
     */
    private String name;
    
    /**
     * 考试状态
     * 可能的值: 进行中, 即将开始, 已结束, 已排期
     */
    private String status;
    
    /**
     * 考试时间范围
     * 格式: HH:mm-HH:mm
     */
    private String time;
    
    /**
     * 考试时长(分钟)
     */
    private Integer duration;
    
    /**
     * 监考人员
     */
    private String proctor;
    
    /**
     * 考生数量
     */
    private Integer candidateCount;
} 