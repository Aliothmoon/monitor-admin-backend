package com.swust.aliothmoon.controller.monitor;

import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.model.vo.MonitorBehaviorAnalysisVO;
import com.swust.aliothmoon.model.vo.MonitorProcessRecordVO;
import com.swust.aliothmoon.model.vo.MonitorScreenshotVO;
import com.swust.aliothmoon.model.vo.MonitorWebsiteVisitVO;
import com.swust.aliothmoon.service.MonitorBehaviorAnalysisService;
import com.swust.aliothmoon.service.MonitorProcessRecordService;
import com.swust.aliothmoon.service.MonitorScreenshotService;
import com.swust.aliothmoon.service.MonitorWebsiteVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考生监控数据控制器
 *
 * @author Aliothmoon
 */
@RestController
@RequestMapping("/monitor/data")
@RequiredArgsConstructor
public class ExamineeMonitorDataController {

    private final MonitorScreenshotService screenshotService;
    private final MonitorWebsiteVisitService websiteVisitService;
    private final MonitorProcessRecordService processRecordService;
    private final MonitorBehaviorAnalysisService behaviorAnalysisService;

    /**
     * 获取考生截图列表
     */
    @GetMapping("/screenshots")
    public R<List<MonitorScreenshotVO>> getScreenshots(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        
        List<MonitorScreenshotVO> screenshots = screenshotService.getScreenshotsByExaminee(accountId, examId, limit);
        return R.ok(screenshots);
    }

    /**
     * 获取考生最新截图
     */
    @GetMapping("/latest-screenshot")
    public R<MonitorScreenshotVO> getLatestScreenshot(
            @RequestParam Integer accountId,
            @RequestParam Integer examId) {
        
        MonitorScreenshotVO screenshot = screenshotService.getLatestScreenshotByExaminee(accountId, examId);
        if (screenshot == null) {
            return R.failed("未找到截图");
        }
        return R.ok(screenshot);
    }

    /**
     * 获取考生网站访问记录
     */
    @GetMapping("/website-visits")
    public R<List<MonitorWebsiteVisitVO>> getWebsiteVisits(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        
        List<MonitorWebsiteVisitVO> websiteVisits = websiteVisitService.getRecentWebsiteVisitsByExaminee(accountId, examId, limit);
        return R.ok(websiteVisits);
    }

    /**
     * 获取考生进程记录
     */
    @GetMapping("/process-records")
    public R<List<MonitorProcessRecordVO>> getProcessRecords(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        
        List<MonitorProcessRecordVO> processRecords = processRecordService.getRecentProcessRecordsByExaminee(accountId, examId, limit);
        return R.ok(processRecords);
    }

    /**
     * 获取考生行为分析记录
     */
    @GetMapping("/behavior-analysis")
    public R<List<MonitorBehaviorAnalysisVO>> getBehaviorAnalysis(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false, defaultValue = "20") Integer limit) {
        
        List<MonitorBehaviorAnalysisVO> behaviorAnalysisList = behaviorAnalysisService.getRecentBehaviorAnalysisByExaminee(accountId, examId, limit);
        return R.ok(behaviorAnalysisList);
    }
    
    /**
     * 获取考生切屏次数
     */
    @GetMapping("/switch-screen-count")
    public R<Integer> getSwitchScreenCount(
            @RequestParam Integer accountId,
            @RequestParam Integer examId) {
        
        Integer count = behaviorAnalysisService.countSwitchScreens(accountId, examId);
        return R.ok(count);
    }
} 