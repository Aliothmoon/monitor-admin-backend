package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.time.LocalDateTime;
import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 域名黑名单实体类。
 *
 * @author Alioth
 * @since 2023-06-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_domain_blacklist")
public class MonitorDomainBlacklist {

    /**
     * 主键ID
     */
    @Id
    @Column("id")
    private Integer id;

    /**
     * 域名
     */
    @Column("domain")
    private String domain;
    
    /**
     * 描述
     */
    @Column("description")
    private String description;
    
    /**
     * 分类
     */
    @Column("category")
    private String category;
    
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