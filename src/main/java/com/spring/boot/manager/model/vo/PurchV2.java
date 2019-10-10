package com.spring.boot.manager.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class PurchV2 {
    private int id;
    //采购编号
    private String number;
    //采购日期
    private String createtime;
    //接单日期
    private String acceptime;
    //签收日期
    private String overtime;
    //1.待报价 2.待审核 3.待接单 4 已失效 5.生产中 6.送货中 7.已完成 8待出账 9完结
    private String status;
    //项目名称
    private String projectname;
    //采购公司
    private String customer;
    //供应商名称
    private String suppliername;
    //尺寸大小
    private String size;
    //特殊要求
    private String special;
    //材质规格
    private String model;
    //耗材编号
    private String code;
    //耗材类型
    private String materialname;
    //采购数量
    private Integer num;
    //销售数量
    private Integer sellnum;
    //收货数量
    private Integer acceptnum;
    //销售单价
    private BigDecimal price;
    //采购报价
    private BigDecimal acceptprice;
    // 应收金额
    private BigDecimal totalprice;
    //应付金额
    private BigDecimal totalpay;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAcceptime() {
        return acceptime;
    }

    public void setAcceptime(String acceptime) {
        this.acceptime = acceptime;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSellnum() {
        return sellnum;
    }

    public void setSellnum(Integer sellnum) {
        this.sellnum = sellnum;
    }

    public Integer getAcceptnum() {
        return acceptnum;
    }

    public void setAcceptnum(Integer acceptnum) {
        this.acceptnum = acceptnum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAcceptprice() {
        return acceptprice;
    }

    public void setAcceptprice(BigDecimal acceptprice) {
        this.acceptprice = acceptprice;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public BigDecimal getTotalpay() {
        return totalpay;
    }

    public void setTotalpay(BigDecimal totalpay) {
        this.totalpay = totalpay;
    }
}
