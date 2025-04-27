package com.swust.aliothmoon.model.riskimage;

import lombok.Data;

/**
 * 风险图片模板添加数据传输对象
 *
 * @author Alioth
 *
 */
@Data
public class RiskImageTemplateAddDTO {

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
} 