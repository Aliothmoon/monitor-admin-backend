package com.swust.aliothmoon.model.screenrecord;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 录屏查询数据传输对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class ScreenRecordQueryDTO {

    /**
     * 关键词（模糊查询）
     */
    private String keyword;

    /**
     * 风险等级 0-低风险 1-中风险 2-高风险
     */
    private Integer riskLevel;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;
} 