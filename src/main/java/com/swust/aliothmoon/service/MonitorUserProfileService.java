package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorUserProfile;
import com.swust.aliothmoon.model.profile.UserProfileUpdateDTO;
import com.swust.aliothmoon.model.profile.UserProfileVO;

/**
 * 用户个人信息服务接口
 *
 * @author Alioth
 * @since 2023-06-11
 */
public interface MonitorUserProfileService extends IService<MonitorUserProfile> {
    
    /**
     * 根据用户ID获取个人信息
     *
     * @param userId 用户ID
     * @return 个人信息VO
     */
    UserProfileVO getByUserId(Integer userId);
    
    /**
     * 更新用户个人信息
     *
     * @param userId 用户ID
     * @param dto 更新数据
     * @return 是否成功
     */
    boolean updateUserProfile(Integer userId, UserProfileUpdateDTO dto);
} 