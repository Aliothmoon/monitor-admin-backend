package com.swust.aliothmoon.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.entity.MonitorUserRole;
import com.swust.aliothmoon.service.MonitorUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  MonitorUserRoleController
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/monitorUserRole")
public class MonitorUserRoleController {

    @Autowired
    private MonitorUserRoleService monitorUserRoleService;

    /**
     * 添加。
     *
     * @param monitorUserRole
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorUserRole monitorUserRole) {
        return monitorUserRoleService.save(monitorUserRole);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorUserRoleService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorUserRole
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorUserRole monitorUserRole) {
        return monitorUserRoleService.updateById(monitorUserRole);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorUserRole> list() {
        return monitorUserRoleService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorUserRole getInfo(@PathVariable Integer id) {
        return monitorUserRoleService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorUserRole> page(Page<MonitorUserRole> page) {
        return monitorUserRoleService.page(page);
    }

}
