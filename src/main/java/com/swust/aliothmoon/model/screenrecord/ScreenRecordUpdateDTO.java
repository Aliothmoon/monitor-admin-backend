package com.swust.aliothmoon.model.screenrecord;

import lombok.Data;

/**
 * 录屏更新数据传输对象
 *
 * @author Aliothmoon
 *
 */
@Data
public class ScreenRecordUpdateDTO {

    /**
     * 录屏ID
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