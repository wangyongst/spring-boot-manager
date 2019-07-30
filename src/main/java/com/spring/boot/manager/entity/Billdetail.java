package com.spring.boot.manager.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Billdetail {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "billid", referencedColumnName = "id")
    private Bill bill;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "purchid", referencedColumnName = "id")
    private Purch purch;
    @Basic
    @Column(name = "billno", nullable = true, length = 255)
    private String billno;
    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Purch getPurch() {
        return purch;
    }

    public void setPurch(Purch purch) {
        this.purch = purch;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }
}
