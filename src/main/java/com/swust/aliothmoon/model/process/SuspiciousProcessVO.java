package com.swust.aliothmoon.model.process;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 可疑进程名单视图对象
 *
 * @author Alioth
 * @since 2023-06-11
 */
@Data
public class SuspiciousProcessVO {
    
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 进程名称
     */
    private String processName;
    
    /**
     * 进程描述
     */
    private String description;
    
    /**
     * 风险等级 1-低 2-中 3-高
     */
    private Integer riskLevel;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 