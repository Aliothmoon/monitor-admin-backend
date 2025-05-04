package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.entity.MonitorUserProfile;
import com.swust.aliothmoon.mapper.MonitorUserMapper;
import com.swust.aliothmoon.mapper.MonitorUserProfileMapper;
import com.swust.aliothmoon.model.profile.UserProfileUpdateDTO;
import com.swust.aliothmoon.model.profile.UserProfileVO;
import com.swust.aliothmoon.service.MonitorUserProfileService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户个人信息服务实现类
 *
 * @author Alioth
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorUserProfileServiceImpl extends ServiceImpl<MonitorUserProfileMapper, MonitorUserProfile> implements MonitorUserProfileService {

    private final MonitorUserProfileMapper userProfileMapper;
    private final MonitorUserMapper userMapper;

    @Override
    @Transactional
    public UserProfileVO getByUserId(Integer userId) {
        MonitorUserProfile profile = userProfileMapper.getByUserId(userId);

        // 如果找不到个人信息，则创建一个默认的
        if (profile == null) {
            profile = createDefaultProfile(userId);
        }

        return TransferUtils.to(profile, UserProfileVO.class);
    }

    /**
     * 创建默认的用户配置文件
     *
     * @param userId 用户ID
     * @return 新创建的配置文件
     */
    private MonitorUserProfile createDefaultProfile(Integer userId) {
        // 查询用户信息，获取用户名
        MonitorUser user = userMapper.selectOneById(userId);
        String username = user != null ? user.getUsername() : "用户" + userId;

        LocalDateTime now = LocalDateTime.now();
        MonitorUserProfile profile = new MonitorUserProfile();
        profile.setUserId(userId);
        profile.setNickname(username);
        profile.setCreatedAt(now);
        profile.setUpdatedAt(now);
        profile.setCreatedBy(userId);
        profile.setUpdatedBy(userId);

        // 保存到数据库
        save(profile);

        return profile;
    }

    @Override
    public boolean updateUserProfile(Integer userId, UserProfileUpdateDTO dto) {
        MonitorUserProfile profile = userProfileMapper.getByUserId(userId);
        LocalDateTime now = LocalDateTime.now();

        if (profile == null) {
            // 创建新的个人信息记录
            profile = new MonitorUserProfile();
            profile.setUserId(userId);
            profile.setNickname(dto.getNickname());
            profile.setEmail(dto.getEmail());
            profile.setPhone(dto.getPhone());
            profile.setCollege(dto.getCollege());
            profile.setDepartment(dto.getDepartment());
            profile.setTitle(dto.getTitle());
            profile.setEmployeeId(dto.getEmployeeId());
            profile.setProfile(dto.getProfile());
            profile.setCreatedAt(now);
            profile.setUpdatedAt(now);
            profile.setCreatedBy(userId);
            profile.setUpdatedBy(userId);

            return save(profile);
        } else {
            // 更新现有个人信息
            profile.setNickname(dto.getNickname());
            profile.setEmail(dto.getEmail());
            profile.setPhone(dto.getPhone());
            profile.setCollege(dto.getCollege());
            profile.setDepartment(dto.getDepartment());
            profile.setTitle(dto.getTitle());
            profile.setEmployeeId(dto.getEmployeeId());
            profile.setProfile(dto.getProfile());
            profile.setUpdatedAt(now);
            profile.setUpdatedBy(userId);

            return updateById(profile);
        }
    }
} 