package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Ask {
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "createuserid", referencedColumnName = "id")
    private User user;

    @Basic
    @Column(name = "applynum", nullable = true)
    private Integer applynum;
    @Basic
    @Column(name = "sellnum", nullable = true)
    private Integer sellnum;
    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    private BigDecimal price;
    @Basic
    @Column(name = "total", nullable = true, precision = 0)
    private Integer total;
    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getApplynum() {
        return applynum;
    }

    public void setApplynum(Integer applynum) {
        this.applynum = applynum;
    }

    public Integer getSellnum() {
        return sellnum;
    }

    public void setSellnum(Integer sellnum) {
        this.sellnum = sellnum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
