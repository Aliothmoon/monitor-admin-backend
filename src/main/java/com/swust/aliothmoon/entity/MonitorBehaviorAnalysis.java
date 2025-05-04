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
 * 考生行为分析记录实体类
 *
 * @author Aliothmoon
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_behavior_analysis")
public class MonitorBehaviorAnalysis {

    /**
     * 主键ID
     */
    @Id
    @Column("id")
    private Integer id;

    /**
     * 考试ID
     */
    @Column("exam_id")
    private Integer examId;

    /**
     * 考生账号ID
     */
    @Column("examinee_account_id")
    private Integer examineeAccountId;

    /**
     * 学生ID
     */
    @Column("student_id")
    private Integer studentId;

    /**
     * 行为事件类型
     * 1-切屏, 2-切换程序, 3-异常操作, 4-违规软件, 5-截图异常, 6-网站异常, 7-其他
     */
    @Column("event_type")
    private Integer eventType;

    /**
     * 行为内容
     */
    @Column("content")
    private String content;

    /**
     * 发生时间
     */
    @Column("event_time")
    private LocalDateTime eventTime;

    /**
     * 风险等级
     * info-信息, warning-警告, danger-严重, success-成功
     */
    @Column("level")
    private String level;

    /**
     * 相关截图ID
     */
    @Column("screenshot_id")
    private Integer screenshotId;

    /**
     * 相关数据ID
     */
    @Column("related_id")
    private Integer relatedId;

    /**
     * 相关数据类型
     */
    @Column("related_type")
    private String relatedType;

    /**
     * 是否已处理
     */
    @Column("is_processed")
    private Boolean isProcessed;

    /**
     * 处理结果
     */
    @Column("process_result")
    private String processResult;

    /**
     * 处理时间
     */
    @Column("process_time")
    private LocalDateTime processTime;

    /**
     * 处理人ID
     */
    @Column("processor_id")
    private Integer processorId;

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
} 