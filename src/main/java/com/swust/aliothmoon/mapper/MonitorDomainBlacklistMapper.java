package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.MonitorDomainBlacklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 域名黑名单 Mapper 接口。
 *
 * @author Alioth
 *
 */
@Mapper
public interface MonitorDomainBlacklistMapper extends BaseMapper<MonitorDomainBlacklist> {

    /**
     * 根据分类查询域名黑名单
     *
     * @param category 分类
     * @return 域名列表
     */
    List<MonitorDomainBlacklist> listByCategory(@Param("category") String category);

    /**
     * 检查域名是否已存在
     *
     * @param domain 域名
     * @param id 排除的ID（用于更新操作时验证）
     * @return 计数
     */
    Integer checkDomainExists(@Param("domain") String domain, @Param("id") Integer id);
} 