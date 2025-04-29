package com.swust.aliothmoon.model.user;

import lombok.Data;

@Data
public class LoggedInUserInfo {
    private Integer userId;
    private String username;
    private Integer roleId;


    private String fileUrlPrefix;

    // 扩展个人信息字段
    private String email;
    private String phone;
    private String nickname;
    private String college;
    private String department;
    private String title;
    private String employeeId;
    private String profile;
    private String registrationDate;
    private String accountId;
    private Integer certification;
}
