package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.entity.MonitorDomainBlacklist;
import com.swust.aliothmoon.model.domain.DomainBlacklistAddDTO;
import com.swust.aliothmoon.model.domain.DomainBlacklistQueryDTO;
import com.swust.aliothmoon.model.domain.DomainBlacklistUpdateDTO;
import com.swust.aliothmoon.model.domain.DomainBlacklistVO;
import com.swust.aliothmoon.service.MonitorDomainBlacklistService;
import com.swust.aliothmoon.service.MonitorExamDomainService;
import com.swust.aliothmoon.utils.TransferUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.swust.aliothmoon.entity.table.MonitorDomainBlacklistTableDef.MONITOR_DOMAIN_BLACKLIST;

/**
 * 域名黑名单控制器
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/exam/domain-blacklist")
@RequiredArgsConstructor
public class DomainBlacklistController {

    private final MonitorDomainBlacklistService domainBlacklistService;
    private final MonitorExamDomainService examDomainService;

    /**
     * 分页查询域名黑名单
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public R<Page<DomainBlacklistVO>> page(@RequestBody DomainBlacklistQueryDTO queryDTO) {
        QueryWrapper queryWrapper = QueryWrapper.create();

        // 添加查询条件
        if (queryDTO.getDomain() != null && !queryDTO.getDomain().isEmpty()) {
            queryWrapper.and(MONITOR_DOMAIN_BLACKLIST.DOMAIN.like(queryDTO.getDomain()));
        }

        if (queryDTO.getCategory() != null && !queryDTO.getCategory().isEmpty()) {
            queryWrapper.and(MONITOR_DOMAIN_BLACKLIST.CATEGORY.eq(queryDTO.getCategory()));
        }

        // 按创建时间降序排序
        queryWrapper.orderBy(MONITOR_DOMAIN_BLACKLIST.CREATED_AT.desc());

        // 分页查询
        Page<MonitorDomainBlacklist> page = domainBlacklistService.page(
                new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                queryWrapper
        );

        // 转换为VO
        Page<DomainBlacklistVO> result = new Page<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotalRow(page.getTotalRow());
        result.setTotalPage(page.getTotalPage());

        List<DomainBlacklistVO> records = TransferUtils.toList(page.getRecords(), DomainBlacklistVO.class);
        result.setRecords(records);

        return R.ok(result);
    }

    /**
     * 根据ID查询域名黑名单
     *
     * @param id 域名ID
     * @return 域名信息
     */
    @GetMapping("/{id}")
    public R<DomainBlacklistVO> getById(@PathVariable Integer id) {
        MonitorDomainBlacklist domain = domainBlacklistService.getById(id);
        if (domain == null) {
            return R.failed("域名不存在");
        }
        DomainBlacklistVO vo = TransferUtils.to(domain, DomainBlacklistVO.class);
        return R.ok(vo);
    }

    /**
     * 根据分类查询域名黑名单列表
     *
     * @param category 分类
     * @return 域名列表
     */
    @GetMapping("/list/category/{category}")
    public R<List<DomainBlacklistVO>> listByCategory(@PathVariable String category) {
        List<MonitorDomainBlacklist> domains = domainBlacklistService.listByCategory(category);
        List<DomainBlacklistVO> vos = TransferUtils.toList(domains, DomainBlacklistVO.class);
        return R.ok(vos);
    }

    /**
     * 获取所有域名黑名单列表（用于下拉选择）
     *
     * @return 域名黑名单列表
     */
    @GetMapping("/list")
    public R<List<DomainBlacklistVO>> list() {
        List<MonitorDomainBlacklist> domainList = domainBlacklistService.list();
        List<DomainBlacklistVO> voList = TransferUtils.toList(domainList, DomainBlacklistVO.class);
        return R.ok(voList);
    }

    /**
     * 添加域名黑名单
     *
     * @param addDTO 添加信息
     * @return 添加结果
     */
    @PostMapping
    public R<Boolean> add(@RequestBody DomainBlacklistAddDTO addDTO) {
        // 检查域名是否已存在
        if (domainBlacklistService.checkDomainExists(addDTO.getDomain(), null)) {
            return R.failed("域名已存在");
        }

        MonitorDomainBlacklist domain = TransferUtils.to(addDTO, MonitorDomainBlacklist.class);

        // 设置创建者和创建时间
        LocalDateTime now = LocalDateTime.now();
        domain.setCreatedAt(now);
        domain.setUpdatedAt(now);
        domain.setCreatedBy(UserInfoContext.get().getUserId());
        domain.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = domainBlacklistService.save(domain);
        return success ? R.ok(true) : R.failed("添加失败");
    }

    /**
     * 更新域名黑名单
     *
     * @param updateDTO 更新信息
     * @return 更新结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody DomainBlacklistUpdateDTO updateDTO) {
        MonitorDomainBlacklist domain = domainBlacklistService.getById(updateDTO.getId());
        if (domain == null) {
            return R.failed("域名不存在");
        }

        // 检查域名是否已存在
        if (domainBlacklistService.checkDomainExists(updateDTO.getDomain(), updateDTO.getId())) {
            return R.failed("域名已存在");
        }

        TransferUtils.copyProperties(updateDTO, domain);

        // 设置更新者和更新时间
        domain.setUpdatedAt(LocalDateTime.now());
        domain.setUpdatedBy(UserInfoContext.get().getUserId());

        boolean success = domainBlacklistService.updateById(domain);
        return success ? R.ok(true) : R.failed("更新失败");
    }

    /**
     * 删除域名黑名单
     *
     * @param id 域名ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Integer id) {
        // 检查该域名是否已关联到考试
        if (examDomainService.countByDomainId(id) > 0) {
            return R.failed("该域名已被考试引用，无法删除");
        }

        boolean success = domainBlacklistService.removeById(id);
        return success ? R.ok(true) : R.failed("删除失败");
    }
} 