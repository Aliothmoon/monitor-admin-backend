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
 * 操作日志实体类
 *
 * @author Aliothmoon
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("operation_log")
public class OperationLog {

    /**
     * 日志ID
     */
    @Id(keyType = KeyType.Auto)
    @Column("log_id")
    private Integer logId;

    /**
     * 用户ID
     */
    @Column("user_id")
    private Integer userId;

    /**
     * 用户名
     */
    @Column("username")
    private String username;

    /**
     * 操作类型
     */
    @Column("operation")
    private String operation;

    /**
     * 请求方法
     */
    @Column("method")
    private String method;

    /**
     * 请求参数
     */
    @Column("params")
    private String params;

    /**
     * 请求路径
     */
    @Column("path")
    private String path;

    /**
     * IP地址
     */
    @Column("ip")
    private String ip;

    /**
     * 浏览器信息
     */
    @Column("browser")
    private String browser;

    /**
     * 操作系统
     */
    @Column("os")
    private String os;

    /**
     * 状态码
     */
    @Column("status")
    private Integer status;

    /**
     * 错误消息
     */
    @Column("error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @Column("operation_time")
    private LocalDateTime operationTime;

    /**
     * 耗时(毫秒)
     */
    @Column("duration")
    private Long duration;
} 