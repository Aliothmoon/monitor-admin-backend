package com.swust.aliothmoon.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.entity.ExamineeInfo;
import com.swust.aliothmoon.entity.table.ExamineeInfoTableDef;
import com.swust.aliothmoon.mapper.ExamineeInfoMapper;
import com.swust.aliothmoon.model.dto.PageInfo;
import com.swust.aliothmoon.service.ExamineeInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 考生信息服务实现类
 *
 * @author Alioth
 *
 */
@Service
public class ExamineeInfoServiceImpl extends ServiceImpl<ExamineeInfoMapper, ExamineeInfo> implements ExamineeInfoService {

    @Override
    public TableDataInfo<ExamineeInfo> getPageData(PageInfo page) {
        Page<ExamineeInfo> p = page(page.toPage());
        return TableDataInfo.of(p);
    }

    @Override
    public TableDataInfo<ExamineeInfo> getPageData(int pageNum, int pageSize, String name, String studentId, String college, String className) {
        ExamineeInfoTableDef examineeInfo = ExamineeInfoTableDef.EXAMINEE_INFO;
        
        QueryChain<ExamineeInfo> queryChain = QueryChain.of(ExamineeInfo.class);
        
        // 添加查询条件
        if (StringUtils.hasText(name)) {
            queryChain.where(examineeInfo.NAME.like("%" + name + "%"));
        }
        
        if (StringUtils.hasText(studentId)) {
            queryChain.where(examineeInfo.STUDENT_ID.like("%" + studentId + "%"));
        }
        
        if (StringUtils.hasText(college)) {
            queryChain.where(examineeInfo.COLLEGE.like("%" + college + "%"));
        }
        
        if (StringUtils.hasText(className)) {
            queryChain.where(examineeInfo.CLASS_NAME.like("%" + className + "%"));
        }
        
        // 按创建时间排序
        queryChain.orderBy(examineeInfo.CREATED_AT.desc());
        
        // 执行分页查询
        Page<ExamineeInfo> page = new Page<>(pageNum, pageSize);
        Page<ExamineeInfo> result = page(page, queryChain);
        
        return TableDataInfo.of(result);
    }
} 