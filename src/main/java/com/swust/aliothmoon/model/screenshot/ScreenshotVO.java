package com.swust.aliothmoon.model.screenshot;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 截图视图对象
 *
 * @author Alioth
 * @since 2025-04-28
 */
@Data
public class ScreenshotVO {
    
    /**
     * 截图ID
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
     * 截图时间
     */
    private LocalDateTime captureTime;
    
    /**
     * 图片URL
     */
    private String imageUrl;
    
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