package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminThreeService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/request/list")
    public Object requestList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminThreeService.requestList(adminParameter, httpSession).getData();
    }

    //采购申请详情
    @GetMapping("/request")
    public Result request(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminThreeService.request(adminParameter, httpSession);
    }


    //采购申请增删改
    @PostMapping("/request/sud")
    public Result requestSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminThreeService.requestSud(adminParameter, httpSession);
    }

    //采购申请增删改
    @PostMapping("/request/ask")
    public Result requestAsk(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminThreeService.requestAsk(adminParameter, httpSession);
    }
}
