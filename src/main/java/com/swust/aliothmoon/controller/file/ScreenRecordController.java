package com.swust.aliothmoon.controller.file;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorScreenRecord;
import com.swust.aliothmoon.model.screenrecord.ScreenRecordQueryDTO;
import com.swust.aliothmoon.model.screenrecord.ScreenRecordUpdateDTO;
import com.swust.aliothmoon.model.screenrecord.ScreenRecordVO;
import com.swust.aliothmoon.service.MonitorScreenRecordService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorScreenRecordTableDef.MONITOR_SCREEN_RECORD;

/**
 * 录屏管理控制器
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/file/screen-record")
@RequiredArgsConstructor
public class ScreenRecordController {

    private final MonitorScreenRecordService screenRecordService;

    /**
     * 分页查询录屏列表
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<ScreenRecordVO>> page(@RequestBody ScreenRecordQueryDTO queryDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create();

        // 添加查询条件
        if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().isEmpty()) {
            queryWrapper.and(MONITOR_SCREEN_RECORD.EXAM_NAME.like(queryDTO.getKeyword())
                    .or(MONITOR_SCREEN_RECORD.STUDENT_NAME.like(queryDTO.getKeyword()))
                    .or(MONITOR_SCREEN_RECORD.REMARK.like(queryDTO.getKeyword())));
        }

        if (queryDTO.getRiskLevel() != null) {
            queryWrapper.and(MONITOR_SCREEN_RECORD.RISK_LEVEL.eq(queryDTO.getRiskLevel()));
        }

        if (queryDTO.getExamId() != null) {
            queryWrapper.and(MONITOR_SCREEN_RECORD.EXAM_ID.eq(queryDTO.getExamId()));
        }

        if (queryDTO.getStudentId() != null) {
            queryWrapper.and(MONITOR_SCREEN_RECORD.STUDENT_ID.eq(queryDTO.getStudentId()));
        }

        if (queryDTO.getStartTime() != null) {
            queryWrapper.and(MONITOR_SCREEN_RECORD.START_TIME.ge(queryDTO.getStartTime()));
        }

        if (queryDTO.getEndTime() != null) {
            queryWrapper.and(MONITOR_SCREEN_RECORD.END_TIME.le(queryDTO.getEndTime()));
        }

        // 按开始时间降序排序
        queryWrapper.orderBy(MONITOR_SCREEN_RECORD.START_TIME.desc());

        // 分页查询
        Page<MonitorScreenRecord> page = screenRecordService.page(
                new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                queryWrapper
        );

        // 转换为VO
        Page<ScreenRecordVO> result = new Page<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalRow(page.getTotalRow());
        result.setTotalPage(page.getTotalPage());

        List<ScreenRecordVO> records = TransferUtils.toList(page.getRecords(), ScreenRecordVO.class);
        result.setRecords(records);

        return R.ok(result);
    }

    /**
     * 根据ID查询录屏
     *
     * @param id 录屏ID
     * @return 录屏信息
     */
    @GetMapping("/{id}")
    public R<ScreenRecordVO> getById(@PathVariable Integer id) {
        MonitorScreenRecord screenRecord = screenRecordService.getById(id);
        if (screenRecord == null) {
            return R.failed("录屏不存在");
        }
        ScreenRecordVO vo = TransferUtils.to(screenRecord, ScreenRecordVO.class);
        return R.ok(vo);
    }

    /**
     * 更新录屏信息
     *
     * @param updateDTO 更新信息
     * @return 更新结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody ScreenRecordUpdateDTO updateDTO) {
        MonitorScreenRecord screenRecord = screenRecordService.getById(updateDTO.getId());
        if (screenRecord == null) {
            return R.failed("录屏不存在");
        }

        screenRecord.setRiskLevel(updateDTO.getRiskLevel());
        screenRecord.setRemark(updateDTO.getRemark());

        // 设置更新者和更新时间
        screenRecord.setUpdatedAt(LocalDateTime.now());
        screenRecord.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = screenRecordService.updateById(screenRecord);
        return success ? R.ok(true) : R.failed("更新失败");
    }

    /**
     * 删除录屏
     *
     * @param id 录屏ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Integer id) {
        boolean success = screenRecordService.removeById(id);
        return success ? R.ok(true) : R.failed("删除失败");
    }
} 