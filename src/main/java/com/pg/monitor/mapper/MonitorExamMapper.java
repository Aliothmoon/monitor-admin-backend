package com.pg.monitor.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.pg.monitor.model.entity.MonitorExam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 考试 Mapper 接口
 */
@Mapper
public interface MonitorExamMapper extends BaseMapper<MonitorExam> {
    
    /**
     * 分页查询考试列表
     *
     * @param page 分页参数
     * @param name 考试名称
     * @param status 考试状态
     * @return 分页结果
     */
    Page<MonitorExam> selectExamPage(Page<MonitorExam> page, @Param("name") String name, @Param("status") Integer status);
} 