package com.spring.boot.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "help")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Help2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;
    @Basic
    @Column(name = "audience", nullable = true)
    private Integer audience;
    @Basic
    @Column(name = "title", nullable = true, length = 255)
    private String title;
    @Basic
    @Column(name = "image", nullable = true, length = 255)
    private String image;
    @Basic
    @Column(name = "description", nullable = true, length = 1024)
    private String description;
    @Basic
    @Column(name = "video", nullable = true, length = 255)
    private String video;
    @Basic
    @Column(name = "indexpic", nullable = true, length = 1024)
    private String indexpic;
    @Basic
    @Column(name = "tag", nullable = true, length = 255)
    private String tag;
    @Basic
    @Column(name = "design", nullable = true, length = 255)
    private String design;
    @Basic
    @Column(name = "background", nullable = true)
    private Integer background;
    @Basic
    @Column(name = "setting", nullable = true)
    private Integer setting;
    @Basic
    @Column(name = "draft", nullable = true)
    private Integer draft;
    @Basic
    @Column(name = "studied", nullable = true)
    private Integer studied;
    @Basic
    @Column(name = "readed", nullable = true)
    private Integer readed;
    @Basic
    @Column(name = "forwarded", nullable = true)
    private Integer forwarded;
    @Basic
    @Column(name = "fans", nullable = true)
    private Integer fans;
    @Basic
    @Column(name = "clicked", nullable = true)
    private Integer clicked;
    @Basic
    @Column(name = "recommend", nullable = true)
    private Integer recommend;
    @Basic
    @Column(name = "refertime", nullable = true, length = 255)
    private String refertime;
    @Basic
    @Column(name = "createtime", nullable = true, length = 255)
    private String createtime;
    @Transient
    private Integer isStudied;
    @Transient
    private Integer times;

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getIsStudied() {
        return isStudied;
    }

    public void setIsStudied(Integer isStudied) {
        this.isStudied = isStudied;
    }

    public String getIndexpic() {
        return indexpic;
    }

    public void setIndexpic(String indexpic) {
        this.indexpic = indexpic;
    }

    public String getRefertime() {
        return refertime;
    }

    public void setRefertime(String refertime) {
        this.refertime = refertime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAudience() {
        return audience;
    }

    public void setAudience(Integer audience) {
        this.audience = audience;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Integer getBackground() {
        return background;
    }

    public void setBackground(Integer background) {
        this.background = background;
    }

    public Integer getDraft() {
        return draft;
    }

    public void setDraft(Integer draft) {
        this.draft = draft;
    }

    public Integer getStudied() {
        return studied;
    }

    public void setStudied(Integer studied) {
        this.studied = studied;
    }

    public Integer getSetting() {
        return setting;
    }

    public void setSetting(Integer setting) {
        this.setting = setting;
    }

    public Integer getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
    }

    public Integer getForwarded() {
        return forwarded;
    }

    public void setForwarded(Integer forwarded) {
        this.forwarded = forwarded;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getClicked() {
        return clicked;
    }

    public void setClicked(Integer clicked) {
        this.clicked = clicked;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
