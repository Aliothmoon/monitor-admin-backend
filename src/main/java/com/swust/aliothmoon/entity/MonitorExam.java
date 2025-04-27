package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author Alioth
 * @since 2025-04-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_exam")
public class MonitorExam {



    @Id
    @Column("exam_id")
    private Integer examId;

    @Column("start_time")
    private LocalDateTime startTime;

    @Column("end_time")
    private LocalDateTime endTime;

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
