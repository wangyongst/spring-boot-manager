package com.spring.boot.manager.utils;

import com.spring.boot.manager.model.weixin.MpTemplateMsg;
import com.spring.boot.manager.model.weixin.WXData;
import com.spring.boot.manager.model.weixin.WeiXinMessageM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.ResourceBundle;

public class WeixinUtils {
    private static String appid = "wxe1fbb56a49af3618";
    private static String mpappid = "wxbe276d9de7304ef3";
    private static String secret = "ed27727f5152b094581a6bd6826907e5";
    private static String template_id1 = "k6QhyPbjKTPuJoQ7JxBqf-VVTWubgdmdDIA1uXqLZuE";
    private static String template_id2 = "YAa5LNgLHtGUQeU0742sDQ0CT6hSsbyQ_qa0ZWKQEd0";
    private static String template_id3 = "fL5KbX45ETWEtY6sJ77F9xF7u0u4fJqCSIXLBLxe_B8";

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
        requestUrl = requestUrl.replace("APPID", appid).replace("APPSECRET", secret);
        return restTemplate.getForObject(requestUrl, String.class);
    }

    /**
     * 发送消息
     */
    public static String sendMessage(int type, RestTemplate restTemplate, String accessToken, String touser, Map<String, WXData> data) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        String template_id = "";
        if (type == 1) template_id = template_id1;
        else if (type == 2) template_id = template_id2;
        else if (type == 3) template_id = template_id3;
        else return null;
        HttpEntity requestBody = new HttpEntity(makeMessage(template_id, accessToken, touser, data));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, requestBody, String.class);
        return responseEntity.getBody();
    }

    public static WeiXinMessageM makeMessage(String template_id, String accessToken, String touser, Map<String, WXData> data) {
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
