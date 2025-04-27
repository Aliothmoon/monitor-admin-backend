package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.MonitorUserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户个人信息 Mapper 接口
 *
 * @author Alioth
 * @since 2023-06-11
 */
@Mapper
public interface MonitorUserProfileMapper extends BaseMapper<MonitorUserProfile> {
    
    /**
     * 根据用户ID查询个人信息
     *
     * @param userId 用户ID
     * @return 个人信息
     */
    MonitorUserProfile getByUserId(@Param("userId") Integer userId);
} 