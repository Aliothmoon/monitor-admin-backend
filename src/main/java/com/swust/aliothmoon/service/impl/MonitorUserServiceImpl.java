package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.mapper.MonitorUserMapper;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.service.MonitorUserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Alioth
 *
 */
@Service
public class MonitorUserServiceImpl extends ServiceImpl<MonitorUserMapper, MonitorUser> implements MonitorUserService {
    @Override
    public TableDataInfo<MonitorUser> getPageData(PageInfo page) {
        Page<MonitorUser> p = page(page.toPage());
        return TableDataInfo.of(p);
    }
}
