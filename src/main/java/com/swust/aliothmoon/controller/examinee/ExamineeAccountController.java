package com.swust.aliothmoon.controller.examinee;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.model.dto.ExamineeLoginDTO;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.model.vo.ExamineeAccountWithInfoVO;
import com.swust.aliothmoon.model.vo.ExamineeInfoVO;
import com.swust.aliothmoon.model.vo.ExamineeLoginVO;
import com.swust.aliothmoon.service.ExamineeAccountService;
import com.swust.aliothmoon.service.ExamineeInfoService;
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
    private final ExamineeInfoService examineeInfoService;

    /**
     * 考生专用登录接口
     *
     * @param loginDTO 登录数据
     * @return 登录结果
     */
    @PostMapping("/login")
    public R<ExamineeLoginVO> login(@RequestBody ExamineeLoginDTO loginDTO) {
        // 参数校验
        String account = loginDTO.getAccount();
        String password = loginDTO.getPassword();
        if (account == null || account.isEmpty() || password == null || password.isEmpty()) {
            return R.failed("账号或密码不能为空");
        }
        
        // 调用服务处理登录逻辑
        ExamineeLoginVO loginVO = examineeAccountService.login(loginDTO);
        if (loginVO == null) {
            return R.failed("账号或密码错误，或账号已被禁用");
        }
        
        return R.ok(loginVO);
    }
    
    /**
     * 获取考生信息
     *
     * @return 考生信息
     */
    @GetMapping("/info")
    public R<ExamineeInfoVO> getExamineeInfo() {
        // 从上下文中获取考生账号ID
        Integer accountId = UserInfoContext.get().getUserId();
        if (accountId == null) {
            return R.failed("未登录");
        }
        
        // 调用服务获取考生信息
        ExamineeInfoVO infoVO = examineeAccountService.getExamineeInfo(accountId);
        if (infoVO == null) {
            return R.failed("账号不存在");
        }
        
        return R.ok(infoVO);
    }

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
        // 获取操作者ID
        Integer userId = UserInfoContext.get().getUserId();
        
        // 如果提交的请求包含考生信息ID
        if (examineeAccount.getExamineeInfoId() != null) {
            // 查询考生信息
            ExamineeInfo examineeInfo = examineeInfoService.getById(examineeAccount.getExamineeInfoId());
            if (examineeInfo != null) {
                // 检查姓名和学号是否重复
                boolean isDuplicate = examineeInfoService.checkDuplicateNameAndStudentId(
                        examineeInfo.getName(), examineeInfo.getStudentId());
                if (isDuplicate) {
                    return R.failed("考生姓名和学号已存在，请勿重复添加");
                }
            }
        }
        
        // 调用服务创建考生账号
        boolean success = examineeAccountService.createExamineeAccount(examineeAccount, userId);
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
        // 获取操作者ID
        Integer userId = UserInfoContext.get().getUserId();
        
        // 如果提交的请求包含考生信息ID
        if (examineeAccount.getExamineeInfoId() != null) {
            // 查询考生信息
            ExamineeInfo examineeInfo = examineeInfoService.getById(examineeAccount.getExamineeInfoId());
            if (examineeInfo != null) {
                // 检查姓名和学号是否重复（排除自身ID）
                boolean isDuplicate = examineeInfoService.checkDuplicateNameAndStudentIdExcludeId(
                        examineeInfo.getName(), 
                        examineeInfo.getStudentId(),
                        examineeInfo.getExamineeInfoId());
                if (isDuplicate) {
                    return R.failed("考生姓名和学号已存在，请勿重复添加");
                }
            }
        }
        
        // 调用服务更新考生账号
        boolean success = examineeAccountService.updateExamineeAccount(examineeAccount, userId);
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