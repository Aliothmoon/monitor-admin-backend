package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeAccount;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.entity.table.ExamineeAccountTableDef;
import com.swust.aliothmoon.entity.table.ExamineeInfoTableDef;
import com.swust.aliothmoon.mapper.ExamineeInfoMapper;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.service.ExamineeAccountService;
import com.swust.aliothmoon.service.ExamineeInfoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swust.aliothmoon.controller.exam.MonitorExamController.getRandomPassword;

/**
 * 考生信息服务实现类
 *
 * @author Aliothmoon
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExamineeInfoServiceImpl extends ServiceImpl<ExamineeInfoMapper, ExamineeInfo> implements ExamineeInfoService {

    private final ExamineeAccountService examineeAccountService;

    @Override
    public TableDataInfo<ExamineeInfo> getPageData(PageInfo page) {
        Page<ExamineeInfo> p = page(page.toPage());
        return TableDataInfo.of(p);
    }

    @Override
    public TableDataInfo<ExamineeInfo> getPageData(int pageNum, int pageSize, String name, String studentId, String college, String className) {
        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;

        QueryChain<ExamineeInfo> queryChain = QueryChain.of(ExamineeInfo.class);

        // 添加查询条件
        if (StringUtils.hasText(name)) {
            queryChain.where(examineeInfo.NAME.like(name));
        }

        if (StringUtils.hasText(studentId)) {
            queryChain.where(examineeInfo.STUDENT_ID.like(studentId));
        }

        if (StringUtils.hasText(college)) {
            queryChain.where(examineeInfo.COLLEGE.like(college));
        }

        if (StringUtils.hasText(className)) {
            queryChain.where(examineeInfo.CLASS_NAME.like(className));
        }

        // 按创建时间排序
        queryChain.orderBy(examineeInfo.CREATED_AT.desc());

        // 执行分页查询
        Page<ExamineeInfo> page = new Page<>(pageNum, pageSize);
        Page<ExamineeInfo> result = page(page, queryChain);

        return TableDataInfo.of(result);
    }

    @Override
    public boolean checkDuplicateNameAndStudentId(String name, String studentId) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(studentId)) {
            return false;
        }

        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;

        // 查询姓名和学号完全匹配的记录
        return QueryChain.of(ExamineeInfo.class)
                .where(examineeInfo.NAME.eq(name, StringUtils.hasText(name)))
                .and(examineeInfo.STUDENT_ID.eq(studentId))
                .exists();
    }

    @Override
    public boolean checkDuplicateNameAndStudentIdExcludeId(String name, String studentId, Integer excludeId) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(studentId) || excludeId == null) {
            return false;
        }

        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;

        // 查询姓名和学号完全匹配且ID不等于excludeId的记录
        return QueryChain.of(ExamineeInfo.class)
                .where(examineeInfo.NAME.eq(name))
                .and(examineeInfo.STUDENT_ID.eq(studentId))
                .and(examineeInfo.EXAMINEE_INFO_ID.ne(excludeId))
                .exists();
    }

    @Override
    public ExamineeInfo checkDuplicateStudentId(String studentId) {
        if (!StringUtils.hasText(studentId)) {
            return null;
        }

        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;

        // 查询学号匹配的记录
        return QueryChain.of(ExamineeInfo.class)
                .where(examineeInfo.STUDENT_ID.eq(studentId))
                .one();
    }

    @Override
    public boolean checkDuplicateStudentIdExcludeId(String studentId, Integer excludeId) {
        if (!StringUtils.hasText(studentId) || excludeId == null) {
            return false;
        }

        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;

        // 查询学号匹配且ID不等于excludeId的记录
        return QueryChain.of(ExamineeInfo.class)
                .where(examineeInfo.STUDENT_ID.eq(studentId))
                .and(examineeInfo.EXAMINEE_INFO_ID.ne(excludeId))
                .exists();
    }

    @Override
    @Transactional
    public List<Map<String, Object>> importExamineesFromExcel(MultipartFile file, Integer examId) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 获取操作者ID
        Integer userId = UserInfoContext.get().getUserId();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // 跳过表头行
            int startRow = 1;
            int totalRows = sheet.getPhysicalNumberOfRows();

            for (int i = startRow; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("rowNum", i + 1);

                try {
                    // 读取各字段
                    String studentId = getStringCellValue(row.getCell(0));
                    String name = getStringCellValue(row.getCell(1));
                    String college = getStringCellValue(row.getCell(2));
                    String className = getStringCellValue(row.getCell(3));

                    // 数据校验
                    if (!StringUtils.hasText(studentId) || !StringUtils.hasText(name)) {
                        resultMap.put("status", "失败");
                        resultMap.put("message", "学号和姓名不能为空");
                        resultList.add(resultMap);
                        continue;
                    }

                    // 首先检查学号是否已存在
                    ExamineeInfo existingExamineeByStudentId = checkDuplicateStudentId(studentId);

                    ExamineeInfo examineeInfo;
                    if (existingExamineeByStudentId != null) {
                        // 如果学号已存在，获取已有记录并更新信息
                        examineeInfo = existingExamineeByStudentId;

                        // 更新考生信息
                        examineeInfo.setName(name); // 更新姓名
                        examineeInfo.setCollege(college);
                        examineeInfo.setClassName(className);
                        examineeInfo.setUpdatedAt(LocalDateTime.now());
                        examineeInfo.setUpdatedBy(userId);

                        // 保存更新后的信息
                        updateById(examineeInfo);

                        resultMap.put("status", "更新");
                        if (existingExamineeByStudentId.getName().equals(name)) {
                            resultMap.put("message", "考生信息已存在，更新成功");
                        } else {
                            resultMap.put("message", "学号已存在但姓名不同，已更新姓名为：" + name);
                        }
                    } else {
                        // 如果不存在，创建新记录
                        examineeInfo = new ExamineeInfo();
                        examineeInfo.setStudentId(studentId);
                        examineeInfo.setName(name);
                        examineeInfo.setCollege(college);
                        examineeInfo.setClassName(className);
                        examineeInfo.setCreatedAt(LocalDateTime.now());
                        examineeInfo.setUpdatedAt(LocalDateTime.now());
                        examineeInfo.setCreatedBy(userId);
                        examineeInfo.setUpdatedBy(userId);

                        // 保存到数据库
                        save(examineeInfo);

                        resultMap.put("status", "新增");
                        resultMap.put("message", "考生信息新增成功");
                    }

                    // 检查是否已有考试账号
                    List<ExamineeAccount> existingAccounts = examineeAccountService.getByExamineeInfoIdAndExamId(
                            examineeInfo.getExamineeInfoId(), examId);

                    if (existingAccounts.isEmpty()) {
                        // 创建考试账号
                        ExamineeAccount account = new ExamineeAccount();
                        account.setExamineeInfoId(examineeInfo.getExamineeInfoId());
                        account.setExamId(examId);
                        account.setAccount(studentId); // 使用学号作为默认账号
                        account.setPassword(generateRandomPassword(8)); // 生成6位随机密码
                        account.setStatus(1); // 未登录状态
                        account.setCreatedAt(LocalDateTime.now());
                        account.setUpdatedAt(LocalDateTime.now());
                        account.setCreatedBy(userId);
                        account.setUpdatedBy(userId);

                        examineeAccountService.save(account);

                        resultMap.put("account", account.getAccount());
                        resultMap.put("password", account.getPassword());
                    } else {
                        // 已有账号，不重复创建
                        ExamineeAccount existingAccount = existingAccounts.get(0);
                        resultMap.put("account", existingAccount.getAccount());
                        resultMap.put("password", existingAccount.getPassword()); // 不显示已有密码
                        resultMap.put("message", resultMap.get("message") + "，但该考生已有考试账号");
                    }

                    // 添加考生信息到结果
                    resultMap.put("studentId", studentId);
                    resultMap.put("name", name);
                    resultMap.put("college", college);
                    resultMap.put("className", className);

                } catch (Exception e) {
                    resultMap.put("status", "失败");
                    resultMap.put("message", "导入错误: " + e.getMessage());
                }

                resultList.add(resultMap);
            }
        }

        return resultList;
    }

    @Override
    public void generateAndDownloadTemplate() {
        try {
            // 获取HttpServletResponse
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("考生导入模板.xlsx", StandardCharsets.UTF_8));

            // 创建工作簿
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("考生信息");

                // 创建表头行
                Row headerRow = sheet.createRow(0);

                // 表头样式
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);

                // 创建表头
                String[] headers = {"学号*", "姓名*", "学院", "班级"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                    sheet.setColumnWidth(i, 256 * 20); // 设置列宽
                }

                // 添加一行示例数据
                Row exampleRow = sheet.createRow(1);
                exampleRow.createCell(0).setCellValue("2023001001");
                exampleRow.createCell(1).setCellValue("张三");
                exampleRow.createCell(2).setCellValue("计算机学院");
                exampleRow.createCell(3).setCellValue("计算机科学与技术2301班");

                // 写入响应流
                try (OutputStream outputStream = response.getOutputStream()) {
                    workbook.write(outputStream);
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            log.error("生成模板失败", e);
        }
    }

    /**
     * 获取单元格的字符串值（处理不同类型的单元格）
     */
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                } else {
                    // 对于数字，特别是学号，避免科学计数法
                    double value = cell.getNumericCellValue();
                    if (value == Math.floor(value)) {
                        return String.format("%.0f", value);
                    } else {
                        return String.valueOf(value);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * 生成随机密码
     */
    private String generateRandomPassword(int length) {
        return getRandomPassword(length);
    }

    @Override
    public void exportExamineesInfo(Integer examId, String examName, String examLocation) {
        try {
            // 获取HttpServletResponse
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = URLEncoder.encode(examName + "-考生名单.xlsx", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            // 创建工作簿
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("考生名单");

                // 创建表头行
                Row headerRow = sheet.createRow(0);

                // 表头样式
                CellStyle headerStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);

                // 创建表头
                String[] headers = {
                        "序号", "学号", "姓名", "学院", "班级", "考试账号", "密码",
                        "考试名称", "考试地点"
                };
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(headerStyle);
                    sheet.setColumnWidth(i, 256 * 20); // 设置列宽
                }

                // 获取考生账号信息（没有分页，获取全部）
                ExamineeAccountTableDef examineeAccount = ExamineeAccountTableDef.EXAMINEE_ACCOUNT;
                ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;

                // 先获取考试的所有考生账号
                List<ExamineeAccount> accounts = examineeAccountService.getByExamId(examId);

                // 写入数据行
                int rowNum = 1;
                String[] statusMap = {"未登录", "已登录", "已交卷"};

                for (ExamineeAccount account : accounts) {
                    Row dataRow = sheet.createRow(rowNum++);

                    // 获取考生信息
                    ExamineeInfo info = null;
                    if (account.getExamineeInfoId() != null) {
                        info = getById(account.getExamineeInfoId());
                    }

                    // 序号
                    dataRow.createCell(0).setCellValue(rowNum - 1);

                    // 学号
                    dataRow.createCell(1).setCellValue(info != null && info.getStudentId() != null ? info.getStudentId() : "");

                    // 姓名
                    dataRow.createCell(2).setCellValue(info != null && info.getName() != null ? info.getName() : "");

                    // 学院
                    dataRow.createCell(3).setCellValue(info != null && info.getCollege() != null ? info.getCollege() : "");

                    // 班级
                    dataRow.createCell(4).setCellValue(info != null && info.getClassName() != null ? info.getClassName() : "");

                    // 考试账号
                    dataRow.createCell(5).setCellValue(account.getAccount() != null ? account.getAccount() : "");

                    // 密码
                    dataRow.createCell(6).setCellValue(account.getPassword()); // 出于安全考虑不导出明文密码

                    // 考试名称
                    dataRow.createCell(7).setCellValue(examName);

                    // 考试地点
                    dataRow.createCell(8).setCellValue(examLocation);

                }

                // 写入响应流
                try (OutputStream outputStream = response.getOutputStream()) {
                    workbook.write(outputStream);
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            log.error("导出考生名单失败", e);
            throw new RuntimeException("导出考生名单失败: " + e.getMessage());
        }
    }
} 