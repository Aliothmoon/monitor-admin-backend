package com.swust.aliothmoon.model.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 域名黑名单视图对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class DomainBlacklistVO {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 域名
     */
    private String domain;

    /**
     * 描述
     */
    private String description;

    /**
     * 分类
     */
    private String category;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 