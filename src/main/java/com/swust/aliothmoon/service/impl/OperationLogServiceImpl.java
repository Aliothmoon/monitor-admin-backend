package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.OperationLog;
import com.swust.aliothmoon.entity.table.OperationLogTableDef;
import com.swust.aliothmoon.mapper.OperationLogMapper;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 操作日志服务实现类
 *
 * @author
 *
 */
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    private final OperationLogMapper operationLogMapper;

    @Override
    public TableDataInfo<OperationLog> getPageData(PageInfo page) {
        Page<OperationLog> p = page(page.toPage());
        return TableDataInfo.of(p);
    }

    @Override
    public TableDataInfo<OperationLog> getPageData(int pageNum, int pageSize, String username, String operation, String path, String ip, String startTime, String endTime) {
        OperationLogTableDef operationLog = OperationLogTableDef.OPERATION_LOG;

        QueryChain<OperationLog> queryChain = QueryChain.of(OperationLog.class);

        // 添加查询条件
        if (StringUtils.hasText(username)) {
            queryChain.where(operationLog.USERNAME.like(username));
        }

        if (StringUtils.hasText(operation)) {
            queryChain.where(operationLog.OPERATION.like(operation));
        }

        if (StringUtils.hasText(path)) {
            queryChain.where(operationLog.PATH.like(path));
        }

        if (StringUtils.hasText(ip)) {
            queryChain.where(operationLog.IP.like(ip));
        }

        // 处理时间范围
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (StringUtils.hasText(startTime)) {
            try {
                LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
                queryChain.where(operationLog.OPERATION_TIME.ge(startDateTime));
            } catch (Exception e) {
                // 时间格式错误，忽略该条件
            }
        }

        if (StringUtils.hasText(endTime)) {
            try {
                LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
                queryChain.where(operationLog.OPERATION_TIME.le(endDateTime));
            } catch (Exception e) {
                // 时间格式错误，忽略该条件
            }
        }

        // 按操作时间降序排序
        queryChain.orderBy(operationLog.OPERATION_TIME.desc());

        // 执行分页查询
        Page<OperationLog> page = new Page<>(pageNum, pageSize);
        Page<OperationLog> result = page(page, queryChain);

        return TableDataInfo.of(result);
    }

    @Override
    public boolean recordLog(OperationLog operationLog) {
        if (operationLog.getOperationTime() == null) {
            operationLog.setOperationTime(LocalDateTime.now());
        }
        return save(operationLog);
    }

    @Override
    public boolean cleanLogBeforeDays(int days) {
        OperationLogTableDef operationLog = OperationLogTableDef.OPERATION_LOG;

        LocalDateTime beforeTime = LocalDateTime.now().minusDays(days);

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(operationLog.OPERATION_TIME.lt(beforeTime));

        return operationLogMapper.deleteByQuery(queryWrapper) > 0;
    }
} 