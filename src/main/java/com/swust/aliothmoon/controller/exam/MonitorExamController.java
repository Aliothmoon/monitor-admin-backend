package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.*;
import com.swust.aliothmoon.model.exam.ExamCreateDTO;
import com.swust.aliothmoon.model.exam.ExamQueryDTO;
import com.swust.aliothmoon.model.exam.ExamUpdateDTO;
import com.swust.aliothmoon.model.exam.ExamVO;
import com.swust.aliothmoon.model.vo.ExamineeAccountWithInfoVO;
import com.swust.aliothmoon.service.*;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorExamTableDef.MONITOR_EXAM;

/**
 * 考试管理控制器
 *
 * @author Aliothmoon
 *
 */
@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class MonitorExamController {

    public static final String chars = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghjklmnopqrstuvwxyz123456789";
    private final MonitorExamService examService;
    private final MonitorExamProcessService examProcessService;
    private final MonitorExamDomainService examDomainService;
    private final MonitorExamRiskImageService examRiskImageService;
    private final ExamineeInfoService examineeInfoService;
    private final ExamineeAccountService examineeAccountService;

    @NotNull
    public static String getRandomPassword(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 分页查询考试列表
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<ExamVO>> page(@RequestBody ExamQueryDTO queryDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create();

        // 添加查询条件
        if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().isEmpty()) {
            queryWrapper.and(MONITOR_EXAM.NAME.like(queryDTO.getKeyword())
                    .or(MONITOR_EXAM.DESCRIPTION.like(queryDTO.getKeyword())));
        }

        if (queryDTO.getStatus() != null) {
            queryWrapper.and(MONITOR_EXAM.STATUS.eq(queryDTO.getStatus()));
        }

        if (queryDTO.getStartTime() != null) {
            queryWrapper.and(MONITOR_EXAM.START_TIME.ge(queryDTO.getStartTime()));
        }

        if (queryDTO.getEndTime() != null) {
            queryWrapper.and(MONITOR_EXAM.END_TIME.le(queryDTO.getEndTime()));
        }

        // 按开始时间降序排序
        queryWrapper.orderBy(MONITOR_EXAM.START_TIME.desc());

        // 分页查询
        Page<MonitorExam> page = examService.page(
                new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                queryWrapper
        );

        // 转换为VO
        Page<ExamVO> result = new Page<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalRow(page.getTotalRow());
        result.setTotalPage(page.getTotalPage());

        List<ExamVO> records = TransferUtils.toList(page.getRecords(), ExamVO.class);

        // 遍历每个考试，获取关联数据
        for (ExamVO exam : records) {
            // 设置可疑进程ID列表
            List<MonitorExamProcess> processList = examProcessService.listByExamId(exam.getId());
            if (processList != null && !processList.isEmpty()) {
                exam.setSuspiciousProcessIds(processList.stream()
                        .map(MonitorExamProcess::getProcessId)
                        .collect(Collectors.toList()));
            }

            // 设置域名黑名单ID列表
            List<MonitorExamDomain> domainList = examDomainService.listByExamId(exam.getId());
            if (domainList != null && !domainList.isEmpty()) {
                exam.setBlacklistDomainIds(domainList.stream()
                        .map(MonitorExamDomain::getDomainId)
                        .collect(Collectors.toList()));
            }

            // 设置风险图片模板ID列表
            List<MonitorExamRiskImage> riskImageList = examRiskImageService.listByExamId(exam.getId());
            if (riskImageList != null && !riskImageList.isEmpty()) {
                exam.setRiskImageIds(riskImageList.stream()
                        .map(MonitorExamRiskImage::getRiskImageId)
                        .collect(Collectors.toList()));
            }
        }

        result.setRecords(records);

        return R.ok(result);
    }

    /**
     * 根据ID查询考试
     *
     * @param id 考试ID
     * @return 考试信息
     */
    @GetMapping("/{id}")
    public R<ExamVO> getById(@PathVariable Integer id) {
        MonitorExam exam = examService.getById(id);
        if (exam == null) {
            return R.failed("考试不存在");
        }
        ExamVO vo = TransferUtils.to(exam, ExamVO.class);

        // 设置可疑进程ID列表
        List<MonitorExamProcess> processList = examProcessService.listByExamId(id);
        if (processList != null && !processList.isEmpty()) {
            vo.setSuspiciousProcessIds(processList.stream()
                    .map(MonitorExamProcess::getProcessId)
                    .collect(Collectors.toList()));
        }

        // 设置域名黑名单ID列表
        List<MonitorExamDomain> domainList = examDomainService.listByExamId(id);
        if (domainList != null && !domainList.isEmpty()) {
            vo.setBlacklistDomainIds(domainList.stream()
                    .map(MonitorExamDomain::getDomainId)
                    .collect(Collectors.toList()));
        }

        // 设置风险图片模板ID列表
        List<MonitorExamRiskImage> riskImageList = examRiskImageService.listByExamId(id);
        if (riskImageList != null && !riskImageList.isEmpty()) {
            vo.setRiskImageIds(riskImageList.stream()
                    .map(MonitorExamRiskImage::getRiskImageId)
                    .collect(Collectors.toList()));
        }

        return R.ok(vo);
    }

    /**
     * 创建考试
     *
     * @param createDTO 创建信息
     * @return 创建结果
     */
    @PostMapping
    @Transactional
    public R<Boolean> create(@RequestBody ExamCreateDTO createDTO) {
        // 验证开始时间必须早于结束时间
        if (createDTO.getStartTime().isAfter(createDTO.getEndTime())) {
            return R.failed("开始时间必须早于结束时间");
        }

        MonitorExam exam = new MonitorExam();
        exam.setName(createDTO.getName());
        exam.setDescription(createDTO.getDescription());
        exam.setStartTime(createDTO.getStartTime());
        exam.setEndTime(createDTO.getEndTime());
        exam.setDuration(createDTO.getDuration());
        exam.setLocation(createDTO.getLocation());

        // 根据当前时间和考试时间设置状态
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(createDTO.getStartTime())) {
            exam.setStatus(0); // 未开始
        } else if (now.isAfter(createDTO.getEndTime())) {
            exam.setStatus(2); // 已结束
        } else {
            exam.setStatus(1); // 进行中
        }

        // 设置创建者和创建时间
        exam.setCreatedAt(now);
        exam.setUpdatedAt(now);
        exam.setCreatedBy(UserInfoContext.get().getUserId());
        exam.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = examService.save(exam);
        if (!success) {
            return R.failed("创建失败");
        }

        // 保存可疑进程关联
        if (createDTO.getSuspiciousProcessIds() != null && !createDTO.getSuspiciousProcessIds().isEmpty()) {
            List<MonitorExamProcess> processList = new ArrayList<>();
            for (Integer processId : createDTO.getSuspiciousProcessIds()) {
                MonitorExamProcess examProcess = new MonitorExamProcess();
                examProcess.setExamId(exam.getId());
                examProcess.setProcessId(processId);
                examProcess.setCreatedAt(now);
                processList.add(examProcess);
            }
            examProcessService.saveBatch(processList);
        }

        // 保存域名黑名单关联
        if (createDTO.getBlacklistDomainIds() != null && !createDTO.getBlacklistDomainIds().isEmpty()) {
            List<MonitorExamDomain> domainList = new ArrayList<>();
            for (Integer domainId : createDTO.getBlacklistDomainIds()) {
                MonitorExamDomain examDomain = new MonitorExamDomain();
                examDomain.setExamId(exam.getId());
                examDomain.setDomainId(domainId);
                examDomain.setCreatedAt(now);
                domainList.add(examDomain);
            }
            examDomainService.saveBatch(domainList);
        }

        // 保存风险图片模板关联
        if (createDTO.getRiskImageIds() != null && !createDTO.getRiskImageIds().isEmpty()) {
            List<MonitorExamRiskImage> riskImageList = new ArrayList<>();
            for (Integer riskImageId : createDTO.getRiskImageIds()) {
                MonitorExamRiskImage examRiskImage = new MonitorExamRiskImage();
                examRiskImage.setExamId(exam.getId());
                examRiskImage.setRiskImageId(riskImageId);
                examRiskImage.setCreatedAt(now);
                riskImageList.add(examRiskImage);
            }
            examRiskImageService.saveBatch(riskImageList);
        }

        return R.ok(true);
    }

    /**
     * 更新考试
     *
     * @param updateDTO 更新信息
     * @return 更新结果
     */
    @PutMapping
    @Transactional
    public R<Boolean> update(@RequestBody ExamUpdateDTO updateDTO) {
        MonitorExam exam = examService.getById(updateDTO.getId());
        if (exam == null) {
            return R.failed("考试不存在");
        }

//        // 已结束的考试不允许修改
//        if (exam.getStatus() == 2) {
//            return R.failed("已结束的考试不允许修改");
//        }

        // 验证开始时间必须早于结束时间
        if (updateDTO.getStartTime().isAfter(updateDTO.getEndTime())) {
            return R.failed("开始时间必须早于结束时间");
        }

        exam.setName(updateDTO.getName());
        exam.setDescription(updateDTO.getDescription());
        exam.setStartTime(updateDTO.getStartTime());
        exam.setEndTime(updateDTO.getEndTime());
        exam.setDuration(updateDTO.getDuration());
        exam.setLocation(updateDTO.getLocation());

        // 重新计算考试状态
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(updateDTO.getStartTime())) {
            exam.setStatus(0); // 未开始
        } else if (now.isAfter(updateDTO.getEndTime())) {
            exam.setStatus(2); // 已结束
        } else {
            exam.setStatus(1); // 进行中
        }

        // 设置更新者和更新时间
        exam.setUpdatedAt(LocalDateTime.now());
        exam.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = examService.updateById(exam);
        if (!success) {
            return R.failed("更新失败");
        }

        // 删除旧的关联关系
        examProcessService.removeByExamId(exam.getId());
        examDomainService.removeByExamId(exam.getId());
        examRiskImageService.removeByExamId(exam.getId());

        // 保存可疑进程关联
        if (updateDTO.getSuspiciousProcessIds() != null && !updateDTO.getSuspiciousProcessIds().isEmpty()) {
            List<MonitorExamProcess> processList = new ArrayList<>();
            for (Integer processId : updateDTO.getSuspiciousProcessIds()) {
                MonitorExamProcess examProcess = new MonitorExamProcess();
                examProcess.setExamId(exam.getId());
                examProcess.setProcessId(processId);
                examProcess.setCreatedAt(now);
                processList.add(examProcess);
            }
            examProcessService.saveBatch(processList);
        }

        // 保存域名黑名单关联
        if (updateDTO.getBlacklistDomainIds() != null && !updateDTO.getBlacklistDomainIds().isEmpty()) {
            List<MonitorExamDomain> domainList = new ArrayList<>();
            for (Integer domainId : updateDTO.getBlacklistDomainIds()) {
                MonitorExamDomain examDomain = new MonitorExamDomain();
                examDomain.setExamId(exam.getId());
                examDomain.setDomainId(domainId);
                examDomain.setCreatedAt(now);
                domainList.add(examDomain);
            }
            examDomainService.saveBatch(domainList);
        }

        // 保存风险图片模板关联
        if (updateDTO.getRiskImageIds() != null && !updateDTO.getRiskImageIds().isEmpty()) {
            List<MonitorExamRiskImage> riskImageList = new ArrayList<>();
            for (Integer riskImageId : updateDTO.getRiskImageIds()) {
                MonitorExamRiskImage examRiskImage = new MonitorExamRiskImage();
                examRiskImage.setExamId(exam.getId());
                examRiskImage.setRiskImageId(riskImageId);
                examRiskImage.setCreatedAt(now);
                riskImageList.add(examRiskImage);
            }
            examRiskImageService.saveBatch(riskImageList);
        }

        return R.ok(true);
    }

    /**
     * 删除考试
     *
     * @param id 考试ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Transactional
    public R<Boolean> delete(@PathVariable Integer id) {
        MonitorExam exam = examService.getById(id);
        if (exam == null) {
            return R.failed("考试不存在");
        }

        // 进行中的考试不允许删除
        if (exam.getStatus() == 1) {
            return R.failed("进行中的考试不允许删除");
        }

        // 删除关联关系
        examProcessService.removeByExamId(id);
        examDomainService.removeByExamId(id);
        examRiskImageService.removeByExamId(id);

        boolean success = examService.removeById(id);
        return success ? R.ok(true) : R.failed("删除失败");
    }

    /**
     * 获取考试考生列表
     *
     * @param examId 考试ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param studentId 学号（可选）
     * @param name 姓名（可选）
     * @param college 学院（可选）
     * @return 考生列表分页数据
     */
    @GetMapping("/examinees")
    public R<Page<ExamineeAccountWithInfoVO>> getExamExaminees(
            @RequestParam Integer examId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String college) {

        // 检查考试是否存在
        MonitorExam exam = examService.getById(examId);
        if (exam == null) {
            return R.failed("考试不存在");
        }

        // 获取考试的考生账号列表
        Page<ExamineeAccountWithInfoVO> page = examineeAccountService.getAllAccountsWithInfoByExamId(
                pageNum, pageSize, null, examId, name, studentId, college, null);

        return R.ok(page);
    }

    /**
     * 添加考生到考试
     *
     * @param requestMap 包含examId和examineeInfoId的请求体
     * @return 操作结果
     */
    @PostMapping("/examinees/add")
    @Transactional
    public R<Boolean> addExamineeToExam(@RequestBody Map<String, Integer> requestMap) {
        Integer examId = requestMap.get("examId");
        Integer examineeInfoId = requestMap.get("examineeInfoId");

        if (examId == null || examineeInfoId == null) {
            return R.failed("考试ID和考生信息ID不能为空");
        }

        // 检查考试是否存在
        MonitorExam exam = examService.getById(examId);
        if (exam == null) {
            return R.failed("考试不存在");
        }

        // 检查考生信息是否存在
        ExamineeInfo examineeInfo = examineeInfoService.getById(examineeInfoId);
        if (examineeInfo == null) {
            return R.failed("考生信息不存在");
        }

        // 检查是否已经有账号
        List<ExamineeAccount> existingAccounts = examineeAccountService.getByExamineeInfoIdAndExamId(examineeInfoId, examId);
        if (!existingAccounts.isEmpty()) {
            // 已经有账号，直接返回成功
            return R.ok(true, "该考生已经在此考试中注册");
        }

        // 创建考生账号
        String account = generateAccountFromStudentId(examineeInfo.getStudentId());
        String password = generateRandomPassword(8); // 生成6位随机密码

        ExamineeAccount examineeAccount = new ExamineeAccount();
        examineeAccount.setExamineeInfoId(examineeInfoId);
        examineeAccount.setExamId(examId);
        examineeAccount.setAccount(account);
        examineeAccount.setPassword(password);
        examineeAccount.setStatus(1); // 未登录状态
        examineeAccount.setCreatedAt(LocalDateTime.now());
        examineeAccount.setUpdatedAt(LocalDateTime.now());
        examineeAccount.setCreatedBy(UserInfoContext.get().getUserId());
        examineeAccount.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = examineeAccountService.save(examineeAccount);
        return R.ok(success);
    }

    /**
     * 从考试中移除考生
     *
     * @param examId 考试ID
     * @param accountId 考生账号ID
     * @return 操作结果
     */
    @DeleteMapping("/examinees/remove/{examId}/{accountId}")
    @Transactional
    public R<Boolean> removeExamineeFromExam(@PathVariable Integer examId, @PathVariable Integer accountId) {
        // 检查考试是否存在
        MonitorExam exam = examService.getById(examId);
        if (exam == null) {
            return R.failed("考试不存在");
        }

        // 检查账号是否存在
        ExamineeAccount account = examineeAccountService.getById(accountId);
        if (account == null) {
            return R.failed("考生账号不存在");
        }

        // 检查账号是否属于该考试
        if (!account.getExamId().equals(examId)) {
            return R.failed("该账号不属于该考试");
        }

        // 删除考生账号
        boolean success = examineeAccountService.removeById(accountId);
        return R.ok(success);
    }

    /**
     * 更新考生账号信息
     *
     * @param requestMap 包含账号信息的请求体
     * @return 操作结果
     */
    @PutMapping("/examinees/account")
    @Transactional
    public R<Boolean> updateExamExamineeAccount(@RequestBody Map<String, Object> requestMap) {
        // 根据请求参数确定更新类型
        if (requestMap.containsKey("id")) {
            // 更新现有账号
            Integer accountId = (Integer) requestMap.get("id");
            String account = (String) requestMap.get("account");
            String password = (String) requestMap.get("password");

            ExamineeAccount examineeAccount = examineeAccountService.getById(accountId);
            if (examineeAccount == null) {
                return R.failed("考生账号不存在");
            }

            if (account != null && !account.isEmpty()) {
                examineeAccount.setAccount(account);
            }

            if (password != null && !password.isEmpty()) {
                examineeAccount.setPassword(password);
            }

            examineeAccount.setUpdatedAt(LocalDateTime.now());
            examineeAccount.setUpdatedBy(UserInfoContext.get().getUserId());

            boolean success = examineeAccountService.updateById(examineeAccount);
            return R.ok(success);

        } else if (requestMap.containsKey("examineeInfoId") && requestMap.containsKey("examId")) {
            // 为考生创建新账号
            Integer examineeInfoId = (Integer) requestMap.get("examineeInfoId");
            Integer examId = (Integer) requestMap.get("examId");
            String account = (String) requestMap.get("account");
            String password = (String) requestMap.get("password");

            // 检查考试是否存在
            MonitorExam exam = examService.getById(examId);
            if (exam == null) {
                return R.failed("考试不存在");
            }

            // 检查考生信息是否存在
            ExamineeInfo examineeInfo = examineeInfoService.getById(examineeInfoId);
            if (examineeInfo == null) {
                return R.failed("考生信息不存在");
            }

            // 检查是否已经有账号
            List<ExamineeAccount> existingAccounts = examineeAccountService.getByExamineeInfoIdAndExamId(examineeInfoId, examId);
            if (!existingAccounts.isEmpty()) {
                // 更新现有账号
                ExamineeAccount existingAccount = existingAccounts.get(0);
                if (account != null && !account.isEmpty()) {
                    existingAccount.setAccount(account);
                }

                if (password != null && !password.isEmpty()) {
                    existingAccount.setPassword(password);
                }

                existingAccount.setUpdatedAt(LocalDateTime.now());
                existingAccount.setUpdatedBy(UserInfoContext.get().getUserId());

                boolean success = examineeAccountService.updateById(existingAccount);
                return R.ok(success);
            } else {
                // 创建新账号
                ExamineeAccount examineeAccount = new ExamineeAccount();
                examineeAccount.setExamineeInfoId(examineeInfoId);
                examineeAccount.setExamId(examId);
                examineeAccount.setAccount(account);
                examineeAccount.setPassword(password);
                examineeAccount.setStatus(1); // 未登录状态
                examineeAccount.setCreatedAt(LocalDateTime.now());
                examineeAccount.setUpdatedAt(LocalDateTime.now());
                examineeAccount.setCreatedBy(UserInfoContext.get().getUserId());
                examineeAccount.setUpdatedBy(UserInfoContext.get().getUserId());

                boolean success = examineeAccountService.save(examineeAccount);
                return R.ok(success);
            }
        }

        return R.failed("请求参数不正确");
    }

    /**
     * 手动导入考生信息
     *
     * @param file 考生信息Excel文件
     * @param examId 考试ID
     * @return 导入结果
     */
    @PostMapping("/examinees/manual-import")
    @Transactional
    public R<Map<String, Object>> manualImportExaminees(@RequestParam("file") MultipartFile file, @RequestParam("examId") Integer examId) {
        if (file.isEmpty()) {
            return R.failed("上传文件为空");
        }

        // 检查考试是否存在
        MonitorExam exam = examService.getById(examId);
        if (exam == null) {
            return R.failed("考试不存在");
        }

        try {
            // 解析Excel文件并导入考生
            List<Map<String, Object>> importResult = examineeInfoService.importExamineesFromExcel(file, examId);

            Map<String, Object> result = new HashMap<>();
            result.put("total", importResult.size());
            result.put("details", importResult);

            return R.ok(result);
        } catch (IOException e) {
            return R.failed("文件解析失败: " + e.getMessage());
        } catch (Exception e) {
            return R.failed("导入失败: " + e.getMessage());
        }
    }

    /**
     * 下载考生导入模板
     *
     * @return 模板文件
     */
    @GetMapping("/examinees/template")
    public void downloadExamineeTemplate() {
        // 实际实现应该是直接返回一个文件流
        // 这里留空，在服务层实现
        examineeInfoService.generateAndDownloadTemplate();
    }

    /**
     * 导出考试考生名单
     *
     * @param examId 考试ID
     */
    @GetMapping("/examinees/export/{examId}")
    public void exportExaminees(@PathVariable Integer examId) {
        // 验证考试是否存在
        MonitorExam exam = examService.getById(examId);
        if (exam == null) {
            throw new RuntimeException("考试不存在");
        }

        // 导出考生名单
        examineeInfoService.exportExamineesInfo(examId, exam.getName(), exam.getLocation());
    }

    /**
     * 从学号生成默认账号
     */
    private String generateAccountFromStudentId(String studentId) {
        // 简单实现：使用学号作为账号
        return studentId;
    }

    /**
     * 生成随机密码
     */
    private String generateRandomPassword(int length) {
        return getRandomPassword(length);
    }
} 