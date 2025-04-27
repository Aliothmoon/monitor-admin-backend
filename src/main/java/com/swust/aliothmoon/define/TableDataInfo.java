package com.swust.aliothmoon.define;


import com.mybatisflex.core.paginate.Page;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页信息
 *
 * @author Aliothmoon
 * @date 2024/05/14
 */
@Data
@Accessors(chain = true)
public class TableDataInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> rows = Collections.emptyList();

    /**
     * 页数
     */
    private int pages;

    private int code;

    public TableDataInfo() {
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<T> list, long total) {
        this.rows = list;
        this.total = total;
    }

    @SuppressWarnings("unchecked")
    public static <E> TableDataInfo<E> of(Page<E> t) {
        return (TableDataInfo<E>) new TableDataInfo<>()
                .setTotal(t.getTotalRow())
                .setPages((int) t.getTotalPage())
                .setRows((List<Object>) t.getRecords());

    }

}