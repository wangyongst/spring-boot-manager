package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Request {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "resourceid", referencedColumnName = "id")
    private Resource resource;

    @Basic
    @Column(name = "num", nullable = true)
    private Integer num;

    @Basic
    @Column(name = "sellnum", nullable = true)
    private Integer sellnum;

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    private BigDecimal price;

    @Basic
    @Column(name = "total", nullable = true, precision = 2)
    private BigDecimal total;

    @Basic
    @Column(name = "createusername", nullable = true, length = 255)
    private String createusername;

    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}