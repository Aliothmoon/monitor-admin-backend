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
import com.swust.aliothmoon.entity.MonitorExam;
import com.swust.aliothmoon.service.MonitorExamService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *  MonitorExamController
 *
 * @author Alioth
 * @since 2025-03-24
 */
@RestController
@RequestMapping("/monitorExam")
public class MonitorExamController {

    @Autowired
    private MonitorExamService monitorExamService;

    /**
     * 添加。
     *
     * @param monitorExam 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody MonitorExam monitorExam) {
        return monitorExamService.save(monitorExam);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Integer id) {
        return monitorExamService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param monitorExam 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody MonitorExam monitorExam) {
        return monitorExamService.updateById(monitorExam);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<MonitorExam> list() {
        return monitorExamService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public MonitorExam getInfo(@PathVariable Integer id) {
        return monitorExamService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<MonitorExam> page(Page<MonitorExam> page) {
        return monitorExamService.page(page);
    }

}
