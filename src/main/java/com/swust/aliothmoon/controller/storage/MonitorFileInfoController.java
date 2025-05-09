package com.swust.aliothmoon.controller.storage;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.entity.MonitorFileInfo;
import com.swust.aliothmoon.service.MonitorFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  MonitorFileInfoController
 *
 * @author Aliothmoon
 *
 */
@RestController
@RequestMapping("/monitorFileInfo")
public class MonitorFileInfoController {

    @Autowired
    private MonitorFileInfoService monitorFileInfoService;

    /**
     * 添加。
     *
     * @param monitorFileInfo
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorFileInfo monitorFileInfo) {
        return monitorFileInfoService.save(monitorFileInfo);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorFileInfoService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorFileInfo
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorFileInfo monitorFileInfo) {
        return monitorFileInfoService.updateById(monitorFileInfo);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorFileInfo> list() {
        return monitorFileInfoService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorFileInfo getInfo(@PathVariable Integer id) {
        return monitorFileInfoService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorFileInfo> page(Page<MonitorFileInfo> page) {
        return monitorFileInfoService.page(page);
    }

}
