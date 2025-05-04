package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorProcessRecord;
import com.swust.aliothmoon.mapper.MonitorProcessRecordMapper;
import com.swust.aliothmoon.model.vo.MonitorProcessRecordVO;
import com.swust.aliothmoon.service.MonitorProcessRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorProcessRecordTableDef.MONITOR_PROCESS_RECORD;

/**
 * 考生进程记录服务实现类
 *
 * @author Aliothmoon
 */
@Service
public class MonitorProcessRecordServiceImpl extends ServiceImpl<MonitorProcessRecordMapper, MonitorProcessRecord> implements MonitorProcessRecordService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public List<MonitorProcessRecordVO> getProcessRecordsByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        List<MonitorProcessRecord> processRecords = list(queryWrapper);
        return processRecords.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorProcessRecordVO> getRecentProcessRecordsByExaminee(Integer examineeAccountId, Integer examId, Integer limit) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc())
                .limit(limit);
        
        List<MonitorProcessRecord> processRecords = list(queryWrapper);
        return processRecords.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public boolean addProcessRecord(MonitorProcessRecord processRecord) {
        processRecord.setCreatedAt(LocalDateTime.now());
        return save(processRecord);
    }

    @Override
    public boolean updateProcessEndTime(Integer id, LocalDateTime endTime) {
        MonitorProcessRecord processRecord = getById(id);
        if (processRecord == null) {
            return false;
        }
        
        processRecord.setEndTime(endTime);
        return updateById(processRecord);
    }

    @Override
    public List<MonitorProcessRecord> listByRiskLevel(Integer riskLevel) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.RISK_LEVEL.eq(riskLevel))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorProcessRecord> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.START_TIME.ge(startTime))
                .and(MONITOR_PROCESS_RECORD.START_TIME.le(endTime))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorProcessRecord> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorProcessRecord> listByNameKeyword(String keyword) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.PROCESS_NAME.like("%" + keyword + "%"))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public Integer countProcessRecords(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId));
        
        return Math.toIntExact(count(queryWrapper));
    }
    
    /**
     * 将实体转换为VO
     */
    private MonitorProcessRecordVO convertToVO(MonitorProcessRecord processRecord) {
        if (processRecord == null) {
            return null;
        }
        
        MonitorProcessRecordVO vo = new MonitorProcessRecordVO();
        vo.setId(processRecord.getId());
        vo.setName(processRecord.getProcessName());
        vo.setPid(0); // 由于实体中没有PID字段，设置默认值
        vo.setStartTime(processRecord.getStartTime());
        vo.setStartTimeStr(processRecord.getStartTime() != null ? processRecord.getStartTime().format(TIME_FORMATTER) : null);
        vo.setEndTime(processRecord.getEndTime());
        vo.setEndTimeStr(processRecord.getEndTime() != null ? processRecord.getEndTime().format(TIME_FORMATTER) : null);
        vo.setRiskLevel(processRecord.getRiskLevel());
        vo.setStatus(processRecord.getRiskLevel() > 0 ? "warning" : "normal");
        vo.setDescription(processRecord.getDescription());
        vo.setMemoryUsage(0.0); // 由于实体中没有这些字段，设置默认值
        vo.setCpuUsage(0.0);
        vo.setIsBlacklist(false);
        
        return vo;
    }
} 