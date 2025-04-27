package com.swust.aliothmoon.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson2.JSON;
import com.swust.aliothmoon.constant.ErrorCode;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.interceptor.define.MatchedHandlerInterceptor;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import com.swust.aliothmoon.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.swust.aliothmoon.constant.Keys.TOKEN;

@Slf4j
@Component
public class AuthorizedInterceptor implements MatchedHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Auth URL {}", request.getRequestURI());
        String val = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (val != null) {
            var key = StrUtil.removePrefix(val, "Bearer ");
            LoggedInUserInfo info = RedisUtils.getVal(TOKEN.invoke(key), LoggedInUserInfo.class);
            if (info != null) {
                UserInfoContext.set(info);
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
    }

    @Override
    public List<String> includes() {
        return List.of("/**");
    }

    @Override
    public List<String> excludes() {
        return List.of("/user/login", "/user/logout");
    }
}
