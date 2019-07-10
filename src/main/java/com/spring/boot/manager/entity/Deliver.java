package com.spring.boot.manager.entity;

import javax.persistence.*;

@Entity
public class Deliver {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "purchid", referencedColumnName = "id")
    private Purch purch;

    @Basic
    @Column(name = "confirmnum", nullable = true)
    private Integer confirmnum;

    @Basic
    @Column(name = "delivernum", nullable = true)
    private Integer delivernum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfirmnum() {
        return confirmnum;
    }

    public void setConfirmnum(Integer confirmnum) {
        this.confirmnum = confirmnum;
    }

    public Purch getPurch() {
        return purch;
    }

    public void setPurch(Purch purch) {
        this.purch = purch;
    }

    public Integer getDelivernum() {
        return delivernum;
    }

    public void setDelivernum(Integer delivernum) {
        this.delivernum = delivernum;
    }
}
