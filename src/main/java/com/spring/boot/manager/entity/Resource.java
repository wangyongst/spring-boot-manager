package com.spring.boot.manager.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Resource {
    private Integer id;
    private Integer projectid;
    private String materialid;
    private String size;
    private String special;
    private String model;
    private String file;
    private Integer createuserid;
    private String createtime;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "projectid", nullable = true)
    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    @Basic
    @Column(name = "materialid", nullable = true, length = 255)
    public String getMaterialid() {
        return materialid;
    }

    public void setMaterialid(String materialid) {
        this.materialid = materialid;
    }

    @Basic
    @Column(name = "size", nullable = true, length = 255)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "special", nullable = true, length = 255)
    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    @Basic
    @Column(name = "model", nullable = true, length = 255)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "file", nullable = true, length = 255)
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @Column(name = "createuserid", nullable = true)
    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id) &&
                Objects.equals(projectid, resource.projectid) &&
                Objects.equals(materialid, resource.materialid) &&
                Objects.equals(size, resource.size) &&
                Objects.equals(special, resource.special) &&
                Objects.equals(model, resource.model) &&
                Objects.equals(file, resource.file) &&
                Objects.equals(createuserid, resource.createuserid) &&
                Objects.equals(createtime, resource.createtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectid, materialid, size, special, model, file, createuserid, createtime);
    }
}
