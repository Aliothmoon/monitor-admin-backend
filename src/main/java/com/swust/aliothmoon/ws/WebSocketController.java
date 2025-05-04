package com.swust.aliothmoon.ws;

import com.alibaba.fastjson2.JSON;
import com.swust.aliothmoon.model.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket相关接口
 */
@Slf4j
@RestController
@RequestMapping("/websocket")
public class WebSocketController {

    /**
     * 获取当前在线人数
     */
    @GetMapping("/online-count")
    public Result<Integer> getOnlineCount() {
        return Result.success(WebSocketServer.getOnlineCount());
    }

    /**
     * 获取在线用户列表
     */
    @GetMapping("/online-users")
    public Result<String[]> getOnlineUsers() {
        return Result.success(WebSocketServer.getOnlineUserIds());
    }

    /**
     * 获取WebSocket状态信息
     */
    @GetMapping("/status")
    public Result<Map<String, Object>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("onlineCount", WebSocketServer.getOnlineCount());
        status.put("onlineUsers", WebSocketServer.getOnlineUserIds());
        return Result.success(status);
    }

    /**
     * 向指定用户发送消息
     */
    @PostMapping("/send/{userId}")
    public Result<Boolean> sendToUser(@PathVariable("userId") String userId, @RequestBody String message) {
        try {
            // 构造WebSocket消息对象
            WebSocketMessage wsMessage = new WebSocketMessage(
                WebSocketMessage.MessageType.SYSTEM,
                message,
                "system"
            );
            
            // 发送消息
            boolean result = WebSocketServer.sendMessageToUser(userId, JSON.toJSONString(wsMessage));
            return Result.success(result);
        } catch (Exception e) {
            log.error("发送消息失败：{}", e.getMessage());
            return Result.fail("发送消息失败: " + e.getMessage());
        }
    }

    /**
     * 广播消息到所有在线用户
     */
    @PostMapping("/broadcast")
    public Result<Void> broadcast(@RequestBody String message) {
        try {
            // 构造WebSocket消息对象
            WebSocketMessage wsMessage = new WebSocketMessage(
                WebSocketMessage.MessageType.SYSTEM,
                message,
                "system"
            );
            
            // 广播消息
            WebSocketServer.broadcast(JSON.toJSONString(wsMessage));
            return Result.success();
        } catch (IOException e) {
            log.error("广播消息失败：{}", e.getMessage());
            return Result.fail("广播消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 发送系统通知
     */
    @PostMapping("/notification")
    public Result<Void> sendNotification(@RequestBody Map<String, Object> params) {
        try {
            String message = (String) params.getOrDefault("message", "");
            String title = (String) params.getOrDefault("title", "系统通知");
            Object data = params.get("data");
            
            // 构造WebSocket消息对象
            WebSocketMessage wsMessage = new WebSocketMessage(
                WebSocketMessage.MessageType.NOTIFICATION,
                message,
                "system"
            );
            wsMessage.setData(data);
            
            // 广播消息
            WebSocketServer.broadcast(JSON.toJSONString(wsMessage));
            return Result.success();
        } catch (IOException e) {
            log.error("发送通知失败：{}", e.getMessage());
            return Result.fail("发送通知失败: " + e.getMessage());
        }
    }
} 