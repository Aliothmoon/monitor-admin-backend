package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorScreenRecord;
import com.swust.aliothmoon.mapper.MonitorScreenRecordMapper;
import com.swust.aliothmoon.service.MonitorScreenRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 录屏管理服务实现类。
 *
 * @author Alioth
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorScreenRecordServiceImpl extends ServiceImpl<MonitorScreenRecordMapper, MonitorScreenRecord> implements MonitorScreenRecordService {

    private final MonitorScreenRecordMapper screenRecordMapper;

    @Override
    public List<MonitorScreenRecord> listByRiskLevel(Integer riskLevel) {
        return screenRecordMapper.listByRiskLevel(riskLevel);
    }

    @Override
    public List<MonitorScreenRecord> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return screenRecordMapper.listByTimeRange(startTime, endTime);
    }

    @Override
    public List<MonitorScreenRecord> listByExamId(Integer examId) {
        return screenRecordMapper.listByExamId(examId);
    }

    @Override
    public List<MonitorScreenRecord> listByStudentId(Integer studentId) {
        return screenRecordMapper.listByStudentId(studentId);
    }
} 