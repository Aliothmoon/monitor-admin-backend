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
 *  实体类。
 *
 * @author Alioth
 * @since 2025-04-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_user")
public class MonitorUser {


    @Id(keyType = KeyType.Auto)
    @Column("user_id")
    private Integer userId;

    @Column("username")
    private String username;

    @Column("account")
    private String account;

    @Column("password")
    private String password;

    @Column("role_id")
    private Integer roleId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("created_by")
    private String createdBy;

    @Column("updated_by")
    private String updatedBy;

}
