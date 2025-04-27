package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorDomainBlacklist;

import java.util.List;

/**
 * 域名黑名单服务接口。
 *
 * @author Alioth
 *
 */
public interface MonitorDomainBlacklistService extends IService<MonitorDomainBlacklist> {

    /**
     * 根据分类查询域名黑名单
     *
     * @param category 分类
     * @return 域名列表
     */
    List<MonitorDomainBlacklist> listByCategory(String category);

    /**
     * 检查域名是否已存在
     *
     * @param domain 域名
     * @param id 排除的ID（用于更新操作时验证）
     * @return 是否存在
     */
    boolean checkDomainExists(String domain, Integer id);
} 