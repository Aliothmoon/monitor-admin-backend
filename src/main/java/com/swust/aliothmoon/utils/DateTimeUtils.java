package com.swust.aliothmoon.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期时间工具类
 * 用于处理多种日期格式
 */
public class DateTimeUtils {

    /**
     * 支持的日期时间格式列表
     */
    private static final List<DateTimeFormatter> FORMATTERS = new ArrayList<>();

    static {
        // 添加支持的格式
        FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        FORMATTERS.add(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
    }

    /**
     * 将字符串解析为LocalDateTime，尝试多种格式
     *
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime对象，如果解析失败返回null
     */
    public static LocalDateTime parse(String dateTimeStr) {
        if (!StringUtils.hasText(dateTimeStr)) {
            return null;
        }

        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDateTime.parse(dateTimeStr, formatter);
            } catch (DateTimeParseException e) {
                // 解析失败，尝试下一个格式
            }
        }

        throw new IllegalArgumentException("不支持的日期时间格式: " + dateTimeStr);
    }

    /**
     * 将LocalDateTime格式化为字符串，使用默认格式
     *
     * @param dateTime LocalDateTime对象
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(FORMATTERS.get(0));
    }

    /**
     * 将LocalDateTime格式化为字符串，使用指定的格式
     *
     * @param dateTime LocalDateTime对象
     * @param pattern  日期格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
} 