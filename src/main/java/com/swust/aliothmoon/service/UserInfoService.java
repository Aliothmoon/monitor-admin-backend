package com.swust.aliothmoon.service;

import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import com.swust.aliothmoon.model.user.LoginTuple;

public interface UserInfoService {

    R<String> login(LoginTuple info);

    boolean logout();

    R<LoggedInUserInfo> self();

}
