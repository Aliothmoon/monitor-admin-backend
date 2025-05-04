package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorExam;
import com.swust.aliothmoon.entity.MonitorExamDomain;
import com.swust.aliothmoon.entity.MonitorExamProcess;
import com.swust.aliothmoon.entity.MonitorExamRiskImage;
import com.swust.aliothmoon.model.exam.ExamCreateDTO;
import com.swust.aliothmoon.model.exam.ExamQueryDTO;
import com.swust.aliothmoon.model.exam.ExamUpdateDTO;
import com.swust.aliothmoon.model.exam.ExamVO;
import com.swust.aliothmoon.service.MonitorExamDomainService;
import com.swust.aliothmoon.service.MonitorExamProcessService;
import com.swust.aliothmoon.service.MonitorExamRiskImageService;
import com.swust.aliothmoon.service.MonitorExamService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorExamTableDef.MONITOR_EXAM;
import static com.swust.aliothmoon.entity.table.MonitorExamProcessTableDef.MONITOR_EXAM_PROCESS;
import static com.swust.aliothmoon.entity.table.MonitorExamDomainTableDef.MONITOR_EXAM_DOMAIN;
import static com.swust.aliothmoon.entity.table.MonitorExamRiskImageTableDef.MONITOR_EXAM_RISK_IMAGE;

/**
 * 考试管理控制器
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class MonitorExamController {

    private final MonitorExamService examService;
    private final MonitorExamProcessService examProcessService;
    private final MonitorExamDomainService examDomainService;
    private final MonitorExamRiskImageService examRiskImageService;

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
} 