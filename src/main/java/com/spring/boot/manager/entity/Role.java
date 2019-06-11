package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Basic
    @Column(name = "supplierid", nullable = true)
    private Integer supplierid;

    @Basic
    @Column(name = "projectid", nullable = true)
    private Integer projectid;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Role2Priv> role2Privs;

    public List<Role2Priv> getRole2Privs() {
        return role2Privs;
    }

    public void setRole2Privs(List<Role2Priv> role2Privs) {
        this.role2Privs = role2Privs;
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


    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }


    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }
}
