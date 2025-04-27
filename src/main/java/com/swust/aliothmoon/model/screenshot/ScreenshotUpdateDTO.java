package com.swust.aliothmoon.model.screenshot;

import lombok.Data;

/**
 * 截图更新数据传输对象
 *
 * @author Alioth
 * @since 2025-04-28
 */
@Data
public class ScreenshotUpdateDTO {
    
    /**
     * 截图ID
     */
    private Integer id;
    
    /**
     * 风险等级 0-低风险 1-中风险 2-高风险
     */
    private Integer riskLevel;
    
    /**
     * 备注
     */
    private String remark;
} 