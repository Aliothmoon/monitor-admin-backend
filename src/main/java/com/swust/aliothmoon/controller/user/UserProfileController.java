package com.swust.aliothmoon.controller.user;

import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.model.profile.UserProfileUpdateDTO;
import com.swust.aliothmoon.model.profile.UserProfileVO;
import com.swust.aliothmoon.service.MonitorUserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户个人信息控制器
 *
 * @author Alioth
 * @since 2023-06-11
 */
@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final MonitorUserProfileService userProfileService;

    /**
     * 获取用户个人信息
     *
     * @return 个人信息
     */
    @GetMapping
    public R<UserProfileVO> getUserProfile() {
        Integer userId = UserInfoContext.get().getUserId();
        UserProfileVO profile = userProfileService.getByUserId(userId);
        return profile != null ? R.ok(profile) : R.failed("未找到用户个人信息");
    }

    /**
     * 更新用户个人信息
     *
     * @param dto 更新数据
     * @return 更新结果
     */
    @PutMapping
    public R<Boolean> updateUserProfile(@RequestBody UserProfileUpdateDTO dto) {
        Integer userId = UserInfoContext.get().getUserId();
        boolean success = userProfileService.updateUserProfile(userId, dto);
        return success ? R.ok(true) : R.failed("更新个人信息失败");
    }
} 