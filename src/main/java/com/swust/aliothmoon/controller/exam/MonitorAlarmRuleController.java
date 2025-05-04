package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.entity.MonitorAlarmRule;
import com.swust.aliothmoon.service.MonitorAlarmRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  MonitorAlarmRuleController
 *
 * @author Aliothmoon
 *
 */
@RestController
@RequestMapping("/monitorAlarmRule")
public class MonitorAlarmRuleController {

    @Autowired
    private MonitorAlarmRuleService monitorAlarmRuleService;

    /**
     * 添加。
     *
     * @param monitorAlarmRule
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorAlarmRule monitorAlarmRule) {
        return monitorAlarmRuleService.save(monitorAlarmRule);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorAlarmRuleService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorAlarmRule
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorAlarmRule monitorAlarmRule) {
        return monitorAlarmRuleService.updateById(monitorAlarmRule);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorAlarmRule> list() {
        return monitorAlarmRuleService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorAlarmRule getInfo(@PathVariable Integer id) {
        return monitorAlarmRuleService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorAlarmRule> page(Page<MonitorAlarmRule> page) {
        return monitorAlarmRuleService.page(page);
    }

}
