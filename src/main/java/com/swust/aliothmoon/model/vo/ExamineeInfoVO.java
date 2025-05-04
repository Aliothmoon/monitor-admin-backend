package com.swust.aliothmoon.model.vo;

import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.entity.ExamineeInfo;
import lombok.Data;

/**
 * 考生信息响应VO
 *
 * @author Aliothmoon
 */
@Data
public class ExamineeInfoVO {
    /**
     * 考生账号信息
     */
    private ExamineeAccount account;

    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 考生个人信息
     */
    private ExamineeInfo examineeInfo;
} 