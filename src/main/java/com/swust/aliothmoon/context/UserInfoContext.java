package com.swust.aliothmoon.context;

import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserInfoContext {
    private static final ThreadLocal<LoggedInUserInfo> LOCAL = new ThreadLocal<>();

    public static void set(LoggedInUserInfo val) {
        LOCAL.set(val);
    }

    public static LoggedInUserInfo get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }


}
