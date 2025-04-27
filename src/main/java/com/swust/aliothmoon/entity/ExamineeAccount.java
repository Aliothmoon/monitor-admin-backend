package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 考生账号实体类
 *
 * @author Alioth
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("examinee_account")
public class ExamineeAccount {

    /**
     * 考生账号ID
     */
    @Id(keyType = KeyType.Auto)
    @Column("account_id")
    private Integer accountId;

    /**
     * 考生信息ID
     */
    @Column("examinee_info_id")
    private Integer examineeInfoId;

    /**
     * 考试ID
     */
    @Column("exam_id")
    private Integer examId;

    /**
     * 账号
     */
    @Column("account")
    private String account;

    /**
     * 密码
     */
    @Column("password")
    private String password;

    /**
     * 状态（0-禁用, 1-启用）
     */
    @Column("status")
    private Integer status;

    /**
     * 上次登录时间
     */
    @Column("last_login_time")
    private LocalDateTime lastLoginTime;

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