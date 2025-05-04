package com.swust.aliothmoon.interceptor;

import com.swust.aliothmoon.interceptor.define.MatchedHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * IP拦截器，用于获取客户端真实IP
 *
 * @author Aliothmoon
 *
 */
@Slf4j
@Component
public class IpInterceptor implements MatchedHandlerInterceptor {

    private static final List<String> IP_HEADER_NAMES = Arrays.asList(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    );

    /**
     * 获取客户端真实IP地址
     *
     * @param request 请求对象
     * @return 真实IP地址
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = null;

        // 尝试从各种Header中获取真实IP
        for (String header : IP_HEADER_NAMES) {
            ip = request.getHeader(header);
            if (isValidIp(ip)) {
                break;
            }
        }

        // 如果仍未获取到有效IP，使用远程地址
        if (!isValidIp(ip)) {
            ip = request.getRemoteAddr();
        }

        // 处理多个IP的情况（通常是经过多个代理）
        if (ip != null && ip.contains(",")) {
            // 取第一个非unknown的有效IP
            String[] ips = ip.split(",");
            for (String candidateIp : ips) {
                if (isValidIp(candidateIp)) {
                    ip = candidateIp.trim();
                    break;
                }
            }
        }

        return ip == null ? "unknown" : ip.trim();
    }

    /**
     * 判断IP是否有效
     *
     * @param ip IP地址
     * @return 是否有效
     */
    private static boolean isValidIp(String ip) {
        return ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = getClientIpAddress(request);
        request.setAttribute("clientIp", ip);
        return true;
    }

    @Override
    public List<String> includes() {
        return Collections.singletonList("/**");
    }
}