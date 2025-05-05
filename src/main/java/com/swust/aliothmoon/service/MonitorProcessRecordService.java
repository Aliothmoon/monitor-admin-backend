package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorProcessRecord;
import com.swust.aliothmoon.model.vo.MonitorProcessRecordVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 考生进程记录服务接口
 *
 * @author Aliothmoon
 */
public interface MonitorProcessRecordService extends IService<MonitorProcessRecord> {
    
    /**
     * 获取考生进程记录
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 进程记录列表
     */
    List<MonitorProcessRecordVO> getProcessRecordsByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 获取考生最近的进程记录
     *
     * @param examineeAccountId 考生账号ID
     * @param examId            考试ID
     * @return 进程记录列表
     */
    List<MonitorProcessRecordVO> getRecentProcessRecordsByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 获取考生最近的进程记录（带分页）
     *
     * @param examineeAccountId 考生账号ID
     * @param examId            考试ID 
     * @param pageNum           页码
     * @param pageSize          每页大小
     * @return 进程记录列表
     */
    List<MonitorProcessRecordVO> getRecentProcessRecordsByExaminee(Integer examineeAccountId, Integer examId, Integer pageNum, Integer pageSize);
    
    /**
     * 添加进程记录
     *
     * @param processRecord 进程记录
     * @return 是否成功
     */
    boolean addProcessRecord(MonitorProcessRecord processRecord);
    
    /**
     * 更新进程结束时间
     *
     * @param id 进程记录ID
     * @param endTime 结束时间
     * @return 是否成功
     */
    boolean updateProcessEndTime(Integer id, LocalDateTime endTime);
    
    /**
     * 根据风险等级查询进程记录
     *
     * @param riskLevel 风险等级
     * @return 进程记录列表
     */
    List<MonitorProcessRecord> listByRiskLevel(Integer riskLevel);
    
    /**
     * 根据时间范围查询进程记录
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 进程记录列表
     */
    List<MonitorProcessRecord> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据考试ID查询进程记录
     *
     * @param examId 考试ID
     * @return 进程记录列表
     */
    List<MonitorProcessRecord> listByExamId(Integer examId);
    
    /**
     * 根据进程名称关键字查询进程记录
     *
     * @param keyword 进程名称关键字
     * @return 进程记录列表
     */
    List<MonitorProcessRecord> listByNameKeyword(String keyword);
    
    /**
     * 统计考生进程总数
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 进程总数
     */
    Integer countProcessRecords(Integer examineeAccountId, Integer examId);
    
    /**
     * 保存考生进程记录
     *
     * @param examId 考试ID
     * @param examineeAccountId 考生账号ID
     * @param processes 进程列表
     * @param recordTime 记录时间
     * @return 是否成功
     */
    boolean saveProcessRecords(Integer examId, Integer examineeAccountId, List<Map<String, Object>> processes, LocalDateTime recordTime);
} 