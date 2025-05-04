package com.swust.aliothmoon.ws;

import com.alibaba.fastjson2.JSON;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import static com.swust.aliothmoon.ws.WebSocketMessage.MessageType.*;

/**
 * WebSocket服务端点
 * 路径为/ws/monitor/{userId}，userId是连接的唯一标识
 */
@Slf4j
@Component
@ServerEndpoint("/ws/monitor/{userId}")
public class WebSocketServer {

    /**
     * 记录当前在线连接数
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端，key是userId，value是WebSocket连接
     */
    private static final Map<String, WebSocketServer> CLIENTS = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，用于给客户端发送数据
     */
    private Session session;

    /**
     * 当前连接的用户ID
     */
    private String userId;

    private String examId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;

        // 如果已存在相同userId的连接，先关闭旧连接
        if (CLIENTS.containsKey(userId)) {
            WebSocketServer oldClient = CLIENTS.get(userId);
            try {
                oldClient.session.close();
                log.info("用户{}的旧连接已关闭", userId);
            } catch (IOException e) {
                log.error("关闭旧连接失败: {}", e.getMessage());
            }
            // 移除旧连接
            CLIENTS.remove(userId);
            ONLINE_COUNT.decrementAndGet();
        }

        // 添加新连接
        CLIENTS.put(userId, this);
        ONLINE_COUNT.incrementAndGet();
        log.info("用户{}连接成功，当前在线人数为：{}", userId, ONLINE_COUNT.get());

        try {
            // 连接成功发送消息
            sendMessage(JSON.toJSONString(new WebSocketMessage(WebSocketMessage.MessageType.CONNECT, "连接成功")));
            // 广播在线人数更新
            broadcastOnlineCount();
        } catch (IOException e) {
            log.error("用户{}连接异常：{}", userId, e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (CLIENTS.containsKey(userId)) {
            CLIENTS.remove(userId);
            ONLINE_COUNT.decrementAndGet();
            log.info("用户{}断开连接，当前在线人数为：{}", userId, ONLINE_COUNT.get());
            
            // 广播在线人数更新
            try {
                broadcastOnlineCount();
            } catch (IOException e) {
                log.error("广播在线人数失败：{}", e.getMessage());
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到用户{}的消息：{}", userId, message);
        
        try {
            // 解析消息
            WebSocketMessage wsMessage = JSON.parseObject(message, WebSocketMessage.class);
            
            // 处理不同类型的消息
            switch (wsMessage.getType()) {
                case HEARTBEAT:
                    // 心跳消息，返回pong
                    sendMessage(JSON.toJSONString(new WebSocketMessage(HEARTBEAT, "pong")));
                    break;
                    
                case PRIVATE:
                    // 点对点消息，转发给指定用户
                    String targetUserId = wsMessage.getTargetUserId();
                    if (targetUserId != null && CLIENTS.containsKey(targetUserId)) {
                        CLIENTS.get(targetUserId).sendMessage(message);
                    }
                    break;
                    
                case BROADCAST:
                    // 广播消息，转发给所有用户
                    broadcast(message);
                    break;
                    
                default:
                    log.warn("未知消息类型：{}", wsMessage.getType());
            }
        } catch (Exception e) {
            log.error("处理消息失败：{}", e.getMessage());
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户{}连接发生错误：{}", userId, error.getMessage());
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            log.error("关闭连接失败：{}", e.getMessage());
        }
        
        // 移除客户端连接
        if (CLIENTS.containsKey(userId)) {
            CLIENTS.remove(userId);
            ONLINE_COUNT.decrementAndGet();
            
            // 广播在线人数更新
            try {
                broadcastOnlineCount();
            } catch (IOException e) {
                log.error("广播在线人数失败：{}", e.getMessage());
            }
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage(String message) throws IOException {
        if (this.session != null && this.session.isOpen()) {
            this.session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 广播消息给所有客户端
     */
    public static void broadcast(String message) throws IOException {
        for (WebSocketServer client : CLIENTS.values()) {
            client.sendMessage(message);
        }
    }
    
    /**
     * 广播在线人数消息
     */
    private void broadcastOnlineCount() throws IOException {
        WebSocketMessage message = new WebSocketMessage(
            WebSocketMessage.MessageType.SYSTEM,
            String.valueOf(ONLINE_COUNT.get())
        );
        message.setData(ONLINE_COUNT.get());
        broadcast(JSON.toJSONString(message));
    }

    /**
     * 获取当前在线人数
     */
    public static int getOnlineCount() {
        return ONLINE_COUNT.get();
    }
    
    /**
     * 获取所有在线用户ID
     */
    public static String[] getOnlineUserIds() {
        return CLIENTS.keySet().toArray(new String[0]);
    }
    
    /**
     * 向指定用户发送消息
     */
    public static boolean sendMessageToUser(String userId, String message) {
        if (CLIENTS.containsKey(userId)) {
            try {
                CLIENTS.get(userId).sendMessage(message);
                return true;
            } catch (IOException e) {
                log.error("发送消息给用户{}失败：{}", userId, e.getMessage());
                return false;
            }
        }
        return false;
    }
} 