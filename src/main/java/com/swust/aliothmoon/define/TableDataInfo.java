package com.swust.aliothmoon.define;


import com.github.pagehelper.IPage;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
    private List<T> rows;

    /**
     * 页数
     */
    private int pages;

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
//
//    public static <T> TableDataInfo<T> build(IPage<T> page) {
//        TableDataInfo<T> rspData = new TableDataInfo<>();
//        rspData.setRows(page.getRecords());
//        rspData.setTotal(page.getTotal());
//        return rspData;
//    }
//
//    public static <T, K> TableDataInfo<K> build(IPage<T> page, List<K> result) {
//        TableDataInfo<K> rspData = new TableDataInfo<>();
//        rspData.setRows(result);
//        rspData.setTotal(page.getTotal());
//        return rspData;
//    }
//
//    public R<TableDataInfo<T>> ok() {
//        return R.ok(this);
//    }

}