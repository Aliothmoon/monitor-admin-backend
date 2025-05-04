package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorSuspiciousProcess;
import com.swust.aliothmoon.model.process.SuspiciousProcessAddDTO;
import com.swust.aliothmoon.model.process.SuspiciousProcessQueryDTO;
import com.swust.aliothmoon.model.process.SuspiciousProcessUpdateDTO;
import com.swust.aliothmoon.model.process.SuspiciousProcessVO;
import com.swust.aliothmoon.service.MonitorSuspiciousProcessService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorSuspiciousProcessTableDef.MONITOR_SUSPICIOUS_PROCESS;

/**
 * 可疑进程名单控制器
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/exam/suspicious-process")
@RequiredArgsConstructor
public class SuspiciousProcessController {

    private final MonitorSuspiciousProcessService suspiciousProcessService;

    /**
     * 分页查询可疑进程名单
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<SuspiciousProcessVO>> page(@RequestBody SuspiciousProcessQueryDTO queryDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create();

        // 添加查询条件
        if (queryDTO.getProcessName() != null && !queryDTO.getProcessName().isEmpty()) {
            queryWrapper.and(MONITOR_SUSPICIOUS_PROCESS.PROCESS_NAME.like(queryDTO.getProcessName()));
        }

        if (queryDTO.getRiskLevel() != null) {
            queryWrapper.and(MONITOR_SUSPICIOUS_PROCESS.RISK_LEVEL.eq(queryDTO.getRiskLevel()));
        }

        // 按创建时间降序排序
        queryWrapper.orderBy(MONITOR_SUSPICIOUS_PROCESS.CREATED_AT.desc());

        // 分页查询
        Page<MonitorSuspiciousProcess> page = suspiciousProcessService.page(
                new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                queryWrapper
        );

        // 转换为VO
        Page<SuspiciousProcessVO> result = new Page<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalRow(page.getTotalRow());
        result.setTotalPage(page.getTotalPage());

        List<SuspiciousProcessVO> records = TransferUtils.toList(page.getRecords(), SuspiciousProcessVO.class);
        result.setRecords(records);

        return R.ok(result);
    }

    /**
     * 根据ID查询可疑进程
     *
     * @param id 进程ID
     * @return 进程信息
     */
    @GetMapping("/{id}")
    public R<SuspiciousProcessVO> getById(@PathVariable Integer id) {
        MonitorSuspiciousProcess process = suspiciousProcessService.getById(id);
        if (process == null) {
            return R.failed("进程不存在");
        }
        SuspiciousProcessVO vo = TransferUtils.to(process, SuspiciousProcessVO.class);
        return R.ok(vo);
    }

    /**
     * 添加可疑进程
     *
     * @param addDTO 添加信息
     * @return 添加结果
     */
    @PostMapping
    public R<Boolean> add(@RequestBody SuspiciousProcessAddDTO addDTO) {
        MonitorSuspiciousProcess process = TransferUtils.to(addDTO, MonitorSuspiciousProcess.class);

        // 设置创建者和创建时间
        LocalDateTime now = LocalDateTime.now();
        process.setCreatedAt(now);
        process.setUpdatedAt(now);
        process.setCreatedBy(UserInfoContext.get().getUserId());
        process.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = suspiciousProcessService.save(process);
        return success ? R.ok(true) : R.failed("添加失败");
    }

    /**
     * 更新可疑进程
     *
     * @param updateDTO 更新信息
     * @return 更新结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody SuspiciousProcessUpdateDTO updateDTO) {
        MonitorSuspiciousProcess process = suspiciousProcessService.getById(updateDTO.getId());
        if (process == null) {
            return R.failed("进程不存在");
        }

        TransferUtils.copyProperties(updateDTO, process);

        // 设置更新者和更新时间
        process.setUpdatedAt(LocalDateTime.now());
        process.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = suspiciousProcessService.updateById(process);
        return success ? R.ok(true) : R.failed("更新失败");
    }

    /**
     * 删除可疑进程
     *
     * @param id 进程ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Integer id) {
        boolean success = suspiciousProcessService.removeById(id);
        return success ? R.ok(true) : R.failed("删除失败");
    }

    /**
     * 获取所有可疑进程列表（用于下拉选择）
     *
     * @return 可疑进程列表
     */
    @GetMapping("/list")
    public R<List<SuspiciousProcessVO>> list() {
        List<MonitorSuspiciousProcess> processList = suspiciousProcessService.list();
        List<SuspiciousProcessVO> voList = TransferUtils.toList(processList, SuspiciousProcessVO.class);
        return R.ok(voList);
    }
} 