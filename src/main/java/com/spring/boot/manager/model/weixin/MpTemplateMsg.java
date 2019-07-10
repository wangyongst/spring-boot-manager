package com.spring.boot.manager.model.weixin;

import org.springframework.beans.factory.annotation.Value;

public class MpTemplateMsg {
    private String appid;
    private String template_id;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
