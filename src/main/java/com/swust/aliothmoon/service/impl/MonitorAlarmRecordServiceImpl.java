package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorAlarmRecord;
import com.swust.aliothmoon.mapper.MonitorAlarmRecordMapper;
import com.swust.aliothmoon.service.MonitorAlarmRecordService;
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
public class MonitorAlarmRecordServiceImpl extends ServiceImpl<MonitorAlarmRecordMapper, MonitorAlarmRecord>  implements MonitorAlarmRecordService{

}
