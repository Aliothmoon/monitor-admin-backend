package com.swust.aliothmoon.define;


/**
 * 可排序
 *
 * @author Aliothmoon
 * @since 2024/07/11
 */
public interface Sortable {
    /**
     * 排序依据字段
     *
     * @return {@code String }
     */
    String getSortBy();

    /**
     * 升序降序 0 asc 1 desc
     *
     * @return int
     */
    int getAsc();
}
