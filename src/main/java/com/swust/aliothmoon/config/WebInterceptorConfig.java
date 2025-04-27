package com.swust.aliothmoon.config;

import com.swust.aliothmoon.interceptor.define.MatchedHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Comparator;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebInterceptorConfig implements WebMvcConfigurer {
    private final List<MatchedHandlerInterceptor> interceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.sort(Comparator.comparingInt(MatchedHandlerInterceptor::order));
        for (MatchedHandlerInterceptor interceptor : interceptors) {
            registry.addInterceptor(interceptor)
                    .addPathPatterns(interceptor.includes())
                    .excludePathPatterns(interceptor.excludes());
        }
    }
}
