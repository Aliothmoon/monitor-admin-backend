package com.swust.aliothmoon.service.impl;

import com.github.ksuid.Ksuid;
import com.swust.aliothmoon.constant.ErrorCode;
import com.swust.aliothmoon.constant.Keys;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.entity.MonitorUserProfile;
import com.swust.aliothmoon.mapper.MonitorUserProfileMapper;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import com.swust.aliothmoon.model.user.LoginTuple;
import com.swust.aliothmoon.service.MonitorUserService;
import com.swust.aliothmoon.service.UserInfoService;
import com.swust.aliothmoon.utils.CryptoUtils;
import com.swust.aliothmoon.utils.MiscUtils;
import com.swust.aliothmoon.utils.RedisUtils;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.swust.aliothmoon.entity.table.MonitorUserTableDef.MONITOR_USER;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final MonitorUserService userService;
    private final MonitorUserProfileMapper userProfileMapper;

    @Override
    public R<LoggedInUserInfo> self() {
        LoggedInUserInfo userInfo = UserInfoContext.get();

        // 加载用户个人信息
        MonitorUserProfile profile = userProfileMapper.getByUserId(userInfo.getUserId());
        if (profile != null) {
            userInfo.setNickname(profile.getNickname());
            userInfo.setEmail(profile.getEmail());
            userInfo.setPhone(profile.getPhone());
            userInfo.setCollege(profile.getCollege());
            userInfo.setDepartment(profile.getDepartment());
            userInfo.setTitle(profile.getTitle());
            userInfo.setEmployeeId(profile.getEmployeeId());
            userInfo.setProfile(profile.getProfile());
        }

        return R.ok(userInfo);
    }

    @Override
    public R<String> login(LoginTuple info) {
        MonitorUser user = userService.queryChain().where(
                MONITOR_USER.ACCOUNT.eq(info.getAccount())
        ).one();
        if (user == null) {
            return ErrorCode.USER_NOT_EXIST.warp();
        }
        String pwd = CryptoUtils.hashPassword(info.getPassword());
        if (!MiscUtils.eq(pwd, user.getPassword())) {
            return ErrorCode.ERROR_PWD.warp();
        }
        String key = Ksuid.newKsuid().asRaw();
        LoggedInUserInfo val = TransferUtils.to(user, LoggedInUserInfo.class);
        RedisUtils.set(Keys.TOKEN.invoke(key), val, 1, TimeUnit.DAYS);

        return R.ok(key);
    }

    @Override
    public boolean logout() {
        return true;
    }
}
