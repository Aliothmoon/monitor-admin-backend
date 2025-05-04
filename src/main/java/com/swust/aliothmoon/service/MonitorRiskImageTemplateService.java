package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorRiskImageTemplate;

import java.util.List;

/**
 * 风险图片模板服务接口。
 *
 * @author Aliothmoon
 *
 */
public interface MonitorRiskImageTemplateService extends IService<MonitorRiskImageTemplate> {

    /**
     * 根据分类查询风险图片模板
     *
     * @param category 分类
     * @return 模板列表
     */
    List<MonitorRiskImageTemplate> listByCategory(String category);

    /**
     * 根据关键词查询风险图片模板
     *
     * @param keyword 关键词
     * @return 模板列表
     */
    List<MonitorRiskImageTemplate> listByKeyword(String keyword);

    /**
     * 检查模板名称是否已存在
     *
     * @param name 模板名称
     * @param id 排除的ID（用于更新操作时验证）
     * @return 是否存在
     */
    boolean checkNameExists(String name, Integer id);
} 