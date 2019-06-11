package com.spring.boot.manager.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Ask {
    private Integer id;
    private Integer projectid;
    private Integer materialid;
    private Integer createuserid;
    private Integer applynum;
    private Integer sellnum;
    private BigDecimal price;
    private Integer total;
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
    @Column(name = "materialid", nullable = true)
    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
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
    @Column(name = "applynum", nullable = true)
    public Integer getApplynum() {
        return applynum;
    }

    public void setApplynum(Integer applynum) {
        this.applynum = applynum;
    }

    @Basic
    @Column(name = "sellnum", nullable = true)
    public Integer getSellnum() {
        return sellnum;
    }

    public void setSellnum(Integer sellnum) {
        this.sellnum = sellnum;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "total", nullable = true, precision = 0)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
        Ask ask = (Ask) o;
        return Objects.equals(id, ask.id) &&
                Objects.equals(projectid, ask.projectid) &&
                Objects.equals(materialid, ask.materialid) &&
                Objects.equals(createuserid, ask.createuserid) &&
                Objects.equals(applynum, ask.applynum) &&
                Objects.equals(sellnum, ask.sellnum) &&
                Objects.equals(price, ask.price) &&
                Objects.equals(total, ask.total) &&
                Objects.equals(createtime, ask.createtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectid, materialid, createuserid, applynum, sellnum, price, total, createtime);
    }
}
