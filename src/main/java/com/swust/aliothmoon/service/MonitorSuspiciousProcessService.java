package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorSuspiciousProcess;

import java.util.List;

/**
 * 可疑进程名单服务接口。
 *
 * @author Aliothmoon
 *
 */
public interface MonitorSuspiciousProcessService extends IService<MonitorSuspiciousProcess> {

    /**
     * 根据风险等级查询可疑进程名单
     *
     * @param riskLevel 风险等级
     * @return 进程列表
     */
    List<MonitorSuspiciousProcess> listByRiskLevel(Integer riskLevel);

    /**
     * 检查进程名是否已存在
     *
     * @param processName 进程名
     * @param id 排除的ID（用于更新操作时验证）
     * @return 是否存在
     */
    boolean checkProcessNameExists(String processName, Integer id);
} 