package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorScreenshot;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 截图管理服务接口。
 *
 * @author Alioth
 * @since 2025-04-28
 */
public interface MonitorScreenshotService extends IService<MonitorScreenshot> {
    
    /**
     * 根据风险等级查询截图列表
     *
     * @param riskLevel 风险等级
     * @return 截图列表
     */
    List<MonitorScreenshot> listByRiskLevel(Integer riskLevel);
    
    /**
     * 根据时间范围查询截图列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 截图列表
     */
    List<MonitorScreenshot> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据考试ID查询截图列表
     *
     * @param examId 考试ID
     * @return 截图列表
     */
    List<MonitorScreenshot> listByExamId(Integer examId);
    
    /**
     * 根据学生ID查询截图列表
     *
     * @param studentId 学生ID
     * @return 截图列表
     */
    List<MonitorScreenshot> listByStudentId(Integer studentId);
} 