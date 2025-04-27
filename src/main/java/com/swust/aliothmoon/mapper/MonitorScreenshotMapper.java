package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.MonitorScreenshot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 截图管理 Mapper 接口。
 *
 * @author Alioth
 *
 */
@Mapper
public interface MonitorScreenshotMapper extends BaseMapper<MonitorScreenshot> {

    /**
     * 根据风险等级查询截图列表
     *
     * @param riskLevel 风险等级
     * @return 截图列表
     */
    List<MonitorScreenshot> listByRiskLevel(@Param("riskLevel") Integer riskLevel);

    /**
     * 根据时间范围查询截图列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 截图列表
     */
    List<MonitorScreenshot> listByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 根据考试ID查询截图列表
     *
     * @param examId 考试ID
     * @return 截图列表
     */
    List<MonitorScreenshot> listByExamId(@Param("examId") Integer examId);

    /**
     * 根据学生ID查询截图列表
     *
     * @param studentId 学生ID
     * @return 截图列表
     */
    List<MonitorScreenshot> listByStudentId(@Param("studentId") Integer studentId);
} 