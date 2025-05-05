package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorScreenshot;
import com.swust.aliothmoon.mapper.MonitorScreenshotMapper;
import com.swust.aliothmoon.model.vo.MonitorScreenshotVO;
import com.swust.aliothmoon.service.MonitorScreenshotService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorScreenshotTableDef.MONITOR_SCREENSHOT;

/**
 * 考生屏幕截图服务实现类
 *
 * @author Aliothmoon
 */
@Service
public class MonitorScreenshotServiceImpl extends ServiceImpl<MonitorScreenshotMapper, MonitorScreenshot> implements MonitorScreenshotService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public List<MonitorScreenshotVO> getScreenshotsByExaminee(Integer examineeAccountId, Integer examId) {
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_SCREENSHOT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc());
        
        // 查询数据
        List<MonitorScreenshot> screenshots = list(queryWrapper);
        
        // 转换为VO
        return screenshots.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorScreenshotVO> getScreenshotsByExaminee(Integer examineeAccountId, Integer examId, Integer pageNum, Integer pageSize) {
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_SCREENSHOT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc());
        
        // 计算分页参数
        int offset = (pageNum - 1) * pageSize;
        queryWrapper.limit(offset, pageSize);
        
        // 查询数据
        List<MonitorScreenshot> screenshots = list(queryWrapper);
        
        // 转换为VO
        return screenshots.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public MonitorScreenshotVO getLatestScreenshotByExaminee(Integer examineeAccountId, Integer examId) {
        // 构建查询条件，获取最新一条记录
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_SCREENSHOT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc())
                .limit(1);
        
        // 查询数据
        MonitorScreenshot screenshot = getOne(queryWrapper);
        
        // 转换为VO
        return screenshot != null ? convertToVO(screenshot) : null;
    }

    @Override
    public Integer countScreenshotsByExaminee(Integer examineeAccountId, Integer examId) {
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_SCREENSHOT.EXAM_ID.eq(examId));
        
        // 查询数量
        return Math.toIntExact(count(queryWrapper));
    }

    @Override
    public boolean addScreenshot(MonitorScreenshot screenshot) {
        // 设置创建时间
        screenshot.setCreatedAt(LocalDateTime.now());
        
        // 如果没有设置截图时间，使用当前时间
        if (screenshot.getCaptureTime() == null) {
            screenshot.setCaptureTime(LocalDateTime.now());
        }
        
        // 保存记录
        return save(screenshot);
    }

    @Override
    public List<MonitorScreenshot> listByRiskLevel(Integer riskLevel) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.HAS_WARNING.eq(riskLevel > 0))
                .orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorScreenshot> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.CAPTURE_TIME.ge(startTime))
                .and(MONITOR_SCREENSHOT.CAPTURE_TIME.le(endTime))
                .orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorScreenshot> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorScreenshot> listByStudentId(Integer studentId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_SCREENSHOT.STUDENT_ID.eq(studentId))
                .orderBy(MONITOR_SCREENSHOT.CAPTURE_TIME.desc());
        
        return list(queryWrapper);
    }
    
    @Override
    public boolean saveScreenshot(Integer examId, Integer examineeAccountId, LocalDateTime captureTime, String screenshotUrl) {
        // 创建截图实体
        MonitorScreenshot screenshot = new MonitorScreenshot();
        screenshot.setExamId(examId);
        screenshot.setExamineeAccountId(examineeAccountId);
        screenshot.setCaptureTime(captureTime);
        screenshot.setScreenshotUrl(screenshotUrl);
        screenshot.setCreatedAt(LocalDateTime.now());
        screenshot.setExamName("");
        
        // 默认设置为无风险警告
        screenshot.setHasWarning(false);
        
        // TODO: 可以在此添加图像分析，检测是否有异常行为
        
        // 保存截图记录
        return save(screenshot);
    }
    
    /**
     * 将实体转换为VO
     * 
     * @param screenshot 截图实体
     * @return 截图VO
     */
    private MonitorScreenshotVO convertToVO(MonitorScreenshot screenshot) {
        if (screenshot == null) {
            return null;
        }
        
        MonitorScreenshotVO vo = new MonitorScreenshotVO();
        vo.setId(screenshot.getId());
        
        // 优先使用新字段，兼容旧字段
        String url = screenshot.getScreenshotUrl();
        if (url == null || url.isEmpty()) {
            url = screenshot.getImageUrl();
        }
        if (url == null || url.isEmpty()) {
            url = screenshot.getScreenshotData(); // 如果URL为空，使用Base64数据
        }
        vo.setUrl(url);
        
        vo.setCaptureTime(screenshot.getCaptureTime());
        // 格式化时间为字符串 HH:mm:ss
        vo.setTime(screenshot.getCaptureTime().format(TIME_FORMATTER));
        vo.setHasWarning(screenshot.getHasWarning());
        vo.setAnalysis(screenshot.getAnalysisResult());
        
        return vo;
    }
} 