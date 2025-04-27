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
 * 考生信息实体类
 *
 * @author Alioth
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("examinee_info")
public class ExamineeInfo {

    /**
     * 考生信息ID
     */
    @Id(keyType = KeyType.Auto)
    @Column("examinee_info_id")
    private Integer examineeInfoId;

    /**
     * 学号
     */
    @Column("student_id")
    private String studentId;

    /**
     * 姓名
     */
    @Column("name")
    private String name;

    /**
     * 性别（0-女, 1-男）
     */
    @Column("gender")
    private Integer gender;

    /**
     * 学院
     */
    @Column("college")
    private String college;

    /**
     * 班级
     */
    @Column("class_name")
    private String className;

    /**
     * 专业
     */
    @Column("major")
    private String major;

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