package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorFileInfo;
import com.swust.aliothmoon.mapper.MonitorFileInfoMapper;
import com.swust.aliothmoon.service.MonitorFileInfoService;
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
public class MonitorFileInfoServiceImpl extends ServiceImpl<MonitorFileInfoMapper, MonitorFileInfo>  implements MonitorFileInfoService{

}
