package com.swust.aliothmoon.context;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;

import java.util.concurrent.TimeUnit;

public class ExamineeAccountContext {
    private static final ThreadLocal<ExamineeAccount> LOCAL = new ThreadLocal<>();

    public static final TimedCache<String, Boolean> ONLINE_CANDIDATE = CacheUtil.newTimedCache(TimeUnit.MINUTES.toMillis(40));


    public static void set(ExamineeAccount val) {
        ONLINE_CANDIDATE.put(StrUtil.toString(val.getAccountId()), true);
        LOCAL.set(val);
    }

    public static ExamineeAccount get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
