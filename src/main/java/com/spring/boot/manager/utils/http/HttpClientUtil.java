package com.spring.boot.manager.utils.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {

    public HttpClient httpClient;

    public HttpClientUtil() throws Exception {
        if (httpClient == null) {
            httpClient = SSLClient.SslHttpClientBuild();
        }
    }

    public HttpResponse sendMessage(String mobile, String text, String tplid) throws IOException {
        HttpPost httpPost = new HttpPost("https://sms.yunpian.com/v2/sms/tpl_single_send.json");
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("apikey", "d25e63352163b9be775dd3a81f54fc5c"));
        list.add(new BasicNameValuePair("mobile", mobile));
        list.add(new BasicNameValuePair("tpl_id", tplid));
        list.add(new BasicNameValuePair("tpl_value", "#code#=" + text));
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(entity);
        }
        return httpClient.execute(httpPost);
    }
}
