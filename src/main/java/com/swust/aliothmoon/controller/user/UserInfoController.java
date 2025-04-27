package com.swust.aliothmoon.controller.user;

import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import com.swust.aliothmoon.model.user.LoginTuple;
import com.swust.aliothmoon.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/info")
    public R<LoggedInUserInfo> getUserInfo() {
        return userInfoService.self();
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody LoginTuple info) {
        return userInfoService.login(info);
    }

    @PostMapping("/logout")
    public boolean logout() {
        return userInfoService.logout();
    }
}
