package com.swust.aliothmoon.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考生屏幕截图VO类
 */
@Data
public class MonitorScreenshotVO {
    
    /**
     * 主键ID
     */
    private Integer id;
    
    /**
     * 截图URL
     */
    private String url;
    
    /**
     * 截图时间
     */
    private LocalDateTime captureTime;
    
    /**
     * 截图时间(字符串)
     */
    private String time;
    
    /**
     * 是否有异常：0-正常 1-异常
     */
    private Boolean hasWarning;
    
    /**
     * 分析结果
     */
    private String analysis;
} 