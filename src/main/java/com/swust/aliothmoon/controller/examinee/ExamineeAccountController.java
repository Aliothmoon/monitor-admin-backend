package com.swust.aliothmoon.controller.examinee;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.model.vo.ExamineeAccountWithInfoVO;
import com.swust.aliothmoon.service.ExamineeAccountService;
import com.swust.aliothmoon.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考生账号Controller
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/examinee/account")
@RequiredArgsConstructor
public class ExamineeAccountController {

    private final ExamineeAccountService examineeAccountService;

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<ExamineeAccount> page(Page<ExamineeAccount> page) {
        return examineeAccountService.page(page);
    }

    /**
     * 获取分页数据
     *
     * @param page 分页参数
     * @return 分页数据
     */
    @PostMapping("/getPageData")
    public TableDataInfo<ExamineeAccount> getPageData(@RequestBody PageInfo page) {
        return examineeAccountService.getPageData(page);
    }

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
    @GetMapping("/getExamineeAccountPageData")
    public TableDataInfo<ExamineeAccount> getExamineeAccountPageData(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(required = false) String account,
            @RequestParam(required = false) Integer examId,
            @RequestParam(required = false) Integer examineeInfoId) {
        return examineeAccountService.getPageData(pageNum, pageSize, account, examId, examineeInfoId);
    }

    /**
     * 保存考生账号
     *
     * @param examineeAccount 考生账号
     * @return 保存结果
     */
    @PostMapping("/saveExamineeAccount")
    @Transactional
    public R<Boolean> saveExamineeAccount(@RequestBody ExamineeAccount examineeAccount) {
        // 设置创建和更新时间
        LocalDateTime now = LocalDateTime.now();
        examineeAccount.setCreatedAt(now);
        examineeAccount.setUpdatedAt(now);
        // 设置创建者和更新者
        Integer userId = UserInfoContext.get().getUserId();
        examineeAccount.setCreatedBy(userId);
        examineeAccount.setUpdatedBy(userId);
        // 设置默认状态为启用
        if (examineeAccount.getStatus() == null) {
            examineeAccount.setStatus(1);
        }
        // 加密密码
        examineeAccount.setPassword(CryptoUtils.hashPassword(examineeAccount.getPassword()));
        
        boolean success = examineeAccountService.save(examineeAccount);
        return R.ok(success);
    }

    /**
     * 更新考生账号
     *
     * @param examineeAccount 考生账号
     * @return 更新结果
     */
    @PutMapping("/updateExamineeAccount")
    public R<Boolean> updateExamineeAccount(@RequestBody ExamineeAccount examineeAccount) {
        // 设置更新时间和更新者
        examineeAccount.setUpdatedAt(LocalDateTime.now());
        examineeAccount.setUpdatedBy(UserInfoContext.get().getUserId());
        
        // 如果密码不为空，则加密密码
        if (examineeAccount.getPassword() != null && !examineeAccount.getPassword().isEmpty()) {
            examineeAccount.setPassword(CryptoUtils.hashPassword(examineeAccount.getPassword()));
        } else {
            // 如果密码为空，不更新密码字段
            examineeAccount.setPassword(null);
        }
        
        boolean success = examineeAccountService.updateById(examineeAccount);
        return R.ok(success);
    }

    /**
     * 删除考生账号
     *
     * @param accountId 考生账号ID
     * @return 删除结果
     */
    @DeleteMapping("/removeExamineeAccount/{accountId}")
    public R<Boolean> removeExamineeAccount(@PathVariable Integer accountId) {
        boolean success = examineeAccountService.removeById(accountId);
        return R.ok(success);
    }

    /**
     * 根据ID获取考生账号
     *
     * @param accountId 考生账号ID
     * @return 考生账号
     */
    @GetMapping("/getExamineeAccount/{accountId}")
    public R<ExamineeAccount> getExamineeAccount(@PathVariable Integer accountId) {
        ExamineeAccount examineeAccount = examineeAccountService.getById(accountId);
        return R.ok(examineeAccount);
    }

    /**
     * 根据考生信息ID获取所有账号
     *
     * @param examineeInfoId 考生信息ID
     * @return 账号列表
     */
    @GetMapping("/getByExamineeInfoId/{examineeInfoId}")
    public R<List<ExamineeAccount>> getByExamineeInfoId(@PathVariable Integer examineeInfoId) {
        List<ExamineeAccount> accounts = examineeAccountService.getByExamineeInfoId(examineeInfoId);
        return R.ok(accounts);
    }

    /**
     * 根据考试ID获取所有账号
     *
     * @param examId 考试ID
     * @return 账号列表
     */
    @GetMapping("/getByExamId/{examId}")
    public R<List<ExamineeAccount>> getByExamId(@PathVariable Integer examId) {
        List<ExamineeAccount> accounts = examineeAccountService.getByExamId(examId);
        return R.ok(accounts);
    }

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
    @GetMapping("/getAllAccountsWithInfo")
    public TableDataInfo<ExamineeAccountWithInfoVO> getAllAccountsWithInfo(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(required = false) String account,
            @RequestParam(required = false) Integer examId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) String className) {
        return examineeAccountService.getAllAccountsWithInfo(pageNum, pageSize, account, examId, name, studentId, college, className);
    }
} 