package com.swust.aliothmoon.controller.user;

import com.mybatisflex.core.paginate.Page;
import com.swust.aliothmoon.constant.UserRoleConstant;
import com.swust.aliothmoon.context.UserInfoContext;
import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.entity.MonitorUserProfile;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.model.user.LoggedInUserInfo;
import com.swust.aliothmoon.service.MonitorUserProfileService;
import com.swust.aliothmoon.service.MonitorUserService;
import com.swust.aliothmoon.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.swust.aliothmoon.utils.MiscUtils.eq;

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

    @Autowired
    private MonitorUserProfileService monitorUserProfileService;

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

    /**
     * 根据条件获取分页数据
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param username 用户名（可选）
     * @param account 账号（可选）
     * @return 分页数据
     */
    @GetMapping("getMonitorUserPageData")
    public TableDataInfo<MonitorUser> getMonitorUserPageData(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String account) {
        return monitorUserService.getPageData(pageNum, pageSize, username, account);
    }

    /**
     * 保存监考员信息
     *
     * @param monitorUser 监考员信息
     * @return 保存结果
     */
    @PostMapping("saveMonitorUser")
    @Transactional
    public boolean saveMonitorUser(@RequestBody MonitorUser monitorUser) {
        // 设置创建和更新时间
        LocalDateTime now = LocalDateTime.now();
        monitorUser.setCreatedAt(now);
        monitorUser.setUpdatedAt(now);

        // 默认角色为监考员
        monitorUser.setRoleId(UserRoleConstant.INVIGILATOR);

        // 加密密码
        monitorUser.setPassword(CryptoUtils.hashPassword(monitorUser.getPassword()));

        // 保存用户信息
        boolean saveSuccess = monitorUserService.save(monitorUser);

        // 如果保存成功并且用户ID不为空，创建用户配置文件
        if (saveSuccess && monitorUser.getUserId() != null) {
            // 创建用户配置文件
            MonitorUserProfile profile = new MonitorUserProfile();
            profile.setUserId(monitorUser.getUserId());
            profile.setNickname(monitorUser.getUsername()); // 使用用户名作为默认昵称
            profile.setCreatedAt(now);
            profile.setUpdatedAt(now);
            profile.setCreatedBy(monitorUser.getUserId());
            profile.setUpdatedBy(monitorUser.getUserId());

            // 保存用户配置文件
            monitorUserProfileService.save(profile);
        }

        return saveSuccess;
    }

    /**
     * 更新监考员信息
     *
     * @param monitorUser 监考员信息
     * @return 更新结果
     */
    @PutMapping("updateMonitorUser")
    public boolean updateMonitorUser(@RequestBody MonitorUser monitorUser) {
        monitorUser.setUpdatedAt(LocalDateTime.now());
        return monitorUserService.updateById(monitorUser);
    }

    /**
     * 删除监考员信息
     *
     * @param userId 监考员ID
     * @return 删除结果
     */
    @DeleteMapping("removeMonitorUser/{userId}")
    public R<Boolean> removeMonitorUser(@PathVariable Integer userId) {
        LoggedInUserInfo info = UserInfoContext.get();
        if (eq(info.getUserId(), userId)) {
            return R.failed("不能删除自己");
        }
        return R.ok(monitorUserService.removeById(userId));
    }
}
