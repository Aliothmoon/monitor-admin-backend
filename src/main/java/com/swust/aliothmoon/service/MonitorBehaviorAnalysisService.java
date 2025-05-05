package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorBehaviorAnalysis;
import com.swust.aliothmoon.model.vo.MonitorBehaviorAnalysisVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考生行为分析记录服务接口
 *
 * @author Aliothmoon
 */
public interface MonitorBehaviorAnalysisService extends IService<MonitorBehaviorAnalysis> {
    
    /**
     * 获取考生行为分析记录
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 行为分析记录列表
     */
    List<MonitorBehaviorAnalysisVO> getBehaviorAnalysisByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 获取考生最近的行为分析记录
     *
     * @param examineeAccountId 考生账号ID
     * @param examId            考试ID
     * @return 行为分析记录列表
     */
    List<MonitorBehaviorAnalysisVO> getRecentBehaviorAnalysisByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 添加行为分析记录
     *
     * @param behaviorAnalysis 行为分析记录
     * @return 是否成功
     */
    boolean addBehaviorAnalysis(MonitorBehaviorAnalysis behaviorAnalysis);
    
    /**
     * 更新行为分析记录处理状态
     *
     * @param id 记录ID
     * @param isProcessed 是否已处理
     * @param processResult 处理结果
     * @param processorId 处理人ID
     * @return 是否成功
     */
    boolean updateProcessStatus(Integer id, Boolean isProcessed, String processResult, Integer processorId);
    
    /**
     * 根据事件类型查询行为分析记录
     *
     * @param eventType 事件类型
     * @return 行为分析记录列表
     */
    List<MonitorBehaviorAnalysis> listByEventType(Integer eventType);
    
    /**
     * 根据风险等级查询行为分析记录
     *
     * @param level 风险等级
     * @return 行为分析记录列表
     */
    List<MonitorBehaviorAnalysis> listByLevel(String level);
    
    /**
     * 根据时间范围查询行为分析记录
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 行为分析记录列表
     */
    List<MonitorBehaviorAnalysis> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据考试ID查询行为分析记录
     *
     * @param examId 考试ID
     * @return 行为分析记录列表
     */
    List<MonitorBehaviorAnalysis> listByExamId(Integer examId);
    
    /**
     * 统计考生切屏次数
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 切屏次数
     */
    Integer countSwitchScreens(Integer examineeAccountId, Integer examId);
    
    /**
     * 保存考生行为分析记录
     *
     * @param examId 考试ID
     * @param examineeAccountId 考生账号ID
     * @param eventType 事件类型
     * @param content 内容描述
     * @param level 风险等级
     * @param eventTime 事件时间
     * @return 是否成功
     */
    boolean saveBehaviorAnalysis(Integer examId, Integer examineeAccountId, Integer eventType, String content, String level, LocalDateTime eventTime);
} 