package com.swust.aliothmoon.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.swust.aliothmoon.define.Sortable;
import com.swust.aliothmoon.define.TableDataInfo;
import com.swust.aliothmoon.utils.page.PageMetadata;
import com.swust.aliothmoon.utils.page.PageableSupport;
import com.swust.aliothmoon.utils.sql.SqlUtil;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 分页工具类
 */
@UtilityClass
public class PageUtils extends PageHelper {

    /**
     * 设置请求分页数据
     */
    public static Page<?> startPage() {
        PageMetadata pageDomain = PageableSupport.getMetaData();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        return PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 排序依据
     *
     * @param sortable 可排序
     */
    public static void sortBy(Sortable sortable) {
        sortBy(sortable, null);
    }

    /**
     * 排序方式 支持多字段排序
     *
     * @param sortable 可排序
     * @param consumer 排序组
     */
    public static void sortBy(Sortable sortable, Consumer<List<String>> consumer) {
        String sortBy = sortable.getSortBy();
        ArrayList<String> group = new ArrayList<>();
        if (StrUtil.isNotEmpty(sortBy)) {
            String isAsc = sortable.getAsc() == 0 ? "asc" : "desc";
            String origin = SqlUtil.escapeOrderBySql(StrUtil.toUnderlineCase(sortBy) + " " + isAsc);
            group.add(origin);
        }
        if (consumer != null) {
            consumer.accept(group);
        }
        if (!group.isEmpty()) {
            PageUtils.orderBy(CollUtil.join(group, ","));
        }
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageHelper.clearPage();
    }

    public static <T> Page<T> getPageList(List<T> input) {
        if (input instanceof Page) {
            return (Page<T>) input;
        }
        Page<T> ts = new Page<>(1, 1);
        ts.addAll(input);
        ts.setPages(1);
        ts.setTotal(input.size());
        return ts;
    }

    public static <T, R> TableDataInfo<R> getDataTable(List<T> list, Function<T, R> func) {
        PageInfo<T> info = new PageInfo<>(list);
        List<R> result = new ArrayList<>(list.size());
        for (T t : list) {
            result.add(func.apply(t));
        }
        return new TableDataInfo<R>().setRows(result).setPages(info.getPages()).setTotal(info.getTotal());
    }
}
