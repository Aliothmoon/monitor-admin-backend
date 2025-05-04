package com.swust.aliothmoon.model.vo;

import lombok.Data;

/**
 * 考生详情VO
 * 用于监控页面显示考生信息
 */
@Data
public class ExamineeDetailVO {
    /**
     * 考生信息ID
     */
    private Integer examineeInfoId;
    
    /**
     * 考生账号ID
     */
    private Integer accountId;
    
    /**
     * 考试ID
     */
    private Integer examId;
    
    /**
     * 考试名称
     */
    private String examName;
    
    /**
     * 考生姓名
     */
    private String name;
    
    /**
     * 考生学号
     */
    private String studentId;
    
    /**
     * 学院
     */
    private String college;
    
    /**
     * 班级
     */
    private String className;
    
    /**
     * 考生状态: 1-未登录 2-已登录 3-离线
     */
    private Integer status;
    
    /**
     * 最后活动时间
     */
    private long lastActivity;
    
    /**
     * 屏幕截图Base64
     */
    private String screenshot;
    
    /**
     * 切屏次数
     */
    private Integer switchCount;
} 