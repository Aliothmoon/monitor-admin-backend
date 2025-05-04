package com.swust.aliothmoon.controller.monitor;

import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorScreenshot;
import com.swust.aliothmoon.service.MonitorScreenshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 截图管理控制器
 *
 * @author Aliothmoon
 */
@RestController
@RequestMapping("/monitor/screenshot")
@RequiredArgsConstructor
public class ScreenshotController {

    private final MonitorScreenshotService screenshotService;
    
    // 图片保存路径
    private static final String SCREENSHOT_DIR = "file/screenshots/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 上传截图
     *
     * @param accountId 考生账号ID
     * @param examId 考试ID
     * @param file 截图文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    public R<Map<String, Object>> uploadScreenshot(
            @RequestParam Integer accountId,
            @RequestParam Integer examId,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(required = false) String base64Data,
            @RequestParam(required = false, defaultValue = "false") Boolean hasWarning,
            @RequestParam(required = false) String analysis) {
        
        try {
            MonitorScreenshot screenshot = new MonitorScreenshot();
            screenshot.setExamineeAccountId(accountId);
            screenshot.setExamId(examId);
            screenshot.setCaptureTime(LocalDateTime.now());
            screenshot.setHasWarning(hasWarning);
            screenshot.setAnalysisResult(analysis);
            
            String screenshotUrl = null;
            
            // 处理文件上传
            if (file != null && !file.isEmpty()) {
                screenshotUrl = saveScreenshotFile(file, accountId, examId);
                screenshot.setScreenshotUrl(screenshotUrl);
                // 保持旧字段兼容
                screenshot.setImageUrl(screenshotUrl);
            } 
            // 处理Base64数据
            else if (base64Data != null && !base64Data.isEmpty()) {
                // 可以选择将Base64存入数据库或保存为文件
                if (base64Data.length() > 1000) { // 如果数据很长
                    screenshotUrl = saveBase64AsFile(base64Data, accountId, examId);
                    screenshot.setScreenshotUrl(screenshotUrl);
                    // 保持旧字段兼容
                    screenshot.setImageUrl(screenshotUrl);
                } else {
                    screenshot.setScreenshotData(base64Data);
                }
            } else {
                return R.failed("没有提供截图数据");
            }
            
            // 保存截图记录
            boolean success = screenshotService.addScreenshot(screenshot);
            
            if (success) {
                Map<String, Object> result = new HashMap<>();
                result.put("id", screenshot.getId());
                result.put("url", screenshotUrl);
                result.put("captureTime", screenshot.getCaptureTime());
                return R.ok(result);
            } else {
                return R.failed("保存截图记录失败");
            }
            
        } catch (Exception e) {
            return R.failed("上传截图失败: " + e.getMessage());
        }
    }
    
    /**
     * 保存截图文件
     *
     * @param file 截图文件
     * @param accountId 考生账号ID
     * @param examId 考试ID
     * @return 文件URL
     * @throws IOException 如果保存失败
     */
    private String saveScreenshotFile(MultipartFile file, Integer accountId, Integer examId) throws IOException {
        // 创建目录
        String today = LocalDateTime.now().format(DATE_FORMATTER);
        String dirPath = SCREENSHOT_DIR + today + "/" + examId + "/" + accountId + "/";
        Path directory = Paths.get(dirPath);
        Files.createDirectories(directory);
        
        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID().toString() + extension;
        
        // 保存文件
        Path filePath = directory.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        
        // 返回相对URL
        return dirPath + fileName;
    }
    
    /**
     * 将Base64数据保存为文件
     *
     * @param base64Data Base64编码的数据
     * @param accountId 考生账号ID
     * @param examId 考试ID
     * @return 文件URL
     * @throws IOException 如果保存失败
     */
    private String saveBase64AsFile(String base64Data, Integer accountId, Integer examId) throws IOException {
        // 处理可能包含的前缀
        String data = base64Data;
        if (data.contains(",")) {
            data = data.split(",")[1];
        }
        
        // 解码Base64数据
        byte[] imageBytes = Base64.getDecoder().decode(data);
        
        // 创建目录
        String today = LocalDateTime.now().format(DATE_FORMATTER);
        String dirPath = SCREENSHOT_DIR + today + "/" + examId + "/" + accountId + "/";
        Path directory = Paths.get(dirPath);
        Files.createDirectories(directory);
        
        // 生成文件名
        String fileName = UUID.randomUUID().toString() + ".jpg";
        
        // 保存文件
        Path filePath = directory.resolve(fileName);
        Files.write(filePath, imageBytes);
        
        // 返回相对URL
        return dirPath + fileName;
    }
    
    /**
     * 删除截图
     *
     * @param id 截图ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> deleteScreenshot(@PathVariable Integer id) {
        MonitorScreenshot screenshot = screenshotService.getById(id);
        if (screenshot == null) {
            return R.failed("截图不存在");
        }
        
        // 删除文件
        String fileUrl = screenshot.getScreenshotUrl();
        if (fileUrl == null || fileUrl.isEmpty()) {
            fileUrl = screenshot.getImageUrl(); // 兼容旧字段
        }
        
        if (fileUrl != null && !fileUrl.isEmpty()) {
            try {
                Path filePath = Paths.get(fileUrl);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                // 文件删除失败，但仍然可以删除数据库记录
                System.err.println("删除截图文件失败: " + e.getMessage());
            }
        }
        
        // 删除数据库记录
        boolean success = screenshotService.removeById(id);
        return R.ok(success);
    }
} 