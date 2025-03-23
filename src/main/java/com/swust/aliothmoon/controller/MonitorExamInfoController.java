package com.swust.aliothmoon.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.swust.aliothmoon.entity.MonitorExamInfo;
import com.swust.aliothmoon.service.MonitorExamInfoService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *  控制层。
 *
 * @author Alioth
 * @since 2025-03-24
 */
@RestController
@RequestMapping("/monitorExamInfo")
public class MonitorExamInfoController {

    @Autowired
    private MonitorExamInfoService monitorExamInfoService;

    /**
     * 添加。
     *
     * @param monitorExamInfo 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorExamInfo monitorExamInfo) {
        return monitorExamInfoService.save(monitorExamInfo);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorExamInfoService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorExamInfo 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorExamInfo monitorExamInfo) {
        return monitorExamInfoService.updateById(monitorExamInfo);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorExamInfo> list() {
        return monitorExamInfoService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorExamInfo getInfo(@PathVariable Integer id) {
        return monitorExamInfoService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorExamInfo> page(Page<MonitorExamInfo> page) {
        return monitorExamInfoService.page(page);
    }

}
