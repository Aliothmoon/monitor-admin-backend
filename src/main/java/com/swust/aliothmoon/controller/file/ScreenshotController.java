package com.swust.aliothmoon.controller.file;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorScreenshot;
import com.swust.aliothmoon.model.screenshot.ScreenshotQueryDTO;
import com.swust.aliothmoon.model.screenshot.ScreenshotUpdateDTO;
import com.swust.aliothmoon.model.screenshot.ScreenshotVO;
import com.swust.aliothmoon.service.MonitorScreenshotService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorScreenshotTableDef.MONITOR_SCREENSHOT;

/**
 * 截图管理控制器
 *
 * @author Aliothmoon
 *
 */
@RestController
@RequestMapping("/file/screenshot")
@RequiredArgsConstructor
public class ScreenshotController {

    private final MonitorScreenshotService screenshotService;

    /**
     * 分页查询截图列表
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<ScreenshotVO>> page(@RequestBody ScreenshotQueryDTO queryDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create();

        // 添加查询条件
        if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().isEmpty()) {
            queryWrapper.and(MONITOR_SCREENSHOT.EXAM_NAME.like(queryDTO.getKeyword())
                    .or(MONITOR_SCREENSHOT.STUDENT_NAME.like(queryDTO.getKeyword()))
                    .or(MONITOR_SCREENSHOT.REMARK.like(queryDTO.getKeyword())));
        }

        if (queryDTO.getRiskLevel() != null) {
            queryWrapper.and(MONITOR_SCREENSHOT.RISK_LEVEL.eq(queryDTO.getRiskLevel()));
        }

        if (queryDTO.getExamId() != null) {
            queryWrapper.and(MONITOR_SCREENSHOT.EXAM_ID.eq(queryDTO.getExamId()));
        }

        if (queryDTO.getStudentId() != null) {
            queryWrapper.and(MONITOR_SCREENSHOT.STUDENT_ID.eq(queryDTO.getStudentId()));
        }

        if (queryDTO.getStartTime() != null) {
            queryWrapper.and(MONITOR_SCREENSHOT.CAPTURE_TIME.ge(queryDTO.getStartTime()));
        }

        if (queryDTO.getEndTime() != null) {
            queryWrapper.and(MONITOR_SCREENSHOT.CAPTURE_TIME.le(queryDTO.getEndTime()));
        }

        // 按截图时间降序排序
        queryWrapper.orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc());

        // 分页查询
        Page<MonitorScreenshot> page = screenshotService.page(
                new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                queryWrapper
        );

        // 转换为VO
        Page<ScreenshotVO> result = new Page<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalRow(page.getTotalRow());
        result.setTotalPage(page.getTotalPage());

        List<ScreenshotVO> records = TransferUtils.toList(page.getRecords(), ScreenshotVO.class);
        result.setRecords(records);

        return R.ok(result);
    }

    /**
     * 根据ID查询截图
     *
     * @param id 截图ID
     * @return 截图信息
     */
    @GetMapping("/{id}")
    public R<ScreenshotVO> getById(@PathVariable Integer id) {
        MonitorScreenshot screenshot = screenshotService.getById(id);
        if (screenshot == null) {
            return R.failed("截图不存在");
        }
        ScreenshotVO vo = TransferUtils.to(screenshot, ScreenshotVO.class);
        return R.ok(vo);
    }

    /**
     * 更新截图信息
     *
     * @param updateDTO 更新信息
     * @return 更新结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody ScreenshotUpdateDTO updateDTO) {
        MonitorScreenshot screenshot = screenshotService.getById(updateDTO.getId());
        if (screenshot == null) {
            return R.failed("截图不存在");
        }

        screenshot.setRiskLevel(updateDTO.getRiskLevel());
        screenshot.setRemark(updateDTO.getRemark());

        // 设置更新者和更新时间
        screenshot.setUpdatedAt(LocalDateTime.now());
        screenshot.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = screenshotService.updateById(screenshot);
        return success ? R.ok(true) : R.failed("更新失败");
    }

    /**
     * 删除截图
     *
     * @param id 截图ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Integer id) {
        boolean success = screenshotService.removeById(id);
        return success ? R.ok(true) : R.failed("删除失败");
    }
} 