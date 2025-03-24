package com.swust.aliothmoon.utils;

import lombok.experimental.UtilityClass;

import java.time.*;

@UtilityClass
public class DurationUtils {

    private static final ZoneId SHANGHAI_ZONE = ZoneId.of("Asia/Shanghai");

    public static String calculateDuration(LocalDateTime start, LocalDateTime end) {
        Instant startInstant = start.atZone(SHANGHAI_ZONE).toInstant();
        Instant endInstant = end.atZone(SHANGHAI_ZONE).toInstant();

        Duration duration = Duration.between(startInstant, endInstant);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return hours + "小时" + minutes + "分钟";
    }

    public static String getStayDuration(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkOut == null) {
            checkOut = LocalDateTime.now(SHANGHAI_ZONE);
        }
        return calculateDuration(checkIn, checkOut);
    }

    public static String getPauseTimeLeft(LocalDateTime pauseStart, Integer pauseMinutes) {
        // 将暂停开始时间转换为中国上海时区的ZonedDateTime对象
        ZonedDateTime pauseStartTime = ZonedDateTime.of(pauseStart, SHANGHAI_ZONE);

        // 计算暂停结束时间
        ZonedDateTime pauseEndTime = pauseStartTime.plusMinutes(pauseMinutes);

        // 计算还剩下多少暂停时间
        ZonedDateTime now = ZonedDateTime.now(SHANGHAI_ZONE);
        Duration duration = Duration.between(now, pauseEndTime);
        long minutesLeft = duration.toMinutes();

        return minutesLeft + "分钟";
    }

    public static Integer getDuration(LocalDateTime pauseStart, LocalDateTime pauseEnd) {
        if (pauseEnd == null) {
            pauseEnd = LocalDateTime.now(SHANGHAI_ZONE);
        }
        if (pauseStart == null) {
            return 0;
        }
        Instant pauseStartInstant = pauseStart.atZone(SHANGHAI_ZONE).toInstant();
        Instant pauseEndInstant = pauseEnd.atZone(SHANGHAI_ZONE).toInstant();

        Duration duration = Duration.between(pauseStartInstant, pauseEndInstant);
        return (int)duration.toMinutes();
    }
}

