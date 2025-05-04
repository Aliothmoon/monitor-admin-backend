package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.MonitorScreenRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 录屏管理 Mapper 接口。
 *
 * @author Aliothmoon
 *
 */
@Mapper
public interface MonitorScreenRecordMapper extends BaseMapper<MonitorScreenRecord> {

    /**
     * 根据风险等级查询录屏列表
     *
     * @param riskLevel 风险等级
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByRiskLevel(@Param("riskLevel") Integer riskLevel);

    /**
     * 根据时间范围查询录屏列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 根据考试ID查询录屏列表
     *
     * @param examId 考试ID
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByExamId(@Param("examId") Integer examId);

    /**
     * 根据学生ID查询录屏列表
     *
     * @param studentId 学生ID
     * @return 录屏列表
     */
    List<MonitorScreenRecord> listByStudentId(@Param("studentId") Integer studentId);
} 