package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminThreeService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminThreeController {

    @Autowired
    public AdminThreeService adminThreeService;

    //询价单
    @GetMapping("/ask/list")
    public Result askList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminThreeService.askList(adminParameter, httpSession);
    }

    //采购申请
    @GetMapping("/apply/list")
    public Result applyList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminThreeService.applyList(adminParameter, httpSession);
    }

    //采购申请详情
    @GetMapping("/apply")
    public Result apply(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminThreeService.apply(adminParameter, httpSession);
    }

}
