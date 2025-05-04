package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorScreenshot;
import com.swust.aliothmoon.mapper.MonitorScreenshotMapper;
import com.swust.aliothmoon.service.MonitorScreenshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 截图管理服务实现类。
 *
 * @author Aliothmoon
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorScreenshotServiceImpl extends ServiceImpl<MonitorScreenshotMapper, MonitorScreenshot> implements MonitorScreenshotService {

    private final MonitorScreenshotMapper screenshotMapper;

    @Override
    public List<MonitorScreenshot> listByRiskLevel(Integer riskLevel) {
        return screenshotMapper.listByRiskLevel(riskLevel);
    }

    @Override
    public List<MonitorScreenshot> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return screenshotMapper.listByTimeRange(startTime, endTime);
    }

    @Override
    public List<MonitorScreenshot> listByExamId(Integer examId) {
        return screenshotMapper.listByExamId(examId);
    }

    @Override
    public List<MonitorScreenshot> listByStudentId(Integer studentId) {
        return screenshotMapper.listByStudentId(studentId);
    }
} 