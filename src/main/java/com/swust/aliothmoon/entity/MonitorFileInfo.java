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
@Table("monitor_file_info")
public class MonitorFileInfo {


    @Id
    @Column("file_id")
    private Integer fileId;

    @Column("file_name")
    private String fileName;

    @Column("file_key")
    private String fileKey;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("created_by")
    private Integer createdBy;

    @Column("updated_by")
    private Integer updatedBy;

}
