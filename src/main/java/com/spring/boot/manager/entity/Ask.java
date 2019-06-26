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
    @JoinColumn(name = "requestid", referencedColumnName = "id")
    private Request request;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "supplierid", referencedColumnName = "id")
    private Supplier supplier;

    @Basic
    @Column(name = "acceptprice", nullable = true, precision = 2)
    private BigDecimal acceptprice;

    @Basic
    @Column(name = "acceptnum", nullable = true)
    public Integer getAcceptnum() {
        return acceptnum;
    }
    private Integer acceptnum;

    @Basic
    @Column(name = "accepttime", nullable = true, length = 255)
    private String accepttime;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getAcceptprice() {
        return acceptprice;
    }

    public void setAcceptprice(BigDecimal acceptprice) {
        this.acceptprice = acceptprice;
    }

    public void setAcceptnum(Integer acceptnum) {
        this.acceptnum = acceptnum;
    }

    public String getAccepttime() {
        return accepttime;
    }

    public void setAccepttime(String accepttime) {
        this.accepttime = accepttime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
