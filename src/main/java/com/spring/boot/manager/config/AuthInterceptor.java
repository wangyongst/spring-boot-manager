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

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            Map<String, String[]> paramter = request.getParameterMap();
            paramter.forEach((k, v) -> {
                System.out.println("Item : " + k + " Count : " + v[0]);
            });
            request.setAttribute("test","mmmmmmmmmm");

//            if (paramter.get("token") != null)
//                for (String s : paramter.get("token")) {
//                    //判断token有效
//                    if (s.equals("s")) {
//                        requestWrapper.setAttribute("test", "tttttt");
//                        return true;
//                    }
//                }
//            else {
//                returnJson(response, "test");
//            }

        } catch (Exception e) {
            logger.error("认证权限判断出错", e);
        }

        return true;
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            logger.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
