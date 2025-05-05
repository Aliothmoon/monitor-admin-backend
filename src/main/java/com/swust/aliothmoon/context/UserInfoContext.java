package com.swust.aliothmoon.context;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class UserInfoContext {
    private static final ThreadLocal<LoggedInUserInfo> LOCAL = new ThreadLocal<>();

    public static final TimedCache<String, Boolean> ONLINE_CACHE = CacheUtil.newTimedCache(TimeUnit.MINUTES.toMillis(40));


    public static void set(LoggedInUserInfo val) {
        ONLINE_CACHE.put(StrUtil.toString(val.getUserId()), true);
        LOCAL.set(val);
    }

    public static LoggedInUserInfo get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }


}
