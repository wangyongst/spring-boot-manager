package com.spring.boot.manager.model;

public class SendMessageBean {
    private String aipkey;
    private String mobile;
    private String text;

    public String getAipkey() {
        return aipkey;
    }

    public void setAipkey(String aipkey) {
        this.aipkey = aipkey;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
