package com.spring.boot.manager.model;

import java.io.Serializable;

public class TableVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object rows;
    private Long total;

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
