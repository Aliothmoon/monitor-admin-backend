package com.swust.aliothmoon.model.screenrecord;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 录屏视图对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class ScreenRecordVO {

    /**
     * 录屏ID
     */
    private Integer id;

    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 时长(秒)
     */
    private Integer duration;

    /**
     * 视频URL
     */
    private String videoUrl;

    /**
     * 文件大小(KB)
     */
    private Integer fileSize;

    /**
     * 风险等级 0-低风险 1-中风险 2-高风险
     */
    private Integer riskLevel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 