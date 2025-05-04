package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 风险图片模板实体类。
 *
 * @author Aliothmoon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_risk_image_template")
public class MonitorRiskImageTemplate {

    /**
     * 主键ID
     */
    @Id
    @Column("id")
    private Integer id;

    /**
     * 模板名称
     */
    @Column("name")
    private String name;

    /**
     * 描述
     */
    @Column("description")
    private String description;

    /**
     * 分类（公式、答案、小抄、参考表等）
     */
    @Column("category")
    private String category;

    /**
     * 图片URL
     */
    @Column("image_url")
    private String imageUrl;

    /**
     * 相似度阈值（1-100）
     */
    @Column("similarity")
    private Integer similarity;

    /**
     * 创建时间
     */
    @Column("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 创建者ID
     */
    @Column("created_by")
    private Integer createdBy;

    /**
     * 更新者ID
     */
    @Column("updated_by")
    private Integer updatedBy;
} 