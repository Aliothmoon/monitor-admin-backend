package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorAlarmRule;
import com.swust.aliothmoon.mapper.MonitorAlarmRuleMapper;
import com.swust.aliothmoon.service.MonitorAlarmRuleService;
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
public class MonitorAlarmRuleServiceImpl extends ServiceImpl<MonitorAlarmRuleMapper, MonitorAlarmRule>  implements MonitorAlarmRuleService{

}
