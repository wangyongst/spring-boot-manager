package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Purch {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "askid", referencedColumnName = "id")
    private Ask ask;

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

    public Ask getAsk() {
        return ask;
    }

    public void setAsk(Ask ask) {
        this.ask = ask;
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
