package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
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
 * @since 2025-03-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_exam")
public class MonitorExam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer examId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
