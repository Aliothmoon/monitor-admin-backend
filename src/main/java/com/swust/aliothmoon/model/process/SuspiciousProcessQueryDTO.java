package com.swust.aliothmoon.model.process;

import lombok.Data;

/**
 * 可疑进程名单查询数据传输对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class SuspiciousProcessQueryDTO {

    /**
     * 进程名称（模糊查询）
     */
    private String processName;

    /**
     * 风险等级 1-低 2-中 3-高
     */
    private Integer riskLevel;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;
} 