package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.OperationLog;
import com.swust.aliothmoon.model.dto.PageInfo;

/**
 * 操作日志服务接口
 *
 * @author 
 *
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 获取分页数据
     *
     * @param page 分页参数
     * @return 分页数据
     */
    TableDataInfo<OperationLog> getPageData(PageInfo page);

    /**
     * 根据条件获取分页数据
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
    TableDataInfo<OperationLog> getPageData(int pageNum, int pageSize, String username, 
                                          String operation, String path, String ip, 
                                          String startTime, String endTime);

    /**
     * 记录操作日志
     *
     * @param operationLog 操作日志对象
     * @return 是否成功
     */
    boolean recordLog(OperationLog operationLog);

    /**
     * 清空指定时间之前的日志
     *
     * @param days 天数
     * @return 是否成功
     */
    boolean cleanLogBeforeDays(int days);
} 