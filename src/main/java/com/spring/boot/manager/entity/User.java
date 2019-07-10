package com.spring.boot.manager.entity;

import javax.persistence.*;

@Entity
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "password", nullable = true, length = 255)
    private String password;
    @Basic
    @Column(name = "openid", nullable = true, length = 255)
    private String openid;
    @Basic
    @Column(name = "createusername", nullable = true, length = 255)
    private String createusername;
    @Basic
    @Column(name = "mobile", nullable = true, length = 255)
    private String mobile;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "roleid", referencedColumnName = "id")
    private Role role;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "supplierid", referencedColumnName = "id")
    private Supplier supplier;
    @Basic
    @Column(name = "deliver", nullable = true)
    private Integer deliver;
    @Basic
    @Column(name = "ischange", nullable = true)
    private Integer ischange;
    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getDeliver() {
        return deliver;
    }

    public void setDeliver(Integer deliver) {
        this.deliver = deliver;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getIschange() {
        return ischange;
    }

    public void setIschange(Integer ischange) {
        this.ischange = ischange;
    }
}
