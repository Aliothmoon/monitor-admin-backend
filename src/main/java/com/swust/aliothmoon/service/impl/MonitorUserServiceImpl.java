package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.entity.table.MonitorUserTableDef;
import com.swust.aliothmoon.mapper.MonitorUserMapper;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.service.MonitorUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public TableDataInfo<MonitorUser> getPageData(int pageNum, int pageSize, String username, String account) {
        MonitorUserTableDef monitorUser = MonitorUserTableDef.MONITOR_USER;

        QueryChain<MonitorUser> queryChain = QueryChain.of(MonitorUser.class);

        // 添加查询条件
        if (StringUtils.hasText(username)) {
            queryChain.where(monitorUser.USERNAME.like(username));
        }

        if (StringUtils.hasText(account)) {
            queryChain.where(monitorUser.ACCOUNT.like(account));
        }

        // 按创建时间排序
        queryChain.orderBy(monitorUser.CREATED_AT.desc());

        // 执行分页查询
        Page<MonitorUser> page = new Page<>(pageNum, pageSize);
        Page<MonitorUser> result = page(page, queryChain);

        return TableDataInfo.of(result);
    }
}
