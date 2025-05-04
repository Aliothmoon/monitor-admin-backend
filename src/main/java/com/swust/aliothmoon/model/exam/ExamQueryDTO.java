package com.swust.aliothmoon.model.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 考试查询数据传输对象
 *
 * @author Alioth
 *
 */
@Data
public class ExamQueryDTO {

    /**
     * 关键词（模糊查询）
     */
    private String keyword;

    /**
     * 考试状态 0-未开始 1-进行中 2-已结束
     */
    private Integer status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;
} 