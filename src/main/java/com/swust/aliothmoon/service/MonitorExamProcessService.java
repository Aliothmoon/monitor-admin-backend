package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorExamProcess;

import java.util.List;

/**
 * 考试-可疑进程关联表服务接口
 *
 * @author Alioth
 *
 */
public interface MonitorExamProcessService extends IService<MonitorExamProcess> {

    /**
     * 根据考试ID查询关联的可疑进程
     *
     * @param examId 考试ID
     * @return 可疑进程关联列表
     */
    List<MonitorExamProcess> listByExamId(Integer examId);

    /**
     * 根据考试ID删除关联关系
     *
     * @param examId 考试ID
     * @return 是否成功
     */
    boolean removeByExamId(Integer examId);

    /**
     * 根据进程ID查询关联的考试数量
     *
     * @param processId 进程ID
     * @return 关联的考试数量
     */
    long countByProcessId(Integer processId);
} 