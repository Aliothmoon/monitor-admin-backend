package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorSuspiciousProcess;
import com.swust.aliothmoon.mapper.MonitorSuspiciousProcessMapper;
import com.swust.aliothmoon.service.MonitorSuspiciousProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 可疑进程名单服务实现类。
 *
 * @author Alioth
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorSuspiciousProcessServiceImpl extends ServiceImpl<MonitorSuspiciousProcessMapper, MonitorSuspiciousProcess> implements MonitorSuspiciousProcessService {

    private final MonitorSuspiciousProcessMapper suspiciousProcessMapper;

    @Override
    public List<MonitorSuspiciousProcess> listByRiskLevel(Integer riskLevel) {
        return suspiciousProcessMapper.listByRiskLevel(riskLevel);
    }

    @Override
    public boolean checkProcessNameExists(String processName, Integer id) {
        Integer count = suspiciousProcessMapper.checkProcessNameExists(processName, id);
        return count != null && count > 0;
    }
} 