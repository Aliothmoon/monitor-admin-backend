package com.swust.aliothmoon.mapper;

import com.mybatisflex.core.BaseMapper;
import com.swust.aliothmoon.entity.ExamineeAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考生账号 Mapper 接口
 *
 * @author Aliothmoon
 *
 */
@Mapper
public interface ExamineeAccountMapper extends BaseMapper<ExamineeAccount> {

    /**
     * 根据考生信息ID获取所有关联账号
     *
     * @param examineeInfoId 考生信息ID
     * @return 账号列表
     */
    List<ExamineeAccount> getByExamineeInfoId(@Param("examineeInfoId") Integer examineeInfoId);

    /**
     * 根据考试ID获取所有关联账号
     *
     * @param examId 考试ID
     * @return 账号列表
     */
    List<ExamineeAccount> getByExamId(@Param("examId") Integer examId);
} 