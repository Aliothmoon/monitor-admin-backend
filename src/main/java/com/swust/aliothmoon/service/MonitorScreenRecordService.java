package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorScreenRecord;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 录屏管理服务接口。
 *
 * @author Alioth
 *
 */
public interface MonitorScreenRecordService extends IService<MonitorScreenRecord> {

    /**
     * 根据风险等级查询录屏列表
     *
     * @param riskLevel 风险等级
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByRiskLevel(Integer riskLevel);

    /**
     * 根据时间范围查询录屏列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据考试ID查询录屏列表
     *
     * @param examId 考试ID
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByExamId(Integer examId);

    /**
     * 根据学生ID查询录屏列表
     *
     * @param studentId 学生ID
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByStudentId(Integer studentId);
} 