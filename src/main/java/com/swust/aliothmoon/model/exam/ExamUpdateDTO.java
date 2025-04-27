package com.swust.aliothmoon.model.exam;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考试更新数据传输对象
 *
 * @author Alioth
 *
 */
@Data
public class ExamUpdateDTO {

    /**
     * 考试ID
     */
    private Integer id;

    /**
     * 考试名称
     */
    private String name;

    /**
     * 考试描述
     */
    private String description;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 考试时长(分钟)
     */
    private Integer duration;
} 