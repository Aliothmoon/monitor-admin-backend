package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorRole;
import com.swust.aliothmoon.mapper.MonitorRoleMapper;
import com.swust.aliothmoon.service.MonitorRoleService;
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
public class MonitorRoleServiceImpl extends ServiceImpl<MonitorRoleMapper, MonitorRole>  implements MonitorRoleService{

}
