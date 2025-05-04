package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 考生网站访问记录实体类
 *
 * @author Aliothmoon
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_website_visit")
public class MonitorWebsiteVisit {

    /**
     * 主键ID
     */
    @Id
    @Column("id")
    private Integer id;

    /**
     * 考试ID
     */
    @Column("exam_id")
    private Integer examId;

    /**
     * 考生账号ID
     */
    @Column("examinee_account_id")
    private Integer examineeAccountId;

    /**
     * 学生ID
     */
    @Column("student_id")
    private Integer studentId;

    /**
     * 访问URL
     */
    @Column("url")
    private String url;

    /**
     * 网站标题
     */
    @Column("title")
    private String title;

    /**
     * 访问时间
     */
    @Column("visit_time")
    private LocalDateTime visitTime;

    /**
     * 停留时长(秒)
     */
    @Column("duration")
    private Integer duration;

    /**
     * 风险等级 0-低风险 1-中风险 2-高风险
     */
    @Column("risk_level")
    private Integer riskLevel;

    /**
     * 描述信息
     */
    @Column("description")
    private String description;

    /**
     * 是否黑名单网站
     */
    @Column("is_blacklist")
    private Boolean isBlacklist;

    /**
     * 创建时间
     */
    @Column("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column("updated_at")
    private LocalDateTime updatedAt;
} 