package com.swust.aliothmoon.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson2.JSON;
import com.swust.aliothmoon.constant.ErrorCode;
import com.swust.aliothmoon.context.ExamineeAccountContext;
import com.swust.aliothmoon.context.TokenHeaderContext;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.interceptor.define.MatchedHandlerInterceptor;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import com.swust.aliothmoon.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.swust.aliothmoon.constant.Keys.CANDIDATE_TOKEN;
import static com.swust.aliothmoon.constant.Keys.TOKEN;

@Slf4j
@Component
public class AuthorizedInterceptor implements MatchedHandlerInterceptor {
    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Auth URL {}", request.getRequestURI());
        String val = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (val != null) {
            var key = StrUtil.removePrefix(val, "Bearer ");
            LoggedInUserInfo info = RedisUtils.getVal(TOKEN.apply(key), LoggedInUserInfo.class);
            if (info != null) {
                UserInfoContext.set(info);
                TokenHeaderContext.set(key);
                return true;
            }
            ExamineeAccount account = RedisUtils.getVal(CANDIDATE_TOKEN.apply(key), ExamineeAccount.class);
            if (account != null) {
                ExamineeAccountContext.set(account);
                return true;
            }


        }

        String resp = JSON.toJSONString(ErrorCode.NO_AUTHORITY.warp());
        response.setContentType(ContentType.JSON.getValue());
        response.getWriter().write(resp);

        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoContext.remove();
        TokenHeaderContext.remove();
    }

    @Override
    public List<String> includes() {
        return List.of("/**");
    }

    @Override
    public List<String> excludes() {
        return List.of(
                "/user/login",
                "/user/logout",
                "/ws/monitor/**",
                "/examinee/account/login"
        );
    }
}
