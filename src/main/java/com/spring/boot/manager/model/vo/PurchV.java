package com.spring.boot.manager.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

public class PurchV {
    private int id;
    //采购编号
    private String number;
    //操作类型  1 询价 2 打样 3 采购
    private int type;
    //发起日期
    private String createtime;
    //1.待报价 2.待审核 3.待接单 4 已失效 5.生产中 6.送货中 7.已完成 8待出账 9完结
    private Integer status;
    //项目名称
    private String projectname;
    //附件
    private String file;
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
    //待生产数量
    private Integer productnum;
    //送货数量
    @JsonIgnore
    private Integer delivernum;
    //实收数量
    @JsonIgnore
    private Integer acceptnum;
    //销售单价
    private BigDecimal price;
    //采购报价
    private BigDecimal acceptprice;
    //收货人
    private String contact;
    //收货人联系电话
    private String mobile;
    //接单倒计时
    private Long time;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public Integer getProductnum() {
        return productnum;
    }

    public void setProductnum(Integer productnum) {
        this.productnum = productnum;
    }

    public Integer getDelivernum() {
        return delivernum;
    }

    public void setDelivernum(Integer delivernum) {
        this.delivernum = delivernum;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
