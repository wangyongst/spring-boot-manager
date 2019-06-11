package com.spring.boot.manager.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Supplier {
    private Integer id;
    private String name;
    private String userid;
    private String product;
    private String fapiao;
    private String yinhang;
    private String zhanghu;
    private String kaihu;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "userid", nullable = true, length = 255)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "product", nullable = true, length = 255)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "fapiao", nullable = true, length = 255)
    public String getFapiao() {
        return fapiao;
    }

    public void setFapiao(String fapiao) {
        this.fapiao = fapiao;
    }

    @Basic
    @Column(name = "yinhang", nullable = true, length = 1024)
    public String getYinhang() {
        return yinhang;
    }

    public void setYinhang(String yinhang) {
        this.yinhang = yinhang;
    }

    @Basic
    @Column(name = "zhanghu", nullable = true, length = 255)
    public String getZhanghu() {
        return zhanghu;
    }

    public void setZhanghu(String zhanghu) {
        this.zhanghu = zhanghu;
    }

    @Basic
    @Column(name = "kaihu", nullable = true, length = 255)
    public String getKaihu() {
        return kaihu;
    }

    public void setKaihu(String kaihu) {
        this.kaihu = kaihu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(id, supplier.id) &&
                Objects.equals(name, supplier.name) &&
                Objects.equals(userid, supplier.userid) &&
                Objects.equals(product, supplier.product) &&
                Objects.equals(fapiao, supplier.fapiao) &&
                Objects.equals(yinhang, supplier.yinhang) &&
                Objects.equals(zhanghu, supplier.zhanghu) &&
                Objects.equals(kaihu, supplier.kaihu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userid, product, fapiao, yinhang, zhanghu, kaihu);
    }
}
