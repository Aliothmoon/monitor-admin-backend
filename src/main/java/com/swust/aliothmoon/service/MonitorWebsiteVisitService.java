package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorWebsiteVisit;
import com.swust.aliothmoon.model.vo.MonitorWebsiteVisitVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考生网站访问记录服务接口
 *
 * @author Aliothmoon
 */
public interface MonitorWebsiteVisitService extends IService<MonitorWebsiteVisit> {
    
    /**
     * 获取考生网站访问记录
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 网站访问记录列表
     */
    List<MonitorWebsiteVisitVO> getWebsiteVisitsByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 获取考生最近的网站访问记录
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @param limit 限制数量
     * @return 网站访问记录列表
     */
    List<MonitorWebsiteVisitVO> getRecentWebsiteVisitsByExaminee(Integer examineeAccountId, Integer examId, Integer limit);
    
    /**
     * 添加网站访问记录
     *
     * @param websiteVisit 网站访问记录
     * @return 是否成功
     */
    boolean addWebsiteVisit(MonitorWebsiteVisit websiteVisit);
    
    /**
     * 根据风险等级查询网站访问记录
     *
     * @param riskLevel 风险等级
     * @return 网站访问记录列表
     */
    List<MonitorWebsiteVisit> listByRiskLevel(Integer riskLevel);
    
    /**
     * 根据时间范围查询网站访问记录
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 网站访问记录列表
     */
    List<MonitorWebsiteVisit> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据考试ID查询网站访问记录
     *
     * @param examId 考试ID
     * @return 网站访问记录列表
     */
    List<MonitorWebsiteVisit> listByExamId(Integer examId);
    
    /**
     * 根据URL关键字查询网站访问记录
     *
     * @param keyword URL关键字
     * @return 网站访问记录列表
     */
    List<MonitorWebsiteVisit> listByUrlKeyword(String keyword);
    
    /**
     * 统计考生访问网站总数
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 访问网站总数
     */
    Integer countWebsiteVisits(Integer examineeAccountId, Integer examId);
} 