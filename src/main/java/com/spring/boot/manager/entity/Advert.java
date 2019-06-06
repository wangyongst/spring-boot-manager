package com.spring.boot.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "advert")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Advert implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "adminuserid", referencedColumnName = "id")
    private AdminUser adminuser;
    @Basic
    @Column(name = "title", nullable = true, length = 255)
    private String title;
    @Basic
    @Column(name = "image", nullable = true, length = 255)
    private String image;
    @Basic
    @Column(name = "url", nullable = true, length = 255)
    private String url;
    @Basic
    @Column(name = "exposure", nullable = true)
    private Integer exposure;
    @Basic
    @Column(name = "clicked", nullable = true)
    private Integer clicked;
    @Basic
    @Column(name = "forwarded", nullable = true)
    private Integer forwarded;
    @Basic
    @Column(name = "buy", nullable = true)
    private Integer buy;
    @Basic
    @Column(name = "refer", nullable = true)
    private Integer refer;
    @Basic
    @Column(name = "type", nullable = true)
    private Integer type;
    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;
    @Basic
    @Column(name = "outtime", nullable = true, length = 255)
    private String outtime;

    @Transient
    private Integer isBuy;

    @Transient
    private Integer status;

    @Transient
    private Float rate;

    public Integer getForwarded() {
        return forwarded;
    }

    public void setForwarded(Integer forwarded) {
        this.forwarded = forwarded;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getRate() {
        return rate;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdminUser getAdminuser() {
        return adminuser;
    }

    public void setAdminuser(AdminUser adminuser) {
        this.adminuser = adminuser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getExposure() {
        return exposure;
    }

    public void setExposure(Integer exposure) {
        this.exposure = exposure;
    }

    public Integer getClicked() {
        return clicked;
    }

    public void setClicked(Integer clicked) {
        this.clicked = clicked;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public Integer getRefer() {
        return refer;
    }

    public void setRefer(Integer refer) {
        this.refer = refer;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
