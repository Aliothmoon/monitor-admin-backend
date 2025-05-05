package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorBehaviorAnalysis;
import com.swust.aliothmoon.mapper.MonitorBehaviorAnalysisMapper;
import com.swust.aliothmoon.model.vo.MonitorBehaviorAnalysisVO;
import com.swust.aliothmoon.service.MonitorBehaviorAnalysisService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorBehaviorAnalysisTableDef.MONITOR_BEHAVIOR_ANALYSIS;

/**
 * 考生行为分析记录服务实现类
 *
 * @author Aliothmoon
 */
@Service
public class MonitorBehaviorAnalysisServiceImpl extends ServiceImpl<MonitorBehaviorAnalysisMapper, MonitorBehaviorAnalysis> implements MonitorBehaviorAnalysisService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public List<MonitorBehaviorAnalysisVO> getBehaviorAnalysisByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_BEHAVIOR_ANALYSIS.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_BEHAVIOR_ANALYSIS.EXAM_ID.eq(examId))
                .orderBy(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.desc());

        List<MonitorBehaviorAnalysis> behaviorAnalysisList = list(queryWrapper);
        return behaviorAnalysisList.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorBehaviorAnalysisVO> getRecentBehaviorAnalysisByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_BEHAVIOR_ANALYSIS.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_BEHAVIOR_ANALYSIS.EXAM_ID.eq(examId))
                .orderBy(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.desc());

        List<MonitorBehaviorAnalysis> behaviorAnalysisList = list(queryWrapper);
        return behaviorAnalysisList.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public boolean addBehaviorAnalysis(MonitorBehaviorAnalysis behaviorAnalysis) {
        behaviorAnalysis.setCreatedAt(LocalDateTime.now());
        if (behaviorAnalysis.getEventTime() == null) {
            behaviorAnalysis.setEventTime(LocalDateTime.now());
        }
        return save(behaviorAnalysis);
    }

    @Override
    public boolean updateProcessStatus(Integer id, Boolean isProcessed, String processResult, Integer processorId) {
        MonitorBehaviorAnalysis behaviorAnalysis = getById(id);
        if (behaviorAnalysis == null) {
            return false;
        }

        behaviorAnalysis.setIsProcessed(isProcessed);
        behaviorAnalysis.setProcessResult(processResult);
        behaviorAnalysis.setProcessorId(processorId);
        behaviorAnalysis.setProcessTime(LocalDateTime.now());
        behaviorAnalysis.setUpdatedAt(LocalDateTime.now());

        return updateById(behaviorAnalysis);
    }

    @Override
    public List<MonitorBehaviorAnalysis> listByEventType(Integer eventType) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TYPE.eq(eventType))
                .orderBy(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.desc());

        return list(queryWrapper);
    }

    @Override
    public List<MonitorBehaviorAnalysis> listByLevel(String level) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_BEHAVIOR_ANALYSIS.LEVEL.eq(level))
                .orderBy(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.desc());

        return list(queryWrapper);
    }

    @Override
    public List<MonitorBehaviorAnalysis> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.ge(startTime))
                .and(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.le(endTime))
                .orderBy(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.desc());

        return list(queryWrapper);
    }

    @Override
    public List<MonitorBehaviorAnalysis> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_BEHAVIOR_ANALYSIS.EXAM_ID.eq(examId))
                .orderBy(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TIME.desc());

        return list(queryWrapper);
    }

    @Override
    public Integer countSwitchScreens(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_BEHAVIOR_ANALYSIS.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_BEHAVIOR_ANALYSIS.EXAM_ID.eq(examId))
                .and(MONITOR_BEHAVIOR_ANALYSIS.EVENT_TYPE.eq(1)); // 1代表切屏事件

        return Math.toIntExact(count(queryWrapper));
    }

    @Override
    public boolean saveBehaviorAnalysis(Integer examId, Integer examineeAccountId, Integer eventType, String content, String level, LocalDateTime eventTime) {
        // 创建行为分析记录
        MonitorBehaviorAnalysis behaviorAnalysis = new MonitorBehaviorAnalysis();
        behaviorAnalysis.setExamId(examId);
        behaviorAnalysis.setExamineeAccountId(examineeAccountId);
        behaviorAnalysis.setEventType(eventType);
        behaviorAnalysis.setContent(content);
        behaviorAnalysis.setLevel(level);
        behaviorAnalysis.setEventTime(eventTime);

        // 设置默认值
        behaviorAnalysis.setIsProcessed(false);
        behaviorAnalysis.setCreatedAt(LocalDateTime.now());

        // 保存记录
        return addBehaviorAnalysis(behaviorAnalysis);
    }

    /**
     * 将实体转换为VO
     */
    private MonitorBehaviorAnalysisVO convertToVO(MonitorBehaviorAnalysis behaviorAnalysis) {
        if (behaviorAnalysis == null) {
            return null;
        }

        MonitorBehaviorAnalysisVO vo = new MonitorBehaviorAnalysisVO();
        vo.setId(behaviorAnalysis.getId());
        vo.setEventType(behaviorAnalysis.getEventType());
        vo.setContent(behaviorAnalysis.getContent());
        vo.setEventTime(behaviorAnalysis.getEventTime());
        vo.setTime(behaviorAnalysis.getEventTime().format(TIME_FORMATTER));
        vo.setLevel(behaviorAnalysis.getLevel());
        vo.setScreenshotId(behaviorAnalysis.getScreenshotId());
        vo.setRelatedId(behaviorAnalysis.getRelatedId());
        vo.setRelatedType(behaviorAnalysis.getRelatedType());
        vo.setIsProcessed(behaviorAnalysis.getIsProcessed());
        vo.setProcessResult(behaviorAnalysis.getProcessResult());
        vo.setProcessTime(behaviorAnalysis.getProcessTime());
        vo.setProcessorId(behaviorAnalysis.getProcessorId());

        return vo;
    }
} 