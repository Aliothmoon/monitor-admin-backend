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
 * 考试-可疑进程关联表实体类
 *
 * @author Alioth
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_exam_process")
public class MonitorExamProcess {

    @Id
    @Column("id")
    private Integer id;

    @Column("exam_id")
    private Integer examId;

    @Column("process_id")
    private Integer processId;

    @Column("created_at")
    private LocalDateTime createdAt;
} 