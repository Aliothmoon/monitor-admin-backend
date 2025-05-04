package com.swust.aliothmoon.ws;

import lombok.Data;

/**
 * WebSocket消息实体类
 */
@Data
public class WebSocketMessage {

    /**
     * 消息类型常量
     */
    public static final class MessageType {
        /**
         * 连接成功消息
         */
        public static final String CONNECT = "CONNECT";
        
        /**
         * 系统消息
         */
        public static final String SYSTEM = "SYSTEM";
        
        /**
         * 心跳消息
         */
        public static final String HEARTBEAT = "HEARTBEAT";
        
        /**
         * 私聊消息
         */
        public static final String PRIVATE = "PRIVATE";
        
        /**
         * 广播消息
         */
        public static final String BROADCAST = "BROADCAST";
        
        /**
         * 通知消息
         */
        public static final String NOTIFICATION = "NOTIFICATION";
        
        /**
         * 状态更新消息
         */
        public static final String STATUS_UPDATE = "STATUS_UPDATE";
        
        /**
         * 硬件状态消息
         */
        public static final String HARDWARE_STATS = "HARDWARE_STATS";
        
        /**
         * 考试状态消息
         */
        public static final String EXAM_STATUS = "EXAM_STATUS";
        
        /**
         * 命令消息
         */
        public static final String COMMAND = "COMMAND";
        
        /**
         * 请求状态更新
         */
        public static final String REQUEST_STATUS = "REQUEST_STATUS";
        
        // 私有构造方法，防止实例化
        private MessageType() {
        }
    }

    /**
     * 消息类型
     */
    private String type;
    
    /**
     * 消息内容
     */
    private String message;
    
    /**
     * 目标用户ID（私聊时使用）
     */
    private String targetUserId;
    
    /**
     * 发送者用户ID
     */
    private String fromUserId;
    
    /**
     * 附加数据
     */
    private Object data;
    
    /**
     * 发送时间戳
     */
    private long timestamp;

    /**
     * 构造函数
     */
    public WebSocketMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 构造函数
     * @param type 消息类型
     * @param message 消息内容
     */
    public WebSocketMessage(String type, String message) {
        this.type = type;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * 构造函数
     * @param type 消息类型
     * @param message 消息内容
     * @param fromUserId 发送者ID
     */
    public WebSocketMessage(String type, String message, String fromUserId) {
        this.type = type;
        this.message = message;
        this.fromUserId = fromUserId;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * 构造函数
     * @param type 消息类型
     * @param message 消息内容
     * @param fromUserId 发送者ID
     * @param targetUserId 接收者ID
     */
    public WebSocketMessage(String type, String message, String fromUserId, String targetUserId) {
        this.type = type;
        this.message = message;
        this.fromUserId = fromUserId;
        this.targetUserId = targetUserId;
        this.timestamp = System.currentTimeMillis();
    }
} 