package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.ApiService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class ApiController {

    @Autowired
    private ApiService apiService;

    //登录
    @PostMapping("/login")
    public Result login(String mobile,String password) {
        return apiService.login(mobile,password);
    }
}
