package com.swust.aliothmoon.model.vo;

import lombok.Data;

/**
 * 考生登录响应VO
 *
 * @author Aliothmoon
 */
@Data
public class ExamineeLoginVO {
    /**
     * JWT令牌
     */
    private String token;

    /**
     * 考生账号ID
     */
    private Integer accountId;

    /**
     * 考试ID
     */
    private Integer examId;
} 