package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorExam;
import com.swust.aliothmoon.mapper.MonitorExamMapper;
import com.swust.aliothmoon.service.MonitorExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorExamTableDef.MONITOR_EXAM;

/**
 * 考试管理服务实现类。
 *
 * @author Aliothmoon
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorExamServiceImpl extends ServiceImpl<MonitorExamMapper, MonitorExam> implements MonitorExamService {

    private final MonitorExamMapper examMapper;

    @Override
    public List<MonitorExam> listByStatus(Integer status) {
        return examMapper.listByStatus(status);
    }

    @Override
    public List<MonitorExam> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return examMapper.listByTimeRange(startTime, endTime);
    }

    @Override
    public List<MonitorExam> listByKeyword(String keyword) {
        return examMapper.listByKeyword(keyword);
    }

    @Override
    public boolean updateStatus(Integer id, Integer status) {
        return examMapper.updateStatus(id, status) > 0;
    }

    @Override
//    @Scheduled(cron = "0 0/5 * * * ?") // 每5分钟执行一次
    public void autoUpdateStatus() {
        LocalDateTime now = LocalDateTime.now();

        // 查找所有未开始但开始时间已到的考试，将状态更新为进行中
        QueryWrapper wrapper1 = QueryWrapper.create()
                .where(MONITOR_EXAM.STATUS.eq(0))
                .and(MONITOR_EXAM.START_TIME.le(now))
                .and(MONITOR_EXAM.END_TIME.ge(now));

        List<MonitorExam> toStartExams = list(wrapper1);
        for (MonitorExam exam : toStartExams) {
            updateStatus(exam.getId(), 1);
        }

        // 查找所有进行中但结束时间已到的考试，将状态更新为已结束
        QueryWrapper wrapper2 = QueryWrapper.create()
                .where(MONITOR_EXAM.STATUS.eq(1))
                .and(MONITOR_EXAM.END_TIME.le(now));

        List<MonitorExam> toEndExams = list(wrapper2);
        for (MonitorExam exam : toEndExams) {
            updateStatus(exam.getId(), 2);
        }
    }
}
