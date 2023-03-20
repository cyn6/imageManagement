package com.cyn.pojo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PageBean<T> {

    private Integer totalCount;
    private List<T> rows;//当前页数据

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
