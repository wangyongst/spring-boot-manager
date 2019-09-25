package com.spring.boot.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Bill {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic
    @Column(name = "billtime", nullable = true, length = 255)
    private String billtime;
    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "supplierid", referencedColumnName = "id")
    private Supplier supplier;
    @Basic
    @Column(name = "total")
    private BigDecimal total;
    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;
    @JsonIgnore
    @OneToMany(mappedBy = "bill", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Billdetail> billdetails;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Billdetail> getBilldetails() {
        return billdetails;
    }

    public void setBilldetails(List<Billdetail> billdetails) {
        this.billdetails = billdetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBilltime() {
        return billtime;
    }

    public void setBilltime(String billtime) {
        this.billtime = billtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
