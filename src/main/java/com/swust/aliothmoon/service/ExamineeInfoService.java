package com.swust.aliothmoon.service;

import com.mybatisflex.core.service.IService;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.model.dto.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 考生信息服务接口
 *
 * @author Alioth
 *
 */
public interface ExamineeInfoService extends IService<ExamineeInfo> {

    /**
     * 获取分页数据
     *
     * @param page 分页参数
     * @return 分页数据
     */
    TableDataInfo<ExamineeInfo> getPageData(PageInfo page);

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
    TableDataInfo<ExamineeInfo> getPageData(int pageNum, int pageSize, String name, String studentId, String college, String className);

    /**
     * 检查姓名和学号是否已存在
     *
     * @param name 姓名
     * @param studentId 学号
     * @return 如果已存在返回true，否则返回false
     */
    boolean checkDuplicateNameAndStudentId(String name, String studentId);

    /**
     * 检查姓名和学号是否已存在（排除指定ID）
     *
     * @param name 姓名
     * @param studentId 学号
     * @param excludeId 需要排除的考生信息ID
     * @return 如果已存在返回true，否则返回false
     */
    boolean checkDuplicateNameAndStudentIdExcludeId(String name, String studentId, Integer excludeId);

    /**
     * 从Excel文件导入考生信息并关联到考试
     *
     * @param file Excel文件
     * @param examId 考试ID
     * @return 导入结果
     * @throws IOException 如果文件读取失败
     */
    List<Map<String, Object>> importExamineesFromExcel(MultipartFile file, Integer examId) throws IOException;

    /**
     * 生成并下载考生导入模板
     */
    void generateAndDownloadTemplate();

    /**
     * 导出考试的考生信息名单
     *
     * @param examId 考试ID
     * @param examName 考试名称
     * @param examLocation 考试地点
     */
    void exportExamineesInfo(Integer examId, String examName, String examLocation);
} 