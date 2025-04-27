package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.model.dto.PageInfo;

/**
 * 考生信息服务接口
 *
 * @author Alioth
 *
 */
public interface ExamineeInfoService extends IService<ExamineeInfo> {

    /**
     * 获取分页数据
     *
     * @param page 分页参数
     * @return 分页数据
     */
    TableDataInfo<ExamineeInfo> getPageData(PageInfo page);

    /**
     * 根据条件获取分页数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param name 姓名（可选）
     * @param studentId 学号（可选）
     * @param college 学院（可选）
     * @param className 班级（可选）
     * @return 分页数据
     */
    TableDataInfo<ExamineeInfo> getPageData(int pageNum, int pageSize, String name, String studentId, String college, String className);
} 