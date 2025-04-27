package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorExam;
import com.swust.aliothmoon.model.exam.ExamCreateDTO;
import com.swust.aliothmoon.model.exam.ExamQueryDTO;
import com.swust.aliothmoon.model.exam.ExamUpdateDTO;
import com.swust.aliothmoon.model.exam.ExamVO;
import com.swust.aliothmoon.service.MonitorExamService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorExamTableDef.MONITOR_EXAM;

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
        return R.ok(vo);
    }

    /**
     * 创建考试
     *
     * @param createDTO 创建信息
     * @return 创建结果
     */
    @PostMapping
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
        return success ? R.ok(true) : R.failed("创建失败");
    }

    /**
     * 更新考试
     *
     * @param updateDTO 更新信息
     * @return 更新结果
     */
    @PutMapping
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
        return success ? R.ok(true) : R.failed("更新失败");
    }

    /**
     * 删除考试
     *
     * @param id 考试ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Integer id) {
        MonitorExam exam = examService.getById(id);
        if (exam == null) {
            return R.failed("考试不存在");
        }

        // 进行中的考试不允许删除
        if (exam.getStatus() == 1) {
            return R.failed("进行中的考试不允许删除");
        }

        boolean success = examService.removeById(id);
        return success ? R.ok(true) : R.failed("删除失败");
    }
} 