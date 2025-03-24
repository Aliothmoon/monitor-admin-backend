package com.swust.aliothmoon.utils.page;

import cn.hutool.core.convert.Convert;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * 表格数据处理
 */
@UtilityClass
public class PageableSupport {

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 当前记录起始索引 兼容性
     */
    public static final String PAGE = "page";

    /**
     * 每页显示记录数 兼容性
     */
    public static final String SIZE = "size";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 分页参数合理化
     */
    public static final String REASONABLE = "reasonable";

    /**
     * 封装分页对象
     */
    public static PageMetadata getMetaData() {
        PageMetadata metadata = new PageMetadata();
        Optional<HttpServletRequest> req = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                        .map(e -> (ServletRequestAttributes) e)
                        .map(ServletRequestAttributes::getRequest);
        if (req.isPresent()) {
            HttpServletRequest request = req.get();
            // 参数兼容性
            String page = Optional.ofNullable(request.getParameter(PAGE_NUM)).orElse(request.getParameter(PAGE));

            String size = Optional.ofNullable(request.getParameter(PAGE_SIZE)).orElse(request.getParameter(SIZE));

            metadata.setPageNum(Convert.toInt(page, 1))
                    .setPageSize(Convert.toInt(size, 10))
                    .setOrderByColumn(request.getParameter(ORDER_BY_COLUMN))
                    .setReasonable(Convert.toBool(request.getParameter(REASONABLE))).setIsAsc(request.getParameter(IS_ASC));
        }
        return metadata;

    }
}
