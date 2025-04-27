package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorUserRole;
import com.swust.aliothmoon.mapper.MonitorUserRoleMapper;
import com.swust.aliothmoon.service.MonitorUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Alioth
 * @since 2025-03-24
 */
@Service
@RequiredArgsConstructor
public class MonitorUserRoleServiceImpl extends ServiceImpl<MonitorUserRoleMapper, MonitorUserRole>  implements MonitorUserRoleService{

}
