package com.swust.aliothmoon.model.dto;

import com.mybatisflex.core.paginate.Page;
import lombok.Data;

@Data
public class PageInfo {
    private int pageNum;
    private int pageSize;
    private int total;

    @SuppressWarnings("unchecked")
    public <T> Page<T> toPage() {
        Page<Object> page = Page.of(this.pageNum, this.pageSize);
        return (Page<T>) page;
    }
}
