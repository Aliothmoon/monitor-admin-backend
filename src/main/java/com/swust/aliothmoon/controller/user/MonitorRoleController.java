package com.swust.aliothmoon.controller.user;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.swust.aliothmoon.entity.MonitorRole;
import com.swust.aliothmoon.service.MonitorRoleService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *  MonitorRoleController
 *
 * @author Alioth
 * @since 2025-03-24
 */
@RestController
@RequestMapping("/monitorRole")
public class MonitorRoleController {

    @Autowired
    private MonitorRoleService monitorRoleService;

    /**
     * 添加。
     *
     * @param monitorRole 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorRole monitorRole) {
        return monitorRoleService.save(monitorRole);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorRoleService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorRole 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorRole monitorRole) {
        return monitorRoleService.updateById(monitorRole);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorRole> list() {
        return monitorRoleService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorRole getInfo(@PathVariable Integer id) {
        return monitorRoleService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorRole> page(Page<MonitorRole> page) {
        return monitorRoleService.page(page);
    }

}
