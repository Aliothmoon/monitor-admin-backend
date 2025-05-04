package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorExam;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考试管理服务接口。
 *
 * @author Aliothmoon
 *
 */
public interface MonitorExamService extends IService<MonitorExam> {

    /**
     * 根据状态查询考试列表
     *
     * @param status 状态
     * @return 考试列表
     */
    List<MonitorExam> listByStatus(Integer status);

    /**
     * 根据时间范围查询考试列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 考试列表
     */
    List<MonitorExam> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据关键词模糊查询考试列表
     *
     * @param keyword 关键词
     * @return 考试列表
     */
    List<MonitorExam> listByKeyword(String keyword);

    /**
     * 更新考试状态
     *
     * @param id 考试ID
     * @param status 状态
     * @return 更新结果
     */
    boolean updateStatus(Integer id, Integer status);

    /**
     * 自动更新考试状态
     * 根据当前时间和考试时间范围自动更新考试状态
     */
    void autoUpdateStatus();
}
