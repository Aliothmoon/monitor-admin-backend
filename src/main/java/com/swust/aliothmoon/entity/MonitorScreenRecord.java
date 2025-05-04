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
 * 录屏管理实体类。
 *
 * @author Aliothmoon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_screen_record")
public class MonitorScreenRecord {

    @Id
    @Column("id")
    private Integer id;

    @Column("exam_id")
    private Integer examId;

    @Column("exam_name")
    private String examName;

    @Column("student_id")
    private Integer studentId;

    @Column("student_name")
    private String studentName;

    @Column("start_time")
    private LocalDateTime startTime;

    @Column("end_time")
    private LocalDateTime endTime;

    @Column("duration")
    private Integer duration;

    @Column("video_url")
    private String videoUrl;

    @Column("file_size")
    private Integer fileSize;

    @Column("risk_level")
    private Integer riskLevel;

    @Column("remark")
    private String remark;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("created_by")
    private Integer createdBy;

    @Column("updated_by")
    private Integer updatedBy;
} 