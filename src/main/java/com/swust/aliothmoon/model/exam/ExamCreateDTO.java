package com.swust.aliothmoon.model.exam;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考试创建数据传输对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class ExamCreateDTO {

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
     * 考试地点
     */
    private String location;

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