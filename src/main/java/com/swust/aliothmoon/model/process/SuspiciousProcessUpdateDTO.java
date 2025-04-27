package com.swust.aliothmoon.model.process;

import lombok.Data;

/**
 * 可疑进程名单更新数据传输对象
 *
 * @author Alioth
 * @since 2023-06-11
 */
@Data
public class SuspiciousProcessUpdateDTO {
    
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
} 