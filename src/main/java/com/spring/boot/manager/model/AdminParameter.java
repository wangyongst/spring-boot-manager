package com.spring.boot.manager.model;

import com.spring.boot.manager.entity.Purch;

import java.math.BigDecimal;
import java.util.List;

public class AdminParameter {
    private int delete;
    private int type;
    private String mobile;
    private String name;
    private String name2;
    private int userid;
    private int roleid;
    private int supplierid;
    private int projectid;
    private int resourceid;
    private int requestid;
    private int billid;
    private int billdetailid;
    private int askid;
    private int purchid;
    private String password;
    private String newpassword;
    private String newpassword2;
    private String createtime;
    private String customer;
    private String zimu;
    private int materialid;
    private int status;
    private String size;
    private String special;
    private String model;
    private String file;
    private String contacts;
    private String fapiao;
    private String zhanghu;
    private String shoukuan;
    private String kaihu;
    private String code;
    private List<Integer> products;
    private List<Integer> permission;
    private String num;
    private String sellnum;
    private String value;
    private String price;
    private String ids;
    private String createusername;
    private String billno;
    private Purch purch;
    private String billtime;
    private String pricetime;
    private String accepttime;

    public String getPricetime() {
        return pricetime;
    }

    public void setPricetime(String pricetime) {
        this.pricetime = pricetime;
    }

    public String getAccepttime() {
        return accepttime;
    }

    public void setAccepttime(String accepttime) {
        this.accepttime = accepttime;
    }

    public String getBilltime() {
        return billtime;
    }

    public void setBilltime(String billtime) {
        this.billtime = billtime;
    }

    public Purch getPurch() {
        return purch;
    }

    public void setPurch(Purch purch) {
        this.purch = purch;
    }

    public int getBilldetailid() {
        return billdetailid;
    }

    public void setBilldetailid(int billdetailid) {
        this.billdetailid = billdetailid;
    }

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public int getPurchid() {
        return purchid;
    }

    public void setPurchid(int purchid) {
        this.purchid = purchid;
    }

    public int getAskid() {
        return askid;
    }

    public void setAskid(int askid) {
        this.askid = askid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Integer> getPermission() {
        return permission;
    }

    public void setPermission(List<Integer> permission) {
        this.permission = permission;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSellnum() {
        return sellnum;
    }

    public void setSellnum(String sellnum) {
        this.sellnum = sellnum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public List<Integer> getProducts() {
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getFapiao() {
        return fapiao;
    }

    public void setFapiao(String fapiao) {
        this.fapiao = fapiao;
    }

    public String getShoukuan() {
        return shoukuan;
    }

    public void setShoukuan(String shoukuan) {
        this.shoukuan = shoukuan;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getZhanghu() {
        return zhanghu;
    }

    public void setZhanghu(String zhanghu) {
        this.zhanghu = zhanghu;
    }

    public String getKaihu() {
        return kaihu;
    }

    public void setKaihu(String kaihu) {
        this.kaihu = kaihu;
    }

    public int getMaterialid() {
        return materialid;
    }

    public void setMaterialid(int materialid) {
        this.materialid = materialid;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getZimu() {
        return zimu;
    }

    public void setZimu(String zimu) {
        this.zimu = zimu;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public int getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(int supplierid) {
        this.supplierid = supplierid;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getNewpassword2() {
        return newpassword2;
    }

    public void setNewpassword2(String newpassword2) {
        this.newpassword2 = newpassword2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
