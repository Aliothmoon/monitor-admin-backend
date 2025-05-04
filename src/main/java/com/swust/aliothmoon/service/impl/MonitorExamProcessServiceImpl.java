package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorExamProcess;
import com.swust.aliothmoon.mapper.MonitorExamProcessMapper;
import com.swust.aliothmoon.service.MonitorExamProcessService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorExamProcessTableDef.MONITOR_EXAM_PROCESS;

/**
 * 考试-可疑进程关联表服务实现类
 *
 * @author Alioth
 *
 */
@Service
public class MonitorExamProcessServiceImpl extends ServiceImpl<MonitorExamProcessMapper, MonitorExamProcess>
        implements MonitorExamProcessService {

    @Override
    public List<MonitorExamProcess> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_PROCESS.EXAM_ID.eq(examId));
        return list(queryWrapper);
    }

    @Override
    public boolean removeByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_PROCESS.EXAM_ID.eq(examId));
        return remove(queryWrapper);
    }

    @Override
    public long countByProcessId(Integer processId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_PROCESS.PROCESS_ID.eq(processId));
        return count(queryWrapper);
    }
} 