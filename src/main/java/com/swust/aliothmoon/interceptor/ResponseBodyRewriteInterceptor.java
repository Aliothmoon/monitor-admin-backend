package com.swust.aliothmoon.interceptor;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson2.JSON;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.define.TableDataInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseBodyRewriteInterceptor implements ResponseBodyAdvice<Object> {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> retType = returnType.getMethod().getReturnType();
        return retType != TableDataInfo.class && retType != R.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return R.ok(body);
    }

    @ExceptionHandler(Throwable.class)
    public void handleError(Throwable e) throws IOException {
        log.info("Error with {}", request.getPathInfo(), e);

        String resp = JSON.toJSONString(R.failed(e.getMessage()));
        response.setContentType(ContentType.JSON.getValue());
        response.getWriter().write(resp);
    }
}
