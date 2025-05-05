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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public R<Map<String, Object>> getScreenshots(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        List<MonitorScreenshotVO> screenshots = screenshotService.getScreenshotsByExaminee(accountId, examId, pageNum, pageSize);
        Integer total = screenshotService.countScreenshotsByExaminee(accountId, examId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", screenshots);
        result.put("total", total);
        
        return R.ok(result);
    }

    /**
     * 获取考生最新截图
     */
    @GetMapping("/latest-screenshot")
    public R<MonitorScreenshotVO> getLatestScreenshot(
            @RequestParam Integer accountId,
            @RequestParam Integer examId) {

        MonitorScreenshotVO screenshot = screenshotService.getLatestScreenshotByExaminee(accountId, examId);
        return R.ok(screenshot);
    }

    /**
     * 获取考生网站访问记录
     */
    @GetMapping("/website-visits")
    public R<Map<String, Object>> getWebsiteVisits(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        List<MonitorWebsiteVisitVO> websiteVisits = websiteVisitService.getRecentWebsiteVisitsByExaminee(accountId, examId, pageNum, pageSize);
        Integer total = websiteVisitService.countWebsiteVisits(accountId, examId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", websiteVisits);
        result.put("total", total);
        
        return R.ok(result);
    }

    /**
     * 获取考生进程记录
     */
    @GetMapping("/process-records")
    public R<Map<String, Object>> getProcessRecords(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        List<MonitorProcessRecordVO> processRecords = processRecordService.getRecentProcessRecordsByExaminee(accountId, examId, pageNum, pageSize);
        Integer total = processRecordService.countProcessRecords(accountId, examId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", processRecords);
        result.put("total", total);
        
        return R.ok(result);
    }

    /**
     * 获取考生行为分析记录
     */
    @GetMapping("/behavior-analysis")
    public R<List<MonitorBehaviorAnalysisVO>> getBehaviorAnalysis(
            @RequestParam Integer accountId,
            @RequestParam Integer examId) {

        List<MonitorBehaviorAnalysisVO> behaviorAnalysisList = behaviorAnalysisService.getRecentBehaviorAnalysisByExaminee(accountId, examId);
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

    /**
     * 上传截图数据
     */
    @PostMapping("/screenshot")
    public R<Boolean> uploadScreenshot(@RequestBody Map<String, Object> screenshotData) {
        Integer examId = (Integer) screenshotData.get("examId");
        Integer examineeAccountId = (Integer) screenshotData.get("examineeAccountId");
        String captureTimeStr = (String) screenshotData.get("captureTime");
        String screenshotUrl = (String) screenshotData.get("screenshotUrl");

        if (examId == null || examineeAccountId == null || screenshotUrl == null) {
            return R.failed("请提供完整的截图数据");
        }

        LocalDateTime captureTime = LocalDateTime.now();
        if (captureTimeStr != null) {
            try {
                captureTime = LocalDateTime.parse(captureTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (Exception e) {
                // 解析失败使用当前时间
            }
        }

        boolean result = screenshotService.saveScreenshot(examId, examineeAccountId, captureTime, screenshotUrl);
        return R.ok(result);
    }

    /**
     * 上传网站访问记录
     */
    @PostMapping("/website-visit")
    public R<Boolean> uploadWebsiteVisit(@RequestBody Map<String, Object> visitData) {
        try {
            Integer examId = (Integer) visitData.get("examId");
            Integer examineeAccountId = (Integer) visitData.get("examineeAccountId");
            String url = (String) visitData.get("url");
            String title = (String) visitData.get("title");
            String visitTimeStr = (String) visitData.get("visitTime");

            if (examId == null || examineeAccountId == null || url == null) {
                return R.failed("请提供完整的网站访问数据");
            }

            LocalDateTime visitTime = LocalDateTime.now();
            if (visitTimeStr != null) {
                try {
                    visitTime = LocalDateTime.parse(visitTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (Exception e) {
                    // 解析失败使用当前时间
                }
            }

            boolean result = websiteVisitService.saveWebsiteVisit(examId, examineeAccountId, url, title, visitTime);
            return R.ok(result);
        } catch (Exception e) {
            return R.failed("保存网站访问记录失败: " + e.getMessage());
        }
    }

    /**
     * 上传进程记录
     */
    @PostMapping("/process")
    public R<Boolean> uploadProcesses(@RequestBody Map<String, Object> processData) {
        try {
            Integer examId = (Integer) processData.get("examId");
            Integer examineeAccountId = (Integer) processData.get("examineeAccountId");
            String recordTimeStr = (String) processData.get("recordTime");
            List<Map<String, Object>> processes = (List<Map<String, Object>>) processData.get("processes");

            if (examId == null || examineeAccountId == null || processes == null) {
                return R.failed("请提供完整的进程数据");
            }

            LocalDateTime recordTime = LocalDateTime.now();
            if (recordTimeStr != null) {
                try {
                    recordTime = LocalDateTime.parse(recordTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (Exception e) {
                    // 解析失败使用当前时间
                }
            }

            boolean result = processRecordService.saveProcessRecords(examId, examineeAccountId, processes, recordTime);
            return R.ok(result);
        } catch (Exception e) {
            return R.failed("保存进程记录失败: " + e.getMessage());
        }
    }

    /**
     * 上传行为记录
     */
    @PostMapping("/behavior")
    public R<Boolean> uploadBehavior(@RequestBody Map<String, Object> behaviorData) {
        try {
            Integer examId = (Integer) behaviorData.get("examId");
            Integer examineeAccountId = (Integer) behaviorData.get("examineeAccountId");
            Integer eventType = (Integer) behaviorData.get("eventType");
            String content = (String) behaviorData.get("content");
            String level = (String) behaviorData.get("level");
            String eventTimeStr = (String) behaviorData.get("eventTime");

            if (examId == null || examineeAccountId == null || eventType == null || content == null) {
                return R.failed("请提供完整的行为数据");
            }

            LocalDateTime eventTime = LocalDateTime.now();
            if (eventTimeStr != null) {
                try {
                    eventTime = LocalDateTime.parse(eventTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (Exception e) {
                    // 解析失败使用当前时间
                }
            }

            boolean result = behaviorAnalysisService.saveBehaviorAnalysis(examId, examineeAccountId, eventType, content, level, eventTime);
            return R.ok(result);
        } catch (Exception e) {
            return R.failed("保存行为记录失败: " + e.getMessage());
        }
    }
} 