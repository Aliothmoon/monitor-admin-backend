package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorUser;
import com.swust.aliothmoon.mapper.MonitorUserMapper;
import com.swust.aliothmoon.service.MonitorUserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Alioth
 * @since 2025-03-24
 */
@Service
public class MonitorUserServiceImpl extends ServiceImpl<MonitorUserMapper, MonitorUser>  implements MonitorUserService{

}
