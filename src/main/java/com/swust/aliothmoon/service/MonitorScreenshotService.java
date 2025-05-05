package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.entity.MonitorScreenshot;
import com.swust.aliothmoon.model.vo.MonitorScreenshotVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 考生屏幕截图服务接口
 *
 * @author Aliothmoon
 */
public interface MonitorScreenshotService extends IService<MonitorScreenshot> {
    
    /**
     * 获取考生屏幕截图记录
     *
     * @param examineeAccountId 考生账号ID
     * @param examId            考试ID
     * @return 屏幕截图记录列表
     */
    List<MonitorScreenshotVO> getScreenshotsByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 获取考生屏幕截图记录（分页）
     *
     * @param examineeAccountId 考生账号ID
     * @param examId            考试ID
     * @param pageNum           页码
     * @param pageSize          每页大小
     * @return 屏幕截图记录列表
     */
    List<MonitorScreenshotVO> getScreenshotsByExaminee(Integer examineeAccountId, Integer examId, Integer pageNum, Integer pageSize);
    
    /**
     * 获取考生最新一张屏幕截图
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 屏幕截图记录
     */
    MonitorScreenshotVO getLatestScreenshotByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 统计考生截图数量
     *
     * @param examineeAccountId 考生账号ID
     * @param examId 考试ID
     * @return 截图数量
     */
    Integer countScreenshotsByExaminee(Integer examineeAccountId, Integer examId);
    
    /**
     * 添加考生屏幕截图记录
     *
     * @param screenshot 屏幕截图记录
     * @return 是否成功
     */
    boolean addScreenshot(MonitorScreenshot screenshot);

    /**
     * 根据风险等级查询截图列表
     *
     * @param riskLevel 风险等级
     * @return 截图列表
     */
    List<MonitorScreenshot> listByRiskLevel(Integer riskLevel);

    /**
     * 根据时间范围查询截图列表
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 截图列表
     */
    List<MonitorScreenshot> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据考试ID查询截图列表
     *
     * @param examId 考试ID
     * @return 截图列表
     */
    List<MonitorScreenshot> listByExamId(Integer examId);

    /**
     * 根据学生ID查询截图列表
     *
     * @param studentId 学生ID
     * @return 截图列表
     */
    List<MonitorScreenshot> listByStudentId(Integer studentId);
    
    /**
     * 保存考生屏幕截图
     *
     * @param examId 考试ID
     * @param examineeAccountId 考生账号ID
     * @param captureTime 截图时间
     * @param screenshotUrl 截图文件路径
     * @return 是否成功
     */
    boolean saveScreenshot(Integer examId, Integer examineeAccountId, LocalDateTime captureTime, String screenshotUrl);
} 