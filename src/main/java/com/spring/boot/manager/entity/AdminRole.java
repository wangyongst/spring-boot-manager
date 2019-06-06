package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "admin_role")
public class AdminRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "adminRole", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<AdminPrivilege> adminPrivileges;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

    public List<AdminPrivilege> getAdminPrivileges() {
        return adminPrivileges;
    }

    public void setAdminPrivileges(List<AdminPrivilege> adminPrivileges) {
        this.adminPrivileges = adminPrivileges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
