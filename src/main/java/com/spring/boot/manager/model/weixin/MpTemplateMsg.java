package com.spring.boot.manager.model.weixin;

import org.thymeleaf.engine.TemplateData;

import java.util.Map;

public class MpTemplateMsg {
    private String appid;
    private String template_id;
    private String url;
    private String miniprogram;
    private Map<String, WXData> data;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(String miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, WXData> getData() {
        return data;
    }

    public void setData(Map<String, WXData> data) {
        this.data = data;
    }
}
