package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorRiskImageTemplate;
import com.swust.aliothmoon.mapper.MonitorRiskImageTemplateMapper;
import com.swust.aliothmoon.service.MonitorRiskImageTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风险图片模板服务实现类。
 *
 * @author Alioth
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorRiskImageTemplateServiceImpl extends ServiceImpl<MonitorRiskImageTemplateMapper, MonitorRiskImageTemplate> implements MonitorRiskImageTemplateService {

    private final MonitorRiskImageTemplateMapper riskImageTemplateMapper;

    @Override
    public List<MonitorRiskImageTemplate> listByCategory(String category) {
        return riskImageTemplateMapper.listByCategory(category);
    }

    @Override
    public List<MonitorRiskImageTemplate> listByKeyword(String keyword) {
        return riskImageTemplateMapper.listByKeyword(keyword);
    }

    @Override
    public boolean checkNameExists(String name, Integer id) {
        Integer count = riskImageTemplateMapper.checkNameExists(name, id);
        return count != null && count > 0;
    }
} 