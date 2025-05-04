package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorExamDomain;
import com.swust.aliothmoon.mapper.MonitorExamDomainMapper;
import com.swust.aliothmoon.service.MonitorExamDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorExamDomainTableDef.MONITOR_EXAM_DOMAIN;

/**
 * 考试-域名黑名单关联表服务实现类
 *
 * @author Aliothmoon
 *
 */
@Service
public class MonitorExamDomainServiceImpl extends ServiceImpl<MonitorExamDomainMapper, MonitorExamDomain>
        implements MonitorExamDomainService {

    @Override
    public List<MonitorExamDomain> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_DOMAIN.EXAM_ID.eq(examId));
        return list(queryWrapper);
    }

    @Override
    public boolean removeByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_DOMAIN.EXAM_ID.eq(examId));
        return remove(queryWrapper);
    }

    @Override
    public long countByDomainId(Integer domainId) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.where(MONITOR_EXAM_DOMAIN.DOMAIN_ID.eq(domainId));
        return count(queryWrapper);
    }
} 