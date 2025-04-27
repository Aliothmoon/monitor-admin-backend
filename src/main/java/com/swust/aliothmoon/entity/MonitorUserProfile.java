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
 * 用户个人信息实体类
 *
 * @author Alioth
 * @since 2023-06-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_user_profile")
public class MonitorUserProfile {
    
    /**
     * 主键ID
     */
    @Id
    @Column("id")
    private Integer id;

    /**
     * 用户ID
     */
    @Column("user_id")
    private Integer userId;

    /**
     * 昵称
     */
    @Column("nickname")
    private String nickname;

    /**
     * 电子邮箱
     */
    @Column("email")
    private String email;

    /**
     * 手机号码
     */
    @Column("phone")
    private String phone;

    /**
     * 所在学院
     */
    @Column("college")
    private String college;

    /**
     * 所在系/部门
     */
    @Column("department")
    private String department;

    /**
     * 职称/职位
     */
    @Column("title")
    private String title;

    /**
     * 工号
     */
    @Column("employee_id")
    private String employeeId;

    /**
     * 个人简介
     */
    @Column("profile")
    private String profile;

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

    /**
     * 创建者ID
     */
    @Column("created_by")
    private Integer createdBy;

    /**
     * 更新者ID
     */
    @Column("updated_by")
    private Integer updatedBy;
} 