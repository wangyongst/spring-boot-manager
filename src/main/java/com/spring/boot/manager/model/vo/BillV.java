package com.spring.boot.manager.model.vo;

import java.math.BigDecimal;
import java.util.List;

public class BillV {
    private int id;
    //月份
    private String month;
    //1.待出账 2已账账 3已完成
    private Integer status;
    //总金额
    private BigDecimal total;
    //详情
    private List<BillDetailV> billDetailVList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<BillDetailV> getBillDetailVList() {
        return billDetailVList;
    }

    public void setBillDetailVList(List<BillDetailV> billDetailVList) {
        this.billDetailVList = billDetailVList;
    }
}
