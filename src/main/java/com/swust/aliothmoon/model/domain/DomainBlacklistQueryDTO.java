package com.swust.aliothmoon.model.domain;

import lombok.Data;

/**
 * 域名黑名单查询数据传输对象
 *
 * @author Alioth
 * @since 2023-06-11
 */
@Data
public class DomainBlacklistQueryDTO {
    
    /**
     * 域名（模糊查询）
     */
    private String domain;
    
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