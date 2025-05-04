package com.swust.aliothmoon.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考生账号与考生信息关联的视图对象
 *
 * @author
 *
 */
@Data
public class ExamineeAccountWithInfoVO {

    /**
     * 考生账号ID
     */
    private Integer accountId;

    /**
     * 考生信息ID
     */
    private Integer examineeInfoId;

    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 账号
     */
    private String account;

    /**
     * 状态（0-禁用, 1-启用）
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（0-女, 1-男）
     */
    private Integer gender;

    /**
     * 学院
     */
    private String college;

    /**
     * 班级
     */
    private String className;

    /**
     * 专业
     */
    private String major;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;
} 