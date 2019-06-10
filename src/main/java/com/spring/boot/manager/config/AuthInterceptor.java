package com.spring.boot.manager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            Map<String,String[]> paramter = requestWrapper.getParameterMap();
            paramter.forEach((k,v)->{
                System.out.println("Item : " + k);
                for(String s : v){
                    System.out.println(s);
                }
            });
            return true;
        }catch (Exception e){
            logger.error("权限判断出错",e);
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
