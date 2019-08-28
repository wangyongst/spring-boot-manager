package com.spring.boot.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "purch", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Deliver> delivers;

    @Basic
    @Column(name = "acceptprice", nullable = true, precision = 2)
    private BigDecimal acceptprice;

    @Basic
    @Column(name = "accepttime", nullable = true, length = 255)
    private String accepttime;

    @Basic
    @Column(name = "acceptnum", nullable = true)
    private Integer acceptnum;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    @Basic
    @Column(name = "islower", nullable = true)
    private Integer islower;

    public Integer getIslower() {
        return islower;
    }

    public void setIslower(Integer islower) {
        this.islower = islower;
    }

    public Integer getAcceptnum() {
        return acceptnum;
    }

    public void setAcceptnum(Integer acceptnum) {
        this.acceptnum = acceptnum;
    }

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

    public List<Deliver> getDelivers() {
        return delivers;
    }

    public void setDelivers(List<Deliver> delivers) {
        this.delivers = delivers;
    }
}
