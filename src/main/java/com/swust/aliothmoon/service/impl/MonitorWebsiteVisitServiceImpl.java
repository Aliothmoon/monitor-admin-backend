package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorWebsiteVisit;
import com.swust.aliothmoon.mapper.MonitorWebsiteVisitMapper;
import com.swust.aliothmoon.model.vo.MonitorWebsiteVisitVO;
import com.swust.aliothmoon.service.MonitorWebsiteVisitService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorWebsiteVisitTableDef.MONITOR_WEBSITE_VISIT;

/**
 * 考生网站访问记录服务实现类
 *
 * @author Aliothmoon
 */
@Service
public class MonitorWebsiteVisitServiceImpl extends ServiceImpl<MonitorWebsiteVisitMapper, MonitorWebsiteVisit> implements MonitorWebsiteVisitService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public List<MonitorWebsiteVisitVO> getWebsiteVisitsByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());
        
        List<MonitorWebsiteVisit> websiteVisits = list(queryWrapper);
        return websiteVisits.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorWebsiteVisitVO> getRecentWebsiteVisitsByExaminee(Integer examineeAccountId, Integer examId, Integer limit) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc())
                .limit(limit);
        
        List<MonitorWebsiteVisit> websiteVisits = list(queryWrapper);
        return websiteVisits.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public boolean addWebsiteVisit(MonitorWebsiteVisit websiteVisit) {
        websiteVisit.setCreatedAt(LocalDateTime.now());
        return save(websiteVisit);
    }

    @Override
    public List<MonitorWebsiteVisit> listByRiskLevel(Integer riskLevel) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.RISK_LEVEL.eq(riskLevel))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorWebsiteVisit> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.VISIT_TIME.ge(startTime))
                .and(MONITOR_WEBSITE_VISIT.VISIT_TIME.le(endTime))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorWebsiteVisit> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorWebsiteVisit> listByUrlKeyword(String keyword) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.URL.like("%" + keyword + "%"))
                .or(MONITOR_WEBSITE_VISIT.TITLE.like("%" + keyword + "%"))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public Integer countWebsiteVisits(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId));
        
        return Math.toIntExact(count(queryWrapper));
    }
    
    /**
     * 将实体转换为VO
     */
    private MonitorWebsiteVisitVO convertToVO(MonitorWebsiteVisit websiteVisit) {
        if (websiteVisit == null) {
            return null;
        }
        
        MonitorWebsiteVisitVO vo = new MonitorWebsiteVisitVO();
        vo.setId(websiteVisit.getId());
        vo.setUrl(websiteVisit.getUrl());
        vo.setTitle(websiteVisit.getTitle());
        vo.setVisitTime(websiteVisit.getVisitTime());
        vo.setTime(websiteVisit.getVisitTime().format(TIME_FORMATTER));
        vo.setDuration(websiteVisit.getDuration());
        vo.setRiskLevel(websiteVisit.getRiskLevel());
        vo.setRisk(websiteVisit.getRiskLevel() > 0 ? "warning" : "normal");
        vo.setDescription(websiteVisit.getDescription());
        vo.setIsBlacklist(websiteVisit.getIsBlacklist());
        
        return vo;
    }
} 