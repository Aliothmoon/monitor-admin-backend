package com.swust.aliothmoon.controller.examinee;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.service.ExamineeInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 考生信息Controller
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/examinee/info")
@RequiredArgsConstructor
public class ExamineeInfoController {

    private final ExamineeInfoService examineeInfoService;

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<ExamineeInfo> page(Page<ExamineeInfo> page) {
        return examineeInfoService.page(page);
    }

    /**
     * 获取分页数据
     *
     * @param page 分页参数
     * @return 分页数据
     */
    @PostMapping("/getPageData")
    public TableDataInfo<ExamineeInfo> getPageData(@RequestBody PageInfo page) {
        return examineeInfoService.getPageData(page);
    }

    /**
     * 根据条件获取分页数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param name 姓名（可选）
     * @param studentId 学号（可选）
     * @param college 学院（可选）
     * @param className 班级（可选）
     * @return 分页数据
     */
    @GetMapping("/getExamineeInfoPageData")
    public TableDataInfo<ExamineeInfo> getExamineeInfoPageData(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) String className) {
        return examineeInfoService.getPageData(pageNum, pageSize, name, studentId, college, className);
    }

    /**
     * 保存考生信息
     *
     * @param examineeInfo 考生信息
     * @return 保存结果
     */
    @PostMapping("/saveExamineeInfo")
    @Transactional
    public R<Boolean> saveExamineeInfo(@RequestBody ExamineeInfo examineeInfo) {
        // 检查学号是否重复
        String name = examineeInfo.getName();
        String studentId = examineeInfo.getStudentId();
        if (studentId != null) {
            ExamineeInfo existingExaminee = examineeInfoService.checkDuplicateStudentId(studentId);
            if (existingExaminee != null) {
                // 如果学号已存在，更新考生信息而不是报错
                existingExaminee.setName(name);
                existingExaminee.setCollege(examineeInfo.getCollege());
                existingExaminee.setClassName(examineeInfo.getClassName());
                existingExaminee.setUpdatedAt(LocalDateTime.now());
                existingExaminee.setUpdatedBy(UserInfoContext.get().getUserId());

                boolean success = examineeInfoService.updateById(existingExaminee);
                return R.ok(success);
            }
        }

        // 设置创建和更新时间
        LocalDateTime now = LocalDateTime.now();
        examineeInfo.setCreatedAt(now);
        examineeInfo.setUpdatedAt(now);
        // 设置创建者和更新者
        Integer userId = UserInfoContext.get().getUserId();
        examineeInfo.setCreatedBy(userId);
        examineeInfo.setUpdatedBy(userId);

        boolean success = examineeInfoService.save(examineeInfo);
        return R.ok(success);
    }

    /**
     * 更新考生信息
     *
     * @param examineeInfo 考生信息
     * @return 更新结果
     */
    @PutMapping("/updateExamineeInfo")
    public R<Boolean> updateExamineeInfo(@RequestBody ExamineeInfo examineeInfo) {
        // 检查学号是否重复(排除自身)
        String studentId = examineeInfo.getStudentId();
        Integer examineeInfoId = examineeInfo.getExamineeInfoId();
        if (studentId != null && examineeInfoId != null) {
            boolean isDuplicate = examineeInfoService.checkDuplicateStudentIdExcludeId(
                    studentId, examineeInfoId);
            if (isDuplicate) {
                return R.failed("学号已存在，请勿重复添加");
            }
        }

        // 设置更新时间和更新者
        examineeInfo.setUpdatedAt(LocalDateTime.now());
        examineeInfo.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = examineeInfoService.updateById(examineeInfo);
        return R.ok(success);
    }

    /**
     * 删除考生信息
     *
     * @param examineeInfoId 考生信息ID
     * @return 删除结果
     */
    @DeleteMapping("/removeExamineeInfo/{examineeInfoId}")
    public R<Boolean> removeExamineeInfo(@PathVariable Integer examineeInfoId) {
        boolean success = examineeInfoService.removeById(examineeInfoId);
        return R.ok(success);
    }

    /**
     * 根据ID获取考生信息
     *
     * @param examineeInfoId 考生信息ID
     * @return 考生信息
     */
    @GetMapping("/getExamineeInfo/{examineeInfoId}")
    public R<ExamineeInfo> getExamineeInfo(@PathVariable Integer examineeInfoId) {
        ExamineeInfo examineeInfo = examineeInfoService.getById(examineeInfoId);
        return R.ok(examineeInfo);
    }
} 