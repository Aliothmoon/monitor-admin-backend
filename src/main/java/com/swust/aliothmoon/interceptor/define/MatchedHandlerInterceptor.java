package com.swust.aliothmoon.interceptor.define;

import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.List;

public interface MatchedHandlerInterceptor extends HandlerInterceptor {

    default int order() {
        return 1;
    }

    List<String> includes();

    default List<String> excludes() {
        return Collections.emptyList();
    }
}
