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
 *  实体类。
 *
 * @author Aliothmoon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("monitor_behavior_record")
public class MonitorBehaviorRecord {


    @Id
    @Column("record_id")
    private Integer recordId;

    @Column("user_id")
    private Integer userId;

    @Column("behavior_type")
    private Integer behaviorType;

    @Column("behavior_detail")
    private String behaviorDetail;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("created_by")
    private Integer createdBy;

    @Column("updated_by")
    private Integer updatedBy;

}
