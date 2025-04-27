package com.swust.aliothmoon.model.dto;

import lombok.Data;

/**
 * 考生登录请求DTO
 *
 * @author Alioth
 */
@Data
public class ExamineeLoginDTO {
    /**
     * 账号
     */
    private String account;
    
    /**
     * 密码
     */
    private String password;
} 