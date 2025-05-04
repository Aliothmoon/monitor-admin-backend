package com.swust.aliothmoon.model.riskimage;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 风险图片模板视图对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class RiskImageTemplateVO {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 分类（公式、答案、小抄、参考表等）
     */
    private String category;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 相似度阈值（1-100）
     */
    private Integer similarity;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 