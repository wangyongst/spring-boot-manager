package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Supplier {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "contacts", nullable = false, length = 255)
    private String contacts;
    @Basic
    @Column(name = "mobile", nullable = false, length = 255)
    private String mobile;
    @Basic
    @Column(name = "fapiao", nullable = false, length = 255)
    private String fapiao;
    @Basic
    @Column(name = "zhanghu", nullable = false, length = 255)
    private String zhanghu;
    @Basic
    @Column(name = "shoukuan", nullable = false, length = 255)
    private String shoukuan;
    @Basic
    @Column(name = "kaihu", nullable = false, length = 255)
    private String kaihu;

    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFapiao() {
        return fapiao;
    }

    public void setFapiao(String fapiao) {
        this.fapiao = fapiao;
    }

    public String getZhanghu() {
        return zhanghu;
    }

    public void setZhanghu(String zhanghu) {
        this.zhanghu = zhanghu;
    }

    public String getShoukuan() {
        return shoukuan;
    }

    public void setShoukuan(String shoukuan) {
        this.shoukuan = shoukuan;
    }

    public String getKaihu() {
        return kaihu;
    }

    public void setKaihu(String kaihu) {
        this.kaihu = kaihu;
    }
}
