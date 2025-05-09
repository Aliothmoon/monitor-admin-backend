package com.swust.aliothmoon.model.profile;

import lombok.Data;

/**
 * 用户个人信息更新数据传输对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class UserProfileUpdateDTO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 所在学院
     */
    private String college;

    /**
     * 所在系/部门
     */
    private String department;

    /**
     * 职称/职位
     */
    private String title;

    /**
     * 工号
     */
    private String employeeId;

    /**
     * 个人简介
     */
    private String profile;
} 