package com.spring.boot.manager.utils.restful;

import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("RestfulUtil")
public class RestfulUtil {

    @Autowired
    private RestTemplate restTemplate;

    public Result get(String url) {
        String response = null;
        try {
            response = restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            return ResultUtil.restClientExceptionWithUrlAndMessage(url, e.getMessage());
        }
        if (response == null) return ResultUtil.isNullExceptionWithUrlAndMessage(response.toString(), "REST 请求结果为空！");
        else return ResultUtil.okWithDataAndMessage(response, "REST 请求结果为JSON格式，请自行转化！");
    }

    public Result post(String url, Map<String, Object> params) {
        HttpEntity httpEntity = new HttpEntity(params);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url, httpEntity, String.class);
        } catch (RestClientException e) {
            return ResultUtil.restClientExceptionWithUrlAndMessage(url, e.getMessage());
        }
        if (response == null) return ResultUtil.isNullExceptionWithUrlAndMessage(response.toString(), "REST 请求结果为空！");
        else return ResultUtil.okWithDataAndMessage(response.getBody(), "REST 请求结果为JSON格式，请自行转化！");
    }
}
