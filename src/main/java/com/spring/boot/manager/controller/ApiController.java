package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.ApiService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiController {

    @Autowired
    private ApiService apiService;

    //
    @PostMapping("/ask/list")
    public Result askList(String mobile,String password) {
        return apiService.login(mobile,password);
    }
}
