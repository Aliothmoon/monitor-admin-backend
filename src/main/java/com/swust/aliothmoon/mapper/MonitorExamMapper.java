package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.MonitorExam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考试管理 Mapper 接口。
 *
 * @author Aliothmoon
 *
 */
@Mapper
public interface MonitorExamMapper extends BaseMapper<MonitorExam> {

    /**
     * 根据状态查询考试列表
     *
     * @param status 状态
     * @return 考试列表
     */
    List<MonitorExam> listByStatus(@Param("status") Integer status);

    /**
     * 根据时间范围查询考试列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 考试列表
     */
    List<MonitorExam> listByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 根据关键词模糊查询考试列表
     *
     * @param keyword 关键词
     * @return 考试列表
     */
    List<MonitorExam> listByKeyword(@Param("keyword") String keyword);

    /**
     * 更新考试状态
     *
     * @param id 考试ID
     * @param status 状态
     * @return 更新结果
     */
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
