package com.swust.aliothmoon.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.service.MonitorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  MonitorUserController
 *
 * @author Alioth
 *
 */
@RestController
@RequestMapping("/monitorUser")
public class MonitorUserController {

    @Autowired
    private MonitorUserService monitorUserService;

    /**
     * 添加。
     *
     * @param monitorUser
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorUser monitorUser) {
        return monitorUserService.save(monitorUser);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public R<Boolean> remove(@PathVariable Integer id) {
        return R.ok(monitorUserService.removeById(id));
    }

    /**
     * 根据主键更新。
     *
     * @param monitorUser
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public R<Boolean> update(@RequestBody MonitorUser monitorUser) {
        return R.ok(monitorUserService.updateById(monitorUser));
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorUser> list() {
        return monitorUserService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorUser getInfo(@PathVariable Integer id) {
        return monitorUserService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorUser> page(Page<MonitorUser> page) {
        return monitorUserService.page(page);
    }

    @PostMapping("getPageData")
    public TableDataInfo<MonitorUser> getPageData(@RequestBody PageInfo page) {
        return monitorUserService.getPageData(page);
    }

}
