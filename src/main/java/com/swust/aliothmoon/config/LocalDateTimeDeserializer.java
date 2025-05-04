package com.swust.aliothmoon.config;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.swust.aliothmoon.utils.ThrowsUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return this.deserialize(p.getText().trim());
    }


    /**
     * 默认日期时间格式
     */
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * ISO日期时间格式
     */
    private static final String ISO_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    private LocalDateTime deserialize(String source) {
        try {
            return LocalDateTimeUtil.parse(source, ISO_DATE_TIME_PATTERN);
        } catch (Exception ignored) {
        }
        try {
            return LocalDateTimeUtil.parse(source, DEFAULT_DATE_TIME_PATTERN);
        } catch (Exception e) {
            return ThrowsUtils.doThrow(e);
        }
    }

}