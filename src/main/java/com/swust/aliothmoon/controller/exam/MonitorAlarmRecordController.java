package com.swust.aliothmoon.controller.exam;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.entity.MonitorAlarmRecord;
import com.swust.aliothmoon.service.MonitorAlarmRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  MonitorAlarmRecordController。
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/monitorAlarmRecord")
public class MonitorAlarmRecordController {

    @Autowired
    private MonitorAlarmRecordService monitorAlarmRecordService;

    /**
     * 添加。
     *
     * @param monitorAlarmRecord
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorAlarmRecord monitorAlarmRecord) {
        return monitorAlarmRecordService.save(monitorAlarmRecord);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorAlarmRecordService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorAlarmRecord
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorAlarmRecord monitorAlarmRecord) {
        return monitorAlarmRecordService.updateById(monitorAlarmRecord);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorAlarmRecord> list() {
        return monitorAlarmRecordService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorAlarmRecord getInfo(@PathVariable Integer id) {
        return monitorAlarmRecordService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorAlarmRecord> page(Page<MonitorAlarmRecord> page) {
        return monitorAlarmRecordService.page(page);
    }

}
