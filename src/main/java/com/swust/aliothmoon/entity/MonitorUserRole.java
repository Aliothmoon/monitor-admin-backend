package com.swust.aliothmoon.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  实体类。
 *
 * @author Aliothmoon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_user_role")
public class MonitorUserRole implements Serializable {


    @Id
    private Integer userId;

    @Id
    private Integer roleId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

}
