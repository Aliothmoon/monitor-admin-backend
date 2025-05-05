package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorWebsiteVisit;
import com.swust.aliothmoon.mapper.MonitorWebsiteVisitMapper;
import com.swust.aliothmoon.model.vo.MonitorWebsiteVisitVO;
import com.swust.aliothmoon.service.MonitorWebsiteVisitService;
import com.swust.aliothmoon.utils.TransferUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.swust.aliothmoon.entity.table.MonitorWebsiteVisitTableDef.MONITOR_WEBSITE_VISIT;

/**
 * 考生网站访问记录服务实现类
 *
 * @author Aliothmoon
 */
@Service
public class MonitorWebsiteVisitServiceImpl extends ServiceImpl<MonitorWebsiteVisitMapper, MonitorWebsiteVisit> implements MonitorWebsiteVisitService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    // 网站黑名单集合（实际项目中建议从数据库读取）
    private static final Set<String> WEBSITE_BLACKLIST = new HashSet<>(Arrays.asList(
            "baidu.com", "google.com", "bing.com", "wikipedia.org", "stackoverflow.com",
            "github.com", "gitlab.com", "bitbucket.org", "csdn.net", "oschina.net",
            "zhihu.com", "qqmail.com", "mail.163.com", "gmail.com", "qq.com",
            "chegg.com", "coursehero.com", "quizlet.com", "koolearn.com",
            "vpn.", "proxy.", "tunnel."
    ));

    // 网站黑名单关键字（部分匹配）
    private static final Set<String> WEBSITE_BLACKLIST_KEYWORDS = new HashSet<>(Arrays.asList(
            "考试", "答案", "试题", "作弊", "cheat", "exam", "answer", "solution",
            "hack", "crack", "proxy", "vpn", "翻译", "translate", "chat",
            "在线", "搜索", "search", "问答", "社交", "社区", "邮箱", "邮件", "聊天"
    ));

    // 风险等级
    private static final int RISK_LEVEL_NORMAL = 0;
    private static final int RISK_LEVEL_WARNING = 1;
    private static final int RISK_LEVEL_DANGER = 2;

    // 提取域名的正则表达式
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("(?:https?://)?(?:www\\.)?([^/]+)(?:/.*)?");

    @Override
    public List<MonitorWebsiteVisitVO> getWebsiteVisitsByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());

        List<MonitorWebsiteVisit> websiteVisits = list(queryWrapper);
        return websiteVisits.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorWebsiteVisitVO> getRecentWebsiteVisitsByExaminee(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());

        List<MonitorWebsiteVisit> websiteVisits = list(queryWrapper);
        return websiteVisits.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<MonitorWebsiteVisitVO> getRecentWebsiteVisitsByExaminee(Integer examineeAccountId, Integer examId, Integer pageNum, Integer pageSize) {
        QueryWrapper wrapper = QueryWrapper.create().where(MONITOR_WEBSITE_VISIT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId));

        List<MonitorWebsiteVisit> records = page(Page.of(pageNum, pageSize), wrapper).getRecords();
        return TransferUtils.toList(records, MonitorWebsiteVisitVO.class);
    }

    @Override
    public boolean addWebsiteVisit(MonitorWebsiteVisit websiteVisit) {
        websiteVisit.setCreatedAt(LocalDateTime.now());
        return save(websiteVisit);
    }

    @Override
    public List<MonitorWebsiteVisit> listByRiskLevel(Integer riskLevel) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.RISK_LEVEL.eq(riskLevel))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());

        return list(queryWrapper);
    }

    @Override
    public List<MonitorWebsiteVisit> listByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.VISIT_TIME.ge(startTime))
                .and(MONITOR_WEBSITE_VISIT.VISIT_TIME.le(endTime))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());

        return list(queryWrapper);
    }

    @Override
    public List<MonitorWebsiteVisit> listByExamId(Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());

        return list(queryWrapper);
    }

    @Override
    public List<MonitorWebsiteVisit> listByUrlKeyword(String keyword) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.URL.like("%" + keyword + "%"))
                .or(MONITOR_WEBSITE_VISIT.TITLE.like("%" + keyword + "%"))
                .orderBy(MONITOR_WEBSITE_VISIT.VISIT_TIME.desc());

        return list(queryWrapper);
    }


    @Override
    public Integer countWebsiteVisits(Integer examineeAccountId, Integer examId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(MONITOR_WEBSITE_VISIT.EXAMINEE_ACCOUNT_ID.eq(examineeAccountId))
                .and(MONITOR_WEBSITE_VISIT.EXAM_ID.eq(examId));

        return Math.toIntExact(count(queryWrapper));
    }

    @Override
    public boolean saveWebsiteVisit(Integer examId, Integer examineeAccountId, String url, String title, LocalDateTime visitTime) {
        // 创建网站访问记录
        MonitorWebsiteVisit websiteVisit = new MonitorWebsiteVisit();
        websiteVisit.setExamId(examId);
        websiteVisit.setExamineeAccountId(examineeAccountId);
        websiteVisit.setUrl(url);
        websiteVisit.setTitle(title);
        websiteVisit.setVisitTime(visitTime);
        websiteVisit.setCreatedAt(LocalDateTime.now());

        // 检查网站是否在黑名单中
        WebsiteBlacklistResult blacklistResult = checkWebsiteBlacklist(url, title);
        websiteVisit.setRiskLevel(blacklistResult.getRiskLevel());
        websiteVisit.setIsBlacklist(blacklistResult.isBlacklisted());

        // 如果网站在黑名单中，设置描述信息
        if (blacklistResult.isBlacklisted()) {
            websiteVisit.setDescription(blacklistResult.getReason());
        }

        return save(websiteVisit);
    }

    /**
     * 网站黑名单检查结果
     */
    private static class WebsiteBlacklistResult {
        private final boolean blacklisted;
        private final int riskLevel;
        private final String reason;

        public WebsiteBlacklistResult(boolean blacklisted, int riskLevel, String reason) {
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
     * 检查网站是否在黑名单中
     *
     * @param url 网站URL
     * @param title 网站标题
     * @return 黑名单检查结果
     */
    private WebsiteBlacklistResult checkWebsiteBlacklist(String url, String title) {
        if (url == null || url.isEmpty()) {
            return new WebsiteBlacklistResult(false, RISK_LEVEL_NORMAL, "");
        }

        // 提取域名
        String domain = extractDomain(url);

        // 如果域名为空，直接使用URL
        if (domain == null || domain.isEmpty()) {
            domain = url.toLowerCase();
        }

        // 检查域名是否在黑名单中
        for (String blacklistDomain : WEBSITE_BLACKLIST) {
            if (domain.contains(blacklistDomain.toLowerCase())) {
                return new WebsiteBlacklistResult(true, RISK_LEVEL_DANGER, "访问黑名单网站: " + blacklistDomain);
            }
        }

        // 检查URL是否包含黑名单关键字
        for (String keyword : WEBSITE_BLACKLIST_KEYWORDS) {
            if (url.toLowerCase().contains(keyword.toLowerCase())) {
                return new WebsiteBlacklistResult(true, RISK_LEVEL_WARNING, "访问可疑网站，URL包含关键字: " + keyword);
            }
        }

        // 检查标题是否包含黑名单关键字
        if (title != null && !title.isEmpty()) {
            for (String keyword : WEBSITE_BLACKLIST_KEYWORDS) {
                if (title.toLowerCase().contains(keyword.toLowerCase())) {
                    return new WebsiteBlacklistResult(true, RISK_LEVEL_WARNING, "访问可疑网站，标题包含关键字: " + keyword);
                }
            }
        }

        // 特殊情况检查：搜索引擎搜索
        if (domain.contains("baidu.com") || domain.contains("google.com") || domain.contains("bing.com")) {
            if (url.contains("q=") || url.contains("query=") || url.contains("word=")) {
                return new WebsiteBlacklistResult(true, RISK_LEVEL_WARNING, "使用搜索引擎进行搜索，可能查询考试相关内容");
            }
        }

        return new WebsiteBlacklistResult(false, RISK_LEVEL_NORMAL, "");
    }

    /**
     * 从URL中提取域名
     *
     * @param url URL
     * @return 域名
     */
    private String extractDomain(String url) {
        java.util.regex.Matcher matcher = DOMAIN_PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return url;
    }

    /**
     * 将实体转换为VO
     */
    private MonitorWebsiteVisitVO convertToVO(MonitorWebsiteVisit websiteVisit) {
        if (websiteVisit == null) {
            return null;
        }

        MonitorWebsiteVisitVO vo = new MonitorWebsiteVisitVO();
        vo.setId(websiteVisit.getId());
        vo.setUrl(websiteVisit.getUrl());
        vo.setTitle(websiteVisit.getTitle());
        vo.setVisitTime(websiteVisit.getVisitTime());
        vo.setTime(websiteVisit.getVisitTime().format(TIME_FORMATTER));
        vo.setDuration(websiteVisit.getDuration());
        vo.setRiskLevel(websiteVisit.getRiskLevel());
        vo.setRisk(websiteVisit.getRiskLevel() > 0 ? "warning" : "normal");
        vo.setDescription(websiteVisit.getDescription());
        vo.setIsBlacklist(websiteVisit.getIsBlacklist());

        return vo;
    }
} 