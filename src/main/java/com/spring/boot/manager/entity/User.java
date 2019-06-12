package com.spring.boot.manager.entity;

import javax.persistence.*;

@Entity
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 10)
    private String name;
    @Basic
    @Column(name = "password", nullable = true, length = 20)
    private String password;
    @Basic
    @Column(name = "createusername", nullable = true, length = 20)
    private String createusername;
    @Basic
    @Column(name = "mobile", nullable = true, length = 32)
    private String mobile;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "roleid", referencedColumnName = "id")
    private Role role;
    @Basic
    @Column(name = "ischange", nullable = true)
    private Integer ischange;
    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

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
