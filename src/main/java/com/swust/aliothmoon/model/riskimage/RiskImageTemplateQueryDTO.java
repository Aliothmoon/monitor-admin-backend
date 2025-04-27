package com.swust.aliothmoon.model.riskimage;

import lombok.Data;

/**
 * 风险图片模板查询数据传输对象
 *
 * @author Alioth
 *
 */
@Data
public class RiskImageTemplateQueryDTO {

    /**
     * 关键词（用于搜索名称和描述）
     */
    private String keyword;

    /**
     * 分类
     */
    private String category;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;
} 