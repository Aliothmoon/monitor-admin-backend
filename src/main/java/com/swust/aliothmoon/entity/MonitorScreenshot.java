package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 截图管理实体类。
 *
 * @author Aliothmoon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_screenshot")
public class MonitorScreenshot {

    @Id
    @Column("id")
    private Integer id;

    /**
     * 考试ID
     */
    @Column("exam_id")
    private Integer examId;

    /**
     * 考试名称
     */
    @Column("exam_name")
    private String examName;

    /**
     * 学生ID
     */
    @Column("student_id")
    private Integer studentId;

    /**
     * 考生账号ID
     */
    @Column("examinee_account_id")
    private Integer examineeAccountId;

    /**
     * 学生姓名
     */
    @Column("student_name")
    private String studentName;

    /**
     * 截图时间
     */
    @Column("capture_time")
    private LocalDateTime captureTime;

    /**
     * 图片URL
     */
    @Column("image_url")
    private String imageUrl;

    /**
     * 截图URL
     */
    @Column("screenshot_url")
    private String screenshotUrl;

    /**
     * 截图Base64数据
     */
    @Column("screenshot_data")
    private String screenshotData;

    /**
     * 文件大小
     */
    @Column("file_size")
    private Integer fileSize;

    /**
     * 风险等级
     */
    @Column("risk_level")
    private Integer riskLevel;

    /**
     * 是否存在警告
     */
    @Column("has_warning")
    private Boolean hasWarning;

    /**
     * 分析结果
     */
    @Column("analysis_result")
    private String analysisResult;

    /**
     * 备注
     */
    @Column("remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 创建人
     */
    @Column("created_by")
    private Integer createdBy;

    /**
     * 更新人
     */
    @Column("updated_by")
    private Integer updatedBy;
} 