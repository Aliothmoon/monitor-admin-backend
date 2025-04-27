package com.pg.monitor.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 考试管理实体类。
 *
 * @author PG
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_exam")
public class MonitorExam {

    @Id
    @Column("id")
    private Integer id;
    
    @Column("name")
    private String name;
    
    @Column("description")
    private String description;
    
    @Column("start_time")
    private LocalDateTime startTime;
    
    @Column("end_time")
    private LocalDateTime endTime;
    
    @Column("duration")
    private Integer duration;
    
    @Column("status")
    private Integer status;
    
    @Column("created_at")
    private LocalDateTime createdAt;
    
    @Column("updated_at")
    private LocalDateTime updatedAt;
    
    @Column("created_by")
    private Integer createdBy;
    
    @Column("updated_by")
    private Integer updatedBy;
} 