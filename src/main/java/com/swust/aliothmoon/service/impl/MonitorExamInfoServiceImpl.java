package com.swust.aliothmoon.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.entity.MonitorExamInfo;
import com.swust.aliothmoon.mapper.MonitorExamInfoMapper;
import com.swust.aliothmoon.service.MonitorExamInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author Aliothmoon
 *
 */
@Service
@RequiredArgsConstructor
public class MonitorExamInfoServiceImpl extends ServiceImpl<MonitorExamInfoMapper, MonitorExamInfo> implements MonitorExamInfoService {

}
