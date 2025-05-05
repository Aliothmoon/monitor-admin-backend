package com.swust.aliothmoon.controller.monitor;

import com.mybatisflex.core.query.QueryChain;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorExam;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.entity.table.MonitorExamTableDef;
import com.swust.aliothmoon.model.vo.DashboardStatsVO;
import com.swust.aliothmoon.model.vo.ExamScheduleVO;
import com.swust.aliothmoon.service.ExamineeAccountService;
import com.swust.aliothmoon.service.MonitorExamService;
import com.swust.aliothmoon.service.MonitorUserService;
import com.swust.aliothmoon.ws.WebSocketServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.context.UserInfoContext.ONLINE_CACHE;


/**
 * 仪表板数据控制器
 * 提供首页仪表板所需的统计数据和列表
 *
 * @author Aliothmoon
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final MonitorExamService examService;
    private final MonitorUserService userService;
    private final ExamineeAccountService examineeAccountService;

    /**
     * 获取仪表板统计数据
     *
     * @return 统计数据
     */
    @GetMapping("/stats")
    public R<DashboardStatsVO> getStats() {
        DashboardStatsVO stats = new DashboardStatsVO();
        
        // 获取活跃考试数量（状态为1表示进行中）
        List<MonitorExam> activeExams = examService.queryChain()
                .where(MonitorExamTableDef.MONITOR_EXAM.STATUS.eq(1))
                .list();
        stats.setActiveExams(activeExams.size());
        
        // 获取今日所有考试数量（包括已结束、进行中、即将开始）
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(1);
        List<MonitorExam> todayExams = examService.queryChain()
                .where(MonitorExamTableDef.MONITOR_EXAM.START_TIME.ge(today))
                .and(MonitorExamTableDef.MONITOR_EXAM.START_TIME.lt(tomorrow))
                .list();
        stats.setTotalExams(todayExams.size());
        
        // 获取监考员数量
        List<MonitorUser> proctors = userService.list();
        stats.setTotalProctors(proctors.size());
        
        stats.setOnlineProctors(ONLINE_CACHE.size());


        
        stats.setOnlineCandidates(WebSocketServer.getOnlineCount()); // 示例值
        
        return R.ok(stats);
    }

    /**
     * 获取考试日程列表
     *
     * @param limit 限制返回数量，默认5条
     * @return 考试列表
     */
    @GetMapping("/exams")
    public R<List<ExamScheduleVO>> getExams(@RequestParam(defaultValue = "5") int limit) {
        // 查询考试列表，按开始时间排序
        List<MonitorExam> exams = examService.queryChain()
                .orderBy(MonitorExamTableDef.MONITOR_EXAM.START_TIME.asc())
                .limit(limit)
                .list();
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        // 将实体转换为VO
        List<ExamScheduleVO> result = exams.stream().map(exam -> {
            ExamScheduleVO vo = new ExamScheduleVO();
            vo.setName(exam.getName());
            
            // 设置考试状态
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(exam.getStartTime()) && now.isBefore(exam.getEndTime())) {
                vo.setStatus("进行中");
            } else if (now.isBefore(exam.getStartTime())) {
                vo.setStatus("即将开始");
            } else {
                vo.setStatus("已结束");
            }
            
            // 格式化时间显示
            String timeRange = timeFormatter.format(exam.getStartTime()) + "-" 
                    + timeFormatter.format(exam.getEndTime());
            vo.setTime(timeRange);
            
            // 计算考试时长（分钟）
            long durationMinutes = java.time.Duration.between(
                    exam.getStartTime(), exam.getEndTime()).toMinutes();
            vo.setDuration((int) durationMinutes);
            
            // 设置监考人员信息（这里需要根据实际系统设计调整）
            vo.setProctor("监考教师");
            
            // 获取该考试的考生数量
            int candidateCount = examineeAccountService.getByExamId(exam.getId()).size();
            vo.setCandidateCount(candidateCount);
            
            return vo;
        }).collect(Collectors.toList());
        
        return R.ok(result);
    }
} 