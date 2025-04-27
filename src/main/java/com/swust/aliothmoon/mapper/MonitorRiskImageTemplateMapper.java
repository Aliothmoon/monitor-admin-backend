package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.MonitorRiskImageTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风险图片模板 Mapper 接口。
 *
 * @author Alioth
 *
 */
@Mapper
public interface MonitorRiskImageTemplateMapper extends BaseMapper<MonitorRiskImageTemplate> {

    /**
     * 根据分类查询风险图片模板
     *
     * @param category 分类
     * @return 模板列表
     */
    List<MonitorRiskImageTemplate> listByCategory(@Param("category") String category);

    /**
     * 关键词搜索风险图片模板
     *
     * @param keyword 关键词（用于搜索名称和描述）
     * @return 模板列表
     */
    List<MonitorRiskImageTemplate> listByKeyword(@Param("keyword") String keyword);

    /**
     * 检查模板名称是否已存在
     *
     * @param name 模板名称
     * @param id 排除的ID（用于更新操作时验证）
     * @return 计数
     */
    Integer checkNameExists(@Param("name") String name, @Param("id") Integer id);
} 