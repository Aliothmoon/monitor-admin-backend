package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.model.vo.ExamineeAccountWithInfoVO;

import java.util.List;

/**
 * 考生账号服务接口
 *
 * @author Alioth
 *
 */
public interface ExamineeAccountService extends IService<ExamineeAccount> {

    /**
     * 获取分页数据
     *
     * @param page 分页参数
     * @return 分页数据
     */
    TableDataInfo<ExamineeAccount> getPageData(PageInfo page);

    /**
     * 根据条件获取分页数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param account 账号（可选）
     * @param examId 考试ID（可选）
     * @param examineeInfoId 考生信息ID（可选）
     * @return 分页数据
     */
    TableDataInfo<ExamineeAccount> getPageData(int pageNum, int pageSize, String account, Integer examId, Integer examineeInfoId);

    /**
     * 获取考生账号与考生信息关联的综合数据分页列表
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param account 账号（可选）
     * @param examId 考试ID（可选）
     * @param name 考生姓名（可选）
     * @param studentId 学号（可选）
     * @param college 学院（可选）
     * @param className 班级（可选）
     * @return 考生账号与考生信息的综合数据分页信息
     */
    TableDataInfo<ExamineeAccountWithInfoVO> getAllAccountsWithInfo(int pageNum, int pageSize, String account, Integer examId, String name, String studentId, String college, String className);

    /**
     * 根据考生信息ID获取所有账号
     *
     * @param examineeInfoId 考生信息ID
     * @return 账号列表
     */
    List<ExamineeAccount> getByExamineeInfoId(Integer examineeInfoId);

    /**
     * 根据考试ID获取所有账号
     *
     * @param examId 考试ID
     * @return 账号列表
     */
    List<ExamineeAccount> getByExamId(Integer examId);
} 