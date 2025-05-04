package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.model.dto.PageInfo;

/**
 *  服务层。
 *
 * @author Alioth
 *
 */
public interface MonitorUserService extends IService<MonitorUser> {

    TableDataInfo<MonitorUser> getPageData(PageInfo page);

    /**
     * 根据条件获取分页数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param username 用户名（可选）
     * @param account 账号（可选）
     * @return 分页数据
     */
    TableDataInfo<MonitorUser> getPageData(int pageNum, int pageSize, String username, String account);
}
