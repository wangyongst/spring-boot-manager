package com.spring.boot.manager.utils;

import com.spring.boot.manager.model.weixin.MpTemplateMsg;
import com.spring.boot.manager.model.weixin.WeiXinMessageM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ResourceBundle;

public class WeixinUtils {
    @Value("${custom.weixin.appid}")
    private static String appid;
    @Value("${custom.weixin.mpappid}")
    private static String mpappid;
    @Value("${custom.weixin.secret}")
    private static String secret;
    @Value("${custom.weixin.template_id}")
    private static String template_id;

    /**
     * 获取openId
     */
    public static String getOpenId(RestTemplate restTemplate, String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appid).replace("SECRET", secret).replace("JSCODE", code);
        return restTemplate.getForObject(requestUrl, String.class);
    }

    /**
     * 获取AccessToken
     */
    public static String getAccessToken(RestTemplate restTemplate) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        requestUrl = requestUrl.replace("APPID", appid).replace("SECRET", secret);
        return restTemplate.getForObject(requestUrl, String.class);
    }

    /**
     * 发送消息
     */
    public static String sendMessage(RestTemplate restTemplate, String accessToken, String touser, Object data) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity requestBody = new HttpEntity(makeMessage(accessToken, touser, data), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, requestBody, String.class);
        return responseEntity.getBody();
    }

    public static WeiXinMessageM makeMessage(String accessToken, String touser, Object data) {
        MpTemplateMsg mpTemplateMsg = new MpTemplateMsg();
        mpTemplateMsg.setAppid(mpappid);
        mpTemplateMsg.setTemplate_id(template_id);
        mpTemplateMsg.setUrl("");
        mpTemplateMsg.setMiniprogram("");
        mpTemplateMsg.setData(data);
        WeiXinMessageM weiXinMessageM = new WeiXinMessageM();
        weiXinMessageM.setAccess_token(accessToken);
        weiXinMessageM.setTouser(touser);
        weiXinMessageM.setMp_template_msg(mpTemplateMsg);
        return weiXinMessageM;
    }
}
