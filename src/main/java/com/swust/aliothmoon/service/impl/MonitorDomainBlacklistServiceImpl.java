package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorDomainBlacklist;
import com.swust.aliothmoon.mapper.MonitorDomainBlacklistMapper;
import com.swust.aliothmoon.service.MonitorDomainBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 域名黑名单服务实现类。
 *
 * @author Aliothmoon
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorDomainBlacklistServiceImpl extends ServiceImpl<MonitorDomainBlacklistMapper, MonitorDomainBlacklist> implements MonitorDomainBlacklistService {

    private final MonitorDomainBlacklistMapper domainBlacklistMapper;

    @Override
    public List<MonitorDomainBlacklist> listByCategory(String category) {
        return domainBlacklistMapper.listByCategory(category);
    }

    @Override
    public boolean checkDomainExists(String domain, Integer id) {
        Integer count = domainBlacklistMapper.checkDomainExists(domain, id);
        return count != null && count > 0;
    }
} 