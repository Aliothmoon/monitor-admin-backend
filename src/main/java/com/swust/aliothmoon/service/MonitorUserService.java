package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.model.dto.PageInfo;

/**
 *  服务层。
 *
 * @author Alioth
 * @since 2025-03-24
 */
public interface MonitorUserService extends IService<MonitorUser> {

    TableDataInfo<MonitorUser> getPageData(PageInfo page);
}
