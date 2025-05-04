package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorRiskImageTemplate;
import com.swust.aliothmoon.model.riskimage.RiskImageTemplateAddDTO;
import com.swust.aliothmoon.model.riskimage.RiskImageTemplateQueryDTO;
import com.swust.aliothmoon.model.riskimage.RiskImageTemplateUpdateDTO;
import com.swust.aliothmoon.model.riskimage.RiskImageTemplateVO;
import com.swust.aliothmoon.service.MonitorRiskImageTemplateService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorRiskImageTemplateTableDef.MONITOR_RISK_IMAGE_TEMPLATE;

/**
 * 风险图片模板控制器
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/exam/risk-image-template")
@RequiredArgsConstructor
public class RiskImageTemplateController {

    private final MonitorRiskImageTemplateService riskImageTemplateService;

    /**
     * 分页查询风险图片模板
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<RiskImageTemplateVO>> page(@RequestBody RiskImageTemplateQueryDTO queryDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create();

        // 添加查询条件
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            queryWrapper.and(MONITOR_RISK_IMAGE_TEMPLATE.NAME.like(queryDTO.getKeyword())
                    .or(MONITOR_RISK_IMAGE_TEMPLATE.DESCRIPTION.like(queryDTO.getKeyword())));
        }

        if (StringUtils.hasText(queryDTO.getCategory())) {
            queryWrapper.and(MONITOR_RISK_IMAGE_TEMPLATE.CATEGORY.eq(queryDTO.getCategory()));
        }

        // 按创建时间降序排序
        queryWrapper.orderBy(MONITOR_RISK_IMAGE_TEMPLATE.CREATED_AT.desc());

        // 分页查询
        Page<MonitorRiskImageTemplate> page = riskImageTemplateService.page(
                new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                queryWrapper
        );

        // 转换为VO
        Page<RiskImageTemplateVO> result = new Page<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalRow(page.getTotalRow());
        result.setTotalPage(page.getTotalPage());

        List<RiskImageTemplateVO> records = TransferUtils.toList(page.getRecords(), RiskImageTemplateVO.class);
        result.setRecords(records);

        return R.ok(result);
    }

    /**
     * 根据ID查询风险图片模板
     *
     * @param id 模板ID
     * @return 模板信息
     */
    @GetMapping("/{id}")
    public R<RiskImageTemplateVO> getById(@PathVariable Integer id) {
        MonitorRiskImageTemplate template = riskImageTemplateService.getById(id);
        if (template == null) {
            return R.failed("模板不存在");
        }
        RiskImageTemplateVO vo = TransferUtils.to(template, RiskImageTemplateVO.class);
        return R.ok(vo);
    }

    /**
     * 根据分类查询风险图片模板列表
     *
     * @param category 分类
     * @return 模板列表
     */
    @GetMapping("/list/category/{category}")
    public R<List<RiskImageTemplateVO>> listByCategory(@PathVariable String category) {
        List<MonitorRiskImageTemplate> templates = riskImageTemplateService.listByCategory(category);
        List<RiskImageTemplateVO> vos = TransferUtils.toList(templates, RiskImageTemplateVO.class);
        return R.ok(vos);
    }

    /**
     * 根据关键词查询风险图片模板列表
     *
     * @param keyword 关键词
     * @return 模板列表
     */
    @GetMapping("/list/keyword/{keyword}")
    public R<List<RiskImageTemplateVO>> listByKeyword(@PathVariable String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return R.failed("关键词不能为空");
        }
        List<MonitorRiskImageTemplate> templates = riskImageTemplateService.listByKeyword(keyword);
        List<RiskImageTemplateVO> vos = TransferUtils.toList(templates, RiskImageTemplateVO.class);
        return R.ok(vos);
    }

    /**
     * 获取所有风险图片模板列表（用于下拉选择）
     *
     * @return 风险图片模板列表
     */
    @GetMapping("/list")
    public R<List<RiskImageTemplateVO>> list() {
        List<MonitorRiskImageTemplate> templateList = riskImageTemplateService.list();
        List<RiskImageTemplateVO> voList = TransferUtils.toList(templateList, RiskImageTemplateVO.class);
        return R.ok(voList);
    }

    /**
     * 添加风险图片模板
     *
     * @param addDTO 添加信息
     * @return 添加结果
     */
    @PostMapping
    public R<Boolean> add(@RequestBody RiskImageTemplateAddDTO addDTO) {
        // 检查模板名称是否已存在
        if (riskImageTemplateService.checkNameExists(addDTO.getName(), null)) {
            return R.failed("模板名称已存在");
        }

        // 检查相似度范围
        if (addDTO.getSimilarity() == null || addDTO.getSimilarity() < 1 || addDTO.getSimilarity() > 100) {
            return R.failed("相似度阈值必须在1-100之间");
        }

        MonitorRiskImageTemplate template = TransferUtils.to(addDTO, MonitorRiskImageTemplate.class);

        // 设置创建者和创建时间
        LocalDateTime now = LocalDateTime.now();
        template.setCreatedAt(now);
        template.setUpdatedAt(now);
        template.setCreatedBy(UserInfoContext.get().getUserId());
        template.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = riskImageTemplateService.save(template);
        return success ? R.ok(true) : R.failed("添加失败");
    }

    /**
     * 更新风险图片模板
     *
     * @param updateDTO 更新信息
     * @return 更新结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody RiskImageTemplateUpdateDTO updateDTO) {
        MonitorRiskImageTemplate template = riskImageTemplateService.getById(updateDTO.getId());
        if (template == null) {
            return R.failed("模板不存在");
        }

        // 检查模板名称是否已存在
        if (riskImageTemplateService.checkNameExists(updateDTO.getName(), updateDTO.getId())) {
            return R.failed("模板名称已存在");
        }

        // 检查相似度范围
        if (updateDTO.getSimilarity() == null || updateDTO.getSimilarity() < 1 || updateDTO.getSimilarity() > 100) {
            return R.failed("相似度阈值必须在1-100之间");
        }

        TransferUtils.copyProperties(updateDTO, template);

        // 设置更新者和更新时间
        template.setUpdatedAt(LocalDateTime.now());
        template.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = riskImageTemplateService.updateById(template);
        return success ? R.ok(true) : R.failed("更新失败");
    }

    /**
     * 删除风险图片模板
     *
     * @param id 模板ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Integer id) {
        boolean success = riskImageTemplateService.removeById(id);
        return success ? R.ok(true) : R.failed("删除失败");
    }
} 