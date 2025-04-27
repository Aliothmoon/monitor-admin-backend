package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.swust.aliothmoon.entity.MonitorBehaviorRecord;
import com.swust.aliothmoon.service.MonitorBehaviorRecordService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *  MonitorBehaviorRecordController
 *
 * @author Alioth
 * @since 2025-03-24
 */
@RestController
@RequestMapping("/monitorBehaviorRecord")
public class MonitorBehaviorRecordController {

    @Autowired
    private MonitorBehaviorRecordService monitorBehaviorRecordService;

    /**
     * 添加。
     *
     * @param monitorBehaviorRecord 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorBehaviorRecord monitorBehaviorRecord) {
        return monitorBehaviorRecordService.save(monitorBehaviorRecord);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorBehaviorRecordService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorBehaviorRecord 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorBehaviorRecord monitorBehaviorRecord) {
        return monitorBehaviorRecordService.updateById(monitorBehaviorRecord);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorBehaviorRecord> list() {
        return monitorBehaviorRecordService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorBehaviorRecord getInfo(@PathVariable Integer id) {
        return monitorBehaviorRecordService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorBehaviorRecord> page(Page<MonitorBehaviorRecord> page) {
        return monitorBehaviorRecordService.page(page);
    }

}
