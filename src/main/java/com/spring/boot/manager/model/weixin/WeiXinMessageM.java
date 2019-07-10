package com.spring.boot.manager.model.weixin;

public class WeiXinMessageM {
    private String access_token;
    private String touser;
    private MpTemplateMsg mp_template_msg;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public MpTemplateMsg getMp_template_msg() {
        return mp_template_msg;
    }

    public void setMp_template_msg(MpTemplateMsg mp_template_msg) {
        this.mp_template_msg = mp_template_msg;
    }
}
