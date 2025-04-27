package com.swust.aliothmoon.model.domain;

import lombok.Data;

/**
 * 域名黑名单更新数据传输对象
 *
 * @author Alioth
 * @since 2023-06-11
 */
@Data
public class DomainBlacklistUpdateDTO {
    
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
} 