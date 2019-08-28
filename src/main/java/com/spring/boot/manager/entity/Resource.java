package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Resource {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "projectid", referencedColumnName = "id")
    private Project project;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "materialid", referencedColumnName = "id")
    private Material material;

    @Basic
    @Column(name = "code", nullable = true, length = 255)
    private String code;

    @Basic
    @Column(name = "size", nullable = true, length = 255)
    private String size;
    @Basic
    @Column(name = "special", nullable = true, length = 255)
    private String special;
    @Basic
    @Column(name = "model", nullable = true, length = 255)
    private String model;
    @Basic
    @Column(name = "file", nullable = true, length = 255)
    private String file;
    @Basic
    @Column(name = "createusername", nullable = true, length = 255)
    private String createusername;

    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
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

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
