package com.swust.aliothmoon.model.exam;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考试视图对象
 *
 * @author Alioth
 *
 */
@Data
public class ExamVO {

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
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 考试时长(分钟)
     */
    private Integer duration;

    /**
     * 考试状态 0-未开始 1-进行中 2-已结束
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 可疑进程ID列表
     */
    private List<Integer> suspiciousProcessIds;
    
    /**
     * 域名黑名单ID列表
     */
    private List<Integer> blacklistDomainIds;
    
    /**
     * 风险图片模板ID列表
     */
    private List<Integer> riskImageIds;
} 