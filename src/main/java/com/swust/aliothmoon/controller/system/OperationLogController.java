package com.swust.aliothmoon.controller.system;

import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.OperationLog;
import com.swust.aliothmoon.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志Controller
 *
 * @author 
 *
 */
@RestController
@RequestMapping("/system/operationLog")
@RequiredArgsConstructor
public class OperationLogController {

    private final OperationLogService operationLogService;

    /**
     * 根据条件获取操作日志分页数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param username 用户名（可选）
     * @param operation 操作类型（可选）
     * @param path 请求路径（可选）
     * @param ip IP地址（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 分页数据
     */
    @GetMapping("/getOperationLogPageData")
    public R<TableDataInfo<OperationLog>> getOperationLogPageData(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String path,
            @RequestParam(required = false) String ip,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        TableDataInfo<OperationLog> pageData = operationLogService.getPageData(
                pageNum, pageSize, username, operation, path, ip, startTime, endTime);
        return R.ok(pageData);
    }

    /**
     * 清空指定天数之前的日志
     *
     * @param days 天数
     * @return 操作结果
     */
    @DeleteMapping("/cleanLogBeforeDays/{days}")
    public R<Boolean> cleanLogBeforeDays(@PathVariable int days) {
        boolean success = operationLogService.cleanLogBeforeDays(days);
        return R.ok(success);
    }

    /**
     * 记录操作日志
     *
     * @param operationLog 操作日志
     * @return 操作结果
     */
    @PostMapping("/recordLog")
    public R<Boolean> recordLog(@RequestBody OperationLog operationLog) {
        // 设置当前用户信息
        if (UserInfoContext.get() != null) {
            operationLog.setUserId(UserInfoContext.get().getUserId());
            operationLog.setUsername(UserInfoContext.get().getUsername());
        }
        boolean success = operationLogService.recordLog(operationLog);
        return R.ok(success);
    }
} 