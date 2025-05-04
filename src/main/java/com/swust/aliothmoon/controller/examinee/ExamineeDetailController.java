package com.swust.aliothmoon.controller.examinee;

import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.entity.MonitorExam;
import com.swust.aliothmoon.model.vo.ExamineeDetailVO;
import com.swust.aliothmoon.service.ExamineeAccountService;
import com.swust.aliothmoon.service.ExamineeInfoService;
import com.swust.aliothmoon.service.MonitorExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考生详情Controller
 *
 * @author Aliothmoon
 *
 */
@RestController
@RequestMapping("/examinee")
@RequiredArgsConstructor
public class ExamineeDetailController {

    private final ExamineeInfoService examineeInfoService;
    private final ExamineeAccountService examineeAccountService;
    private final MonitorExamService examService;

    /**
     * 获取考生详细信息，用于监控页面
     *
     * @param id 考生信息ID
     * @return 考生详情
     */
    @GetMapping("/detail/{id}")
    public R<ExamineeDetailVO> getExamineeDetail(@PathVariable Integer id) {
        // 查询考生基本信息
        ExamineeInfo examineeInfo = examineeInfoService.getById(id);
        if (examineeInfo == null) {
            return R.failed("考生信息不存在");
        }

        // 查询考生账号信息
        List<ExamineeAccount> accounts = examineeAccountService.getByExamineeInfoId(id);
        
        // 创建返回对象
        ExamineeDetailVO detailVO = new ExamineeDetailVO();
        detailVO.setExamineeInfoId(examineeInfo.getExamineeInfoId());
        detailVO.setName(examineeInfo.getName());
        detailVO.setStudentId(examineeInfo.getStudentId());
        detailVO.setCollege(examineeInfo.getCollege());
        detailVO.setClassName(examineeInfo.getClassName());
        
        // 如果有账号信息，获取第一个账号关联的考试信息
        if (!accounts.isEmpty()) {
            ExamineeAccount account = accounts.get(0);
            detailVO.setAccountId(account.getAccountId());
            detailVO.setStatus(account.getStatus());
            
            // 获取考试信息
            if (account.getExamId() != null) {
                MonitorExam exam = examService.getById(account.getExamId());
                if (exam != null) {
                    detailVO.setExamId(exam.getId());
                    detailVO.setExamName(exam.getName());
                }
            }
        }
        
        // 模拟一些监控数据
        detailVO.setLastActivity(System.currentTimeMillis());
        detailVO.setScreenshot(""); // 这里需要从实际存储中获取
        detailVO.setSwitchCount(0);
        
        return R.ok(detailVO);
    }
} 