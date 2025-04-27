package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.entity.table.ExamineeAccountTableDef;
import com.swust.aliothmoon.entity.table.ExamineeInfoTableDef;
import com.swust.aliothmoon.mapper.ExamineeAccountMapper;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.model.vo.ExamineeAccountWithInfoVO;
import com.swust.aliothmoon.service.ExamineeAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 考生账号服务实现类
 *
 * @author Alioth
 *
 */
@Service
@RequiredArgsConstructor
public class ExamineeAccountServiceImpl extends ServiceImpl<ExamineeAccountMapper, ExamineeAccount> implements ExamineeAccountService {

    private final ExamineeAccountMapper examineeAccountMapper;

    @Override
    public TableDataInfo<ExamineeAccount> getPageData(PageInfo page) {
        Page<ExamineeAccount> p = page(page.toPage());
        return TableDataInfo.of(p);
    }

    @Override
    public TableDataInfo<ExamineeAccount> getPageData(int pageNum, int pageSize, String account, Integer examId, Integer examineeInfoId) {
        ExamineeAccountTableDef examineeAccount = ExamineeAccountTableDef.EXAMINEE_ACCOUNT;
        
        QueryChain<ExamineeAccount> queryChain = QueryChain.of(ExamineeAccount.class);
        
        // 添加查询条件
        if (StringUtils.hasText(account)) {
            queryChain.where(examineeAccount.ACCOUNT.like("%" + account + "%"));
        }
        
        if (examId != null) {
            queryChain.where(examineeAccount.EXAM_ID.eq(examId));
        }
        
        if (examineeInfoId != null) {
            queryChain.where(examineeAccount.EXAMINEE_INFO_ID.eq(examineeInfoId));
        }
        
        // 按创建时间排序
        queryChain.orderBy(examineeAccount.CREATED_AT.desc());
        
        // 执行分页查询
        Page<ExamineeAccount> page = new Page<>(pageNum, pageSize);
        Page<ExamineeAccount> result = page(page, queryChain);
        
        return TableDataInfo.of(result);
    }

    @Override
    public List<ExamineeAccount> getByExamineeInfoId(Integer examineeInfoId) {
        return examineeAccountMapper.getByExamineeInfoId(examineeInfoId);
    }

    @Override
    public List<ExamineeAccount> getByExamId(Integer examId) {
        return examineeAccountMapper.getByExamId(examId);
    }

    @Override
    public TableDataInfo<ExamineeAccountWithInfoVO> getAllAccountsWithInfo(int pageNum, int pageSize, String account, Integer examId, String name, String studentId, String college, String className) {
        ExamineeAccountTableDef examineeAccount = ExamineeAccountTableDef.EXAMINEE_ACCOUNT;
        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;
        
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(
                examineeAccount.ACCOUNT_ID,
                examineeAccount.EXAMINEE_INFO_ID,
                examineeAccount.EXAM_ID,
                examineeAccount.ACCOUNT,
                examineeAccount.STATUS,
                examineeAccount.LAST_LOGIN_TIME,
                examineeAccount.CREATED_AT,
                examineeInfo.STUDENT_ID,
                examineeInfo.NAME,
                examineeInfo.GENDER,
                examineeInfo.COLLEGE,
                examineeInfo.CLASS_NAME,
                examineeInfo.MAJOR,
                examineeInfo.EMAIL,
                examineeInfo.PHONE
            )
            .from(examineeAccount)
            .leftJoin(examineeInfo).on(examineeAccount.EXAMINEE_INFO_ID.eq(examineeInfo.EXAMINEE_INFO_ID));
        
        // 添加查询条件
        if (StringUtils.hasText(account)) {
            queryWrapper.and(examineeAccount.ACCOUNT.like("%" + account + "%"));
        }
        
        if (examId != null) {
            queryWrapper.and(examineeAccount.EXAM_ID.eq(examId));
        }
        
        if (StringUtils.hasText(name)) {
            queryWrapper.and(examineeInfo.NAME.like("%" + name + "%"));
        }
        
        if (StringUtils.hasText(studentId)) {
            queryWrapper.and(examineeInfo.STUDENT_ID.like("%" + studentId + "%"));
        }
        
        if (StringUtils.hasText(college)) {
            queryWrapper.and(examineeInfo.COLLEGE.like("%" + college + "%"));
        }
        
        if (StringUtils.hasText(className)) {
            queryWrapper.and(examineeInfo.CLASS_NAME.like("%" + className + "%"));
        }
        
        // 按创建时间排序
        queryWrapper.orderBy(examineeAccount.CREATED_AT.desc());
        
        // 执行分页查询
        Page<ExamineeAccountWithInfoVO> page = new Page<>(pageNum, pageSize);
        Page<ExamineeAccountWithInfoVO> result = this.pageAs(page, queryWrapper, ExamineeAccountWithInfoVO.class);
        
        return TableDataInfo.of(result);
    }
} 