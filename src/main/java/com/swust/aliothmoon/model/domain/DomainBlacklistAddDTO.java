package com.swust.aliothmoon.model.domain;

import lombok.Data;

/**
 * 域名黑名单添加数据传输对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class DomainBlacklistAddDTO {

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