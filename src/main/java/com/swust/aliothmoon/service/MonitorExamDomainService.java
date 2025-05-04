package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorExamDomain;

import java.util.List;

/**
 * 考试-域名黑名单关联表服务接口
 *
 * @author Alioth
 *
 */
public interface MonitorExamDomainService extends IService<MonitorExamDomain> {

    /**
     * 根据考试ID查询关联的域名黑名单
     *
     * @param examId 考试ID
     * @return 域名黑名单关联列表
     */
    List<MonitorExamDomain> listByExamId(Integer examId);
    
    /**
     * 根据考试ID删除关联关系
     *
     * @param examId 考试ID
     * @return 是否成功
     */
    boolean removeByExamId(Integer examId);
} 