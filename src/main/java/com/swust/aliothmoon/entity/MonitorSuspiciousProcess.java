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
 * 可疑进程名单实体类。
 *
 * @author Aliothmoon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_suspicious_process")
public class MonitorSuspiciousProcess {

    /**
     * 主键ID
     */
    @Id
    @Column("id")
    private Integer id;

    /**
     * 进程名称
     */
    @Column("process_name")
    private String processName;

    /**
     * 进程描述
     */
    @Column("description")
    private String description;

    /**
     * 风险等级 1-低 2-中 3-高
     */
    @Column("risk_level")
    private Integer riskLevel;

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
     * 创建者ID
     */
    @Column("created_by")
    private Integer createdBy;

    /**
     * 更新者ID
     */
    @Column("updated_by")
    private Integer updatedBy;
} 