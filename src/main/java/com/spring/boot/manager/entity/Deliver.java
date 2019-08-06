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

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

    @Basic
    @Column(name = "accepttime", nullable = true, length = 255)
    private String accepttime;

    public String getAccepttime() {
        return accepttime;
    }

    public void setAccepttime(String accepttime) {
        this.accepttime = accepttime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
