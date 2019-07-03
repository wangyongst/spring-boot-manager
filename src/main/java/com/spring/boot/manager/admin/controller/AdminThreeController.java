package com.spring.boot.manager.admin.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminThreeController {

    @Autowired
    public AdminTwoService adminTwoService;

    //物料采销表
    @GetMapping("/purch/list")
    public Object purchList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.purchList(adminParameter).getData();
    }

    //物料采销表
    @GetMapping("/ask/list")
    public Object askList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.askList(adminParameter).getData();
    }


    //物料采销表增删改
    @PostMapping("/ask/sud")
    public Object askSud(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.askSud(adminParameter).getData();
    }

    //采购申请
    @GetMapping("/request/list")
    public Object requestList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.requestList(adminParameter).getData();
    }

    //采购申请详情
    @GetMapping("/request")
    public Result request(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.request(adminParameter);
    }


    //采购申请增删改
    @PostMapping("/request/sud")
    public Result requestSud(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.requestSud(adminParameter);
    }

    //发起
    @PostMapping("/request/ask")
    public Result requestAsk(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.requestAsk(adminParameter);
    }
}
