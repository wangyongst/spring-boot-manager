package com.spring.boot.manager.model.vo;

import java.math.BigDecimal;

public class DevlierV {
    private int id;
    //采购编号
    private String number;
    //订单编号
    private int purchid;
    //操作类型  1 询价 2 打样 3 采购
    private int type;
    //发起日期
    private String createtime;
    //1待送货 2送货中 3已完成
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
    private Integer delivernum;
    //已收货数量
    private Integer acceptnum;
    //实收数量
    private Integer confirmnum;
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
    //采购公司
    private String customer;
    //供应商名称
    private String suppliername;
    //送货时间
    private String delivertime;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getConfirmnum() {
        return confirmnum;
    }

    public void setConfirmnum(Integer confirmnum) {
        this.confirmnum = confirmnum;
    }

    public String getDelivertime() {
        return delivertime;
    }

    public void setDelivertime(String delivertime) {
        this.delivertime = delivertime;
    }

    public int getPurchid() {
        return purchid;
    }

    public void setPurchid(int purchid) {
        this.purchid = purchid;
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
