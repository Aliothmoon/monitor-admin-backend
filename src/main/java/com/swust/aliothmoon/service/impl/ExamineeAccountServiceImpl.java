package com.swust.aliothmoon.service.impl;

import com.github.ksuid.Ksuid;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.entity.MonitorExam;
import com.swust.aliothmoon.entity.table.ExamineeAccountTableDef;
import com.swust.aliothmoon.entity.table.ExamineeInfoTableDef;
import com.swust.aliothmoon.mapper.ExamineeAccountMapper;
import com.swust.aliothmoon.model.dto.ExamineeLoginDTO;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.model.vo.ExamDetailsVO;
import com.swust.aliothmoon.model.vo.ExamineeAccountWithInfoVO;
import com.swust.aliothmoon.model.vo.ExamineeInfoVO;
import com.swust.aliothmoon.model.vo.ExamineeLoginVO;
import com.swust.aliothmoon.service.ExamineeAccountService;
import com.swust.aliothmoon.service.ExamineeInfoService;
import com.swust.aliothmoon.service.MonitorExamService;
import com.swust.aliothmoon.utils.CryptoUtils;
import com.swust.aliothmoon.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.swust.aliothmoon.constant.Keys.CANDIDATE_TOKEN;

/**
 * 考生账号服务实现类
 *
 * @author Aliothmoon
 *
 */
@Service
@RequiredArgsConstructor
public class ExamineeAccountServiceImpl extends ServiceImpl<ExamineeAccountMapper, ExamineeAccount> implements ExamineeAccountService {

    private final ExamineeAccountMapper examineeAccountMapper;
    @Lazy
    @Autowired
    private ExamineeInfoService examineeInfoService;
    @Lazy
    @Autowired
    private MonitorExamService examService;

    @Override
    public String generateAccountByStudentId(Integer examId, String studentId) {
        return Integer.toOctalString(examId) + studentId;
    }

    @Override
    public ExamineeAccount validateLogin(String account, String password) {
        if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
            return null;
        }

        ExamineeAccountTableDef examineeAccount = ExamineeAccountTableDef.EXAMINEE_ACCOUNT;

        // 根据账号查询用户
        ExamineeAccount accountEntity = QueryChain.of(ExamineeAccount.class)
                .where(examineeAccount.ACCOUNT.eq(account))
                .one();

        if (accountEntity == null) {
            return null;
        }

        // 验证密码
        if (!Objects.equals(password, accountEntity.getPassword())) {
            return null;
        }

        // 更新最后登录时间
        accountEntity.setLastLoginTime(LocalDateTime.now());
        updateById(accountEntity);

        return accountEntity;
    }

    @Override
    public String generateExamineeToken(ExamineeAccount examineeAccount) {
        if (examineeAccount == null || examineeAccount.getAccountId() == null) {
            return null;
        }

        String key = Ksuid.newKsuid().asRaw();
        RedisUtils.set(CANDIDATE_TOKEN.apply(key), examineeAccount,1, TimeUnit.DAYS);

        return key;
    }

    @Override
    public ExamineeLoginVO login(ExamineeLoginDTO loginDTO) {
        if (loginDTO == null) {
            return null;
        }

        String account = loginDTO.getAccount();
        String password = loginDTO.getPassword();

        if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
            return null;
        }

        // 验证账号和密码
        ExamineeAccount examineeAccount = validateLogin(account, password);
        if (examineeAccount == null) {
            return null;
        }

        // 验证账号状态
        if (examineeAccount.getStatus() != 1) {
            return null;
        }

        // 生成考生token
        String token = generateExamineeToken(examineeAccount);

        // 返回登录信息
        ExamineeLoginVO loginVO = new ExamineeLoginVO();
        loginVO.setToken(token);
        loginVO.setAccountId(examineeAccount.getAccountId());
        loginVO.setExamId(examineeAccount.getExamId());

        return loginVO;
    }

    @Override
    public ExamineeInfoVO getExamineeInfo(Integer accountId) {
        if (accountId == null) {
            return null;
        }

        // 获取考生账号信息
        ExamineeAccount account = getById(accountId);
        if (account == null) {
            return null;
        }

        // 获取考生个人信息
        ExamineeInfo examineeInfo = null;
        if (account.getExamineeInfoId() != null) {
            examineeInfo = examineeInfoService.getById(account.getExamineeInfoId());
        }

        // 组装返回信息
        ExamineeInfoVO infoVO = new ExamineeInfoVO();
        infoVO.setAccount(account);
        infoVO.setExamId(account.getExamId());
        infoVO.setExamineeInfo(examineeInfo);
        
        // 获取考试详情
        if (account.getExamId() != null) {
            MonitorExam exam = examService.getById(account.getExamId());
            if (exam != null) {
                ExamDetailsVO examDetailsVO = new ExamDetailsVO();
                examDetailsVO.setId(exam.getId());
                examDetailsVO.setName(exam.getName());
                examDetailsVO.setDescription(exam.getDescription());
                examDetailsVO.setStartTime(exam.getStartTime());
                examDetailsVO.setEndTime(exam.getEndTime());
                examDetailsVO.setDuration(exam.getDuration());
                examDetailsVO.setLocation(exam.getLocation());
                examDetailsVO.setStatus(exam.getStatus());
                
                // 设置当前服务器时间
                LocalDateTime now = LocalDateTime.now();
                examDetailsVO.setServerTime(now);
                
                // 计算考试剩余时间（秒）
                if (now.isBefore(exam.getEndTime())) {
                    long remainingSeconds = java.time.Duration.between(now, exam.getEndTime()).getSeconds();
                    examDetailsVO.setRemainingTime(remainingSeconds);
                } else {
                    examDetailsVO.setRemainingTime(0L);
                }
                
                infoVO.setExamDetails(examDetailsVO);
            }
        }

        return infoVO;
    }

    @Override
    public boolean createExamineeAccount(ExamineeAccount examineeAccount, Integer operatorId) {
        if (examineeAccount == null || operatorId == null) {
            return false;
        }

        // 设置创建和更新时间
        LocalDateTime now = LocalDateTime.now();
        examineeAccount.setCreatedAt(now);
        examineeAccount.setUpdatedAt(now);

        // 设置创建者和更新者
        examineeAccount.setCreatedBy(operatorId);
        examineeAccount.setUpdatedBy(operatorId);

        // 设置默认状态为启用
        if (examineeAccount.getStatus() == null) {
            examineeAccount.setStatus(1);
        }

        // 加密密码
        examineeAccount.setPassword(CryptoUtils.hashPassword(examineeAccount.getPassword()));

        return save(examineeAccount);
    }

    @Override
    public boolean updateExamineeAccount(ExamineeAccount examineeAccount, Integer operatorId) {
        if (examineeAccount == null || operatorId == null) {
            return false;
        }

        // 设置更新时间和更新者
        examineeAccount.setUpdatedAt(LocalDateTime.now());
        examineeAccount.setUpdatedBy(operatorId);

        // 如果密码不为空，则加密密码
        if (StringUtils.hasText(examineeAccount.getPassword())) {
            examineeAccount.setPassword(CryptoUtils.hashPassword(examineeAccount.getPassword()));
        } else {
            // 如果密码为空，不更新密码字段
            examineeAccount.setPassword(null);
        }

        return updateById(examineeAccount);
    }

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
            queryChain.where(examineeAccount.ACCOUNT.like(account));
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
            queryWrapper.and(examineeAccount.ACCOUNT.like(account));
        }

        if (examId != null) {
            queryWrapper.and(examineeAccount.EXAM_ID.eq(examId));
        }

        if (StringUtils.hasText(name)) {
            queryWrapper.and(examineeInfo.NAME.like(name));
        }

        if (StringUtils.hasText(studentId)) {
            queryWrapper.and(examineeInfo.STUDENT_ID.like(studentId));
        }

        if (StringUtils.hasText(college)) {
            queryWrapper.and(examineeInfo.COLLEGE.like(college));
        }

        if (StringUtils.hasText(className)) {
            queryWrapper.and(examineeInfo.CLASS_NAME.like(className));
        }

        // 按创建时间排序
        queryWrapper.orderBy(examineeAccount.CREATED_AT.desc());

        // 执行分页查询
        Page<ExamineeAccountWithInfoVO> page = new Page<>(pageNum, pageSize);
        Page<ExamineeAccountWithInfoVO> result = this.pageAs(page, queryWrapper, ExamineeAccountWithInfoVO.class);

        return TableDataInfo.of(result);
    }

    @Override
    public Page<ExamineeAccountWithInfoVO> getAllAccountsWithInfoByExamId(int pageNum, int pageSize, String account, Integer examId, String name, String studentId, String college, String className) {
        ExamineeAccountTableDef examineeAccount = ExamineeAccountTableDef.EXAMINEE_ACCOUNT;
        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;

        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(
                        examineeAccount.ACCOUNT_ID, // 映射为id，方便前端展示
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

        // 添加考试ID查询条件（必需）
        queryWrapper.and(examineeAccount.EXAM_ID.eq(examId));

        queryWrapper.and(wrapper -> {
            // 添加其他查询条件（可选）
            if (StringUtils.hasText(account)) {
                wrapper.or(examineeAccount.ACCOUNT.like(account));
            }

            if (StringUtils.hasText(name)) {
                wrapper.or(examineeInfo.NAME.like(name));
            }

            if (StringUtils.hasText(studentId)) {
                wrapper.or(examineeInfo.STUDENT_ID.like(studentId));
            }

            if (StringUtils.hasText(college)) {
                wrapper.or(examineeInfo.COLLEGE.like(college));
            }

            if (StringUtils.hasText(className)) {
                wrapper.or(examineeInfo.CLASS_NAME.like(className));
            }
        }, true);


        // 按创建时间排序
        queryWrapper.orderBy(examineeAccount.CREATED_AT.desc());

        // 执行分页查询
        Page<ExamineeAccountWithInfoVO> page = new Page<>(pageNum, pageSize);
        return this.pageAs(page, queryWrapper, ExamineeAccountWithInfoVO.class);
    }

    @Override
    public List<ExamineeAccount> getByExamineeInfoIdAndExamId(Integer examineeInfoId, Integer examId) {
        ExamineeAccountTableDef examineeAccount = ExamineeAccountTableDef.EXAMINEE_ACCOUNT;

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(examineeAccount.EXAMINEE_INFO_ID.eq(examineeInfoId))
                .and(examineeAccount.EXAM_ID.eq(examId));

        return list(queryWrapper);
    }
} 