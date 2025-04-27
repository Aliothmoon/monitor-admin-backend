package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorBehaviorRecord;
import com.swust.aliothmoon.mapper.MonitorBehaviorRecordMapper;
import com.swust.aliothmoon.service.MonitorBehaviorRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Alioth
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorBehaviorRecordServiceImpl extends ServiceImpl<MonitorBehaviorRecordMapper, MonitorBehaviorRecord> implements MonitorBehaviorRecordService {

}
