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

    @Basic
    @Column(name = "createusername", nullable = true, length = 255)
    private String createusername;

    @Basic
    @Column(name = "confirmtime", nullable = true, length = 255)
    private String confirmtime;


    @Basic
    @Column(name = "overtime", nullable = true, length = 255)
    private String overtime;

    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;

    @Basic
    @Column(name = "type", nullable = true)
    private Integer type;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getConfirmtime() {
        return confirmtime;
    }

    public void setConfirmtime(String confirmtime) {
        this.confirmtime = confirmtime;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
