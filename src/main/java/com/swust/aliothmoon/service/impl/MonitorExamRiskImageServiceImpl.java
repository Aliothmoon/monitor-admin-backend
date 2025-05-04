package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorExamRiskImage;
import com.swust.aliothmoon.mapper.MonitorExamRiskImageMapper;
import com.swust.aliothmoon.service.MonitorExamRiskImageService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorExamRiskImageTableDef.MONITOR_EXAM_RISK_IMAGE;

/**
 * 考试-风险图片模板关联表服务实现类
 *
 * @author Alioth
 *
 */
@Service
public class MonitorExamRiskImageServiceImpl extends ServiceImpl<MonitorExamRiskImageMapper, MonitorExamRiskImage>
        implements MonitorExamRiskImageService {

    @Override
    public List<MonitorExamRiskImage> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_RISK_IMAGE.EXAM_ID.eq(examId));
        return list(queryWrapper);
    }

    @Override
    public boolean removeByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_RISK_IMAGE.EXAM_ID.eq(examId));
        return remove(queryWrapper);
    }
} 