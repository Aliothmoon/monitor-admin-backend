package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorExamRiskImage;

import java.util.List;

/**
 * 考试-风险图片模板关联表服务接口
 *
 * @author Alioth
 *
 */
public interface MonitorExamRiskImageService extends IService<MonitorExamRiskImage> {

    /**
     * 根据考试ID查询关联的风险图片模板
     *
     * @param examId 考试ID
     * @return 风险图片模板关联列表
     */
    List<MonitorExamRiskImage> listByExamId(Integer examId);
    
    /**
     * 根据考试ID删除关联关系
     *
     * @param examId 考试ID
     * @return 是否成功
     */
    boolean removeByExamId(Integer examId);
} 