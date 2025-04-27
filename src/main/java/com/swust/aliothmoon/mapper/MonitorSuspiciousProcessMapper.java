package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.MonitorSuspiciousProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 可疑进程名单 Mapper 接口。
 *
 * @author Alioth
 *
 */
@Mapper
public interface MonitorSuspiciousProcessMapper extends BaseMapper<MonitorSuspiciousProcess> {

    /**
     * 根据风险等级查询可疑进程名单
     *
     * @param riskLevel 风险等级
     * @return 进程列表
     */
    List<MonitorSuspiciousProcess> listByRiskLevel(@Param("riskLevel") Integer riskLevel);

    /**
     * 检查进程名是否已存在
     *
     * @param processName 进程名
     * @param id 排除的ID（用于更新操作时验证）
     * @return 计数
     */
    Integer checkProcessNameExists(@Param("processName") String processName, @Param("id") Integer id);
} 