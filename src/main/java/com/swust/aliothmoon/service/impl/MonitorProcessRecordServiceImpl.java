package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorProcessRecord;
import com.swust.aliothmoon.mapper.MonitorProcessRecordMapper;
import com.swust.aliothmoon.model.vo.MonitorProcessRecordVO;
import com.swust.aliothmoon.service.MonitorProcessRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorProcessRecordTableDef.MONITOR_PROCESS_RECORD;

/**
 * 考生进程记录服务实现类
 *
 * @author Aliothmoon
 */
@Service
public class MonitorProcessRecordServiceImpl extends ServiceImpl<MonitorProcessRecordMapper, MonitorProcessRecord> implements MonitorProcessRecordService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    // 进程黑名单集合（实际项目中建议从数据库读取）
    private static final Set<String> PROCESS_BLACKLIST = new HashSet<>(Arrays.asList(
        "teamviewer.exe", "anydesk.exe", "vnc", "remote", "chat.exe", 
        "qq.exe", "wechat.exe", "dingtalk.exe", "telegram.exe", "discord.exe", "skype.exe",
        "browser_proxy.exe", "proxy.exe", "vpn", "tor.exe", "ultrasurf.exe",
        "cheatengine.exe", "cheat", "hack", "keygen.exe", "crack.exe",
        "cmd.exe", "powershell.exe", "bash.exe", "terminal.exe", "ssh.exe"
    ));
    
    // 进程黑名单关键字（部分匹配）
    private static final Set<String> PROCESS_BLACKLIST_KEYWORDS = new HashSet<>(Arrays.asList(
        "远程", "控制", "作弊", "聊天", "黑客", "破解", "外挂", "代理", "vpn", "共享", "屏幕"
    ));
    
    // 风险等级
    private static final int RISK_LEVEL_NORMAL = 0;
    private static final int RISK_LEVEL_WARNING = 1;
    private static final int RISK_LEVEL_DANGER = 2;

    @Override
    public List<MonitorProcessRecordVO> getProcessRecordsByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        List<MonitorProcessRecord> processRecords = list(queryWrapper);
        return processRecords.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorProcessRecordVO> getRecentProcessRecordsByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());

        List<MonitorProcessRecord> processRecords = list(queryWrapper);
        return processRecords.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorProcessRecordVO> getRecentProcessRecordsByExaminee(Integer examineeAccountId, Integer examId, Integer pageNum, Integer pageSize) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
                
        // 计算分页参数
        int offset = (pageNum - 1) * pageSize;
        queryWrapper.limit(offset, pageSize);
        
        List<MonitorProcessRecord> processRecords = list(queryWrapper);
        return processRecords.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public boolean addProcessRecord(MonitorProcessRecord processRecord) {
        processRecord.setCreatedAt(LocalDateTime.now());
        return save(processRecord);
    }

    @Override
    public boolean updateProcessEndTime(Integer id, LocalDateTime endTime) {
        MonitorProcessRecord processRecord = getById(id);
        if (processRecord == null) {
            return false;
        }
        
        processRecord.setEndTime(endTime);
        return updateById(processRecord);
    }

    @Override
    public List<MonitorProcessRecord> listByRiskLevel(Integer riskLevel) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.RISK_LEVEL.eq(riskLevel))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorProcessRecord> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.START_TIME.ge(startTime))
                .and(MONITOR_PROCESS_RECORD.START_TIME.le(endTime))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorProcessRecord> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public List<MonitorProcessRecord> listByNameKeyword(String keyword) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.PROCESS_NAME.like("%" + keyword + "%"))
                .orderBy(MONITOR_PROCESS_RECORD.START_TIME.desc());
        
        return list(queryWrapper);
    }

    @Override
    public Integer countProcessRecords(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_PROCESS_RECORD.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_PROCESS_RECORD.EXAM_ID.eq(examId));
        
        return Math.toIntExact(count(queryWrapper));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveProcessRecords(Integer examId, Integer examineeAccountId, List<Map<String, Object>> processes, LocalDateTime recordTime) {
        if (processes == null || processes.isEmpty()) {
            return false;
        }
        
        List<MonitorProcessRecord> processRecords = new ArrayList<>();
        
        for (Map<String, Object> process : processes) {
            MonitorProcessRecord record = new MonitorProcessRecord();
            record.setExamId(examId);
            record.setExamineeAccountId(examineeAccountId);
            
            // 获取进程名称
            String processName = getStringValue(process, "name");
            record.setProcessName(processName);
            
            // 获取进程PID和资源使用信息，存储到描述字段
            Integer pid = getIntegerValue(process, "pid");
            Double memoryUsage = getDoubleValue(process, "memory");
            Double cpuUsage = getDoubleValue(process, "cpu");
            
            // 构建详细的描述信息
            String description = String.format("PID: %d, 内存使用: %.2f MB, CPU使用率: %.2f%%", 
                                            pid != null ? pid : 0, 
                                            memoryUsage, 
                                            cpuUsage);
            record.setDescription(description);
            
            // 设置开始时间为当前记录时间
            record.setStartTime(recordTime);
            
            // 设置创建时间
            record.setCreatedAt(LocalDateTime.now());
            
            // 检查进程是否在黑名单中
            ProcessBlacklistResult blacklistResult = checkProcessBlacklist(processName);
            record.setRiskLevel(blacklistResult.getRiskLevel());
            
            // 如果进程在黑名单中，更新描述信息
            if (blacklistResult.isBlacklisted()) {
                record.setDescription(description + " - " + blacklistResult.getReason());
            }
            
            processRecords.add(record);
        }
        
        // 批量保存进程记录
        return saveBatch(processRecords);
    }
    
    /**
     * 进程黑名单检查结果
     */
    private static class ProcessBlacklistResult {
        private final boolean blacklisted;
        private final int riskLevel;
        private final String reason;
        
        public ProcessBlacklistResult(boolean blacklisted, int riskLevel, String reason) {
            this.blacklisted = blacklisted;
            this.riskLevel = riskLevel;
            this.reason = reason;
        }
        
        public boolean isBlacklisted() {
            return blacklisted;
        }
        
        public int getRiskLevel() {
            return riskLevel;
        }
        
        public String getReason() {
            return reason;
        }
    }
    
    /**
     * 检查进程是否在黑名单中
     * 
     * @param processName 进程名称
     * @return 黑名单检查结果
     */
    private ProcessBlacklistResult checkProcessBlacklist(String processName) {
        if (processName == null || processName.isEmpty()) {
            return new ProcessBlacklistResult(false, RISK_LEVEL_NORMAL, "");
        }
        
        // 转为小写进行匹配
        String lowerProcessName = processName.toLowerCase();
        
        // 检查完全匹配的黑名单
        for (String blacklistProcess : PROCESS_BLACKLIST) {
            if (lowerProcessName.equals(blacklistProcess.toLowerCase())) {
                return new ProcessBlacklistResult(true, RISK_LEVEL_DANGER, "黑名单进程: " + blacklistProcess);
            }
        }
        
        // 检查部分匹配的黑名单关键字
        for (String keyword : PROCESS_BLACKLIST_KEYWORDS) {
            if (lowerProcessName.contains(keyword.toLowerCase())) {
                return new ProcessBlacklistResult(true, RISK_LEVEL_WARNING, "可疑进程，包含关键字: " + keyword);
            }
        }
        
        // 如果是系统管理类进程，设置为警告级别
        if (lowerProcessName.contains("regedit") || 
            lowerProcessName.contains("taskmgr") || 
            lowerProcessName.contains("msconfig")) {
            return new ProcessBlacklistResult(true, RISK_LEVEL_WARNING, "系统工具进程，可能用于修改系统设置");
        }
        
        return new ProcessBlacklistResult(false, RISK_LEVEL_NORMAL, "");
    }
    
    /**
     * 将实体转换为VO
     */
    private MonitorProcessRecordVO convertToVO(MonitorProcessRecord processRecord) {
        if (processRecord == null) {
            return null;
        }
        
        MonitorProcessRecordVO vo = new MonitorProcessRecordVO();
        vo.setId(processRecord.getId());
        vo.setName(processRecord.getProcessName());
        
        // 从描述中解析信息
        String description = processRecord.getDescription();
        int pid = 0;
        double memoryUsage = 0.0;
        double cpuUsage = 0.0;
        
        if (description != null) {
            try {
                // 尝试从描述中提取PID、内存和CPU使用率
                if (description.contains("PID:")) {
                    String pidStr = description.substring(description.indexOf("PID:") + 4, description.indexOf(","));
                    pid = Integer.parseInt(pidStr.trim());
                }
                
                if (description.contains("内存使用:") && description.contains("MB")) {
                    String memStr = description.substring(description.indexOf("内存使用:") + 5, description.indexOf("MB"));
                    memoryUsage = Double.parseDouble(memStr.trim());
                }
                
                if (description.contains("CPU使用率:") && description.contains("%")) {
                    String cpuStr = description.substring(description.indexOf("CPU使用率:") + 7, description.indexOf("%"));
                    cpuUsage = Double.parseDouble(cpuStr.trim());
                }
            } catch (Exception e) {
                // 解析失败则使用默认值
            }
        }
        
        vo.setPid(pid);
        vo.setStartTime(processRecord.getStartTime());
        vo.setStartTimeStr(processRecord.getStartTime() != null ? processRecord.getStartTime().format(TIME_FORMATTER) : null);
        vo.setEndTime(processRecord.getEndTime());
        vo.setEndTimeStr(processRecord.getEndTime() != null ? processRecord.getEndTime().format(TIME_FORMATTER) : null);
        vo.setRiskLevel(processRecord.getRiskLevel());
        vo.setStatus(processRecord.getRiskLevel() > 0 ? "warning" : "normal");
        vo.setDescription(processRecord.getDescription());
        vo.setMemoryUsage(memoryUsage);
        vo.setCpuUsage(cpuUsage);
        vo.setIsBlacklist(processRecord.getRiskLevel() > 0);
        
        return vo;
    }
    
    /**
     * 安全获取字符串值
     */
    private String getStringValue(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
            Object value = data.get(key);
            return value != null ? value.toString() : "";
        }
        return "";
    }
    
    /**
     * 安全获取Integer值
     */
    private Integer getIntegerValue(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else if (value instanceof String) {
                try {
                    return Integer.parseInt((String) value);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
    
    /**
     * 安全获取Double值
     */
    private Double getDoubleValue(Map<String, Object> data, String key) {
        if (data.containsKey(key)) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else if (value instanceof String) {
                try {
                    return Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }
} 