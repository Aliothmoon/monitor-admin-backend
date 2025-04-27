package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 截图管理实体类。
 *
 * @author Alioth
 * @since 2025-04-28
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
    
    @Column("exam_id")
    private Integer examId;
    
    @Column("exam_name")
    private String examName;
    
    @Column("student_id")
    private Integer studentId;
    
    @Column("student_name")
    private String studentName;
    
    @Column("capture_time")
    private LocalDateTime captureTime;
    
    @Column("image_url")
    private String imageUrl;
    
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