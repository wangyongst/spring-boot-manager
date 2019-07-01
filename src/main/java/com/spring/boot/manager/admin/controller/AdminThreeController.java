package com.spring.boot.manager.admin.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminThreeService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminThreeController {

    @Autowired
    public AdminThreeService adminThreeService;

    //物料采销表
    @GetMapping("/ask/list")
    public Object askList(@ModelAttribute AdminParameter adminParameter) {
        return adminThreeService.askList(adminParameter).getData();
    }


    //物料采销表增删改
    @PostMapping("/ask/sud")
    public Object askSud(@ModelAttribute AdminParameter adminParameter) {
        return adminThreeService.askSud(adminParameter).getData();
    }

    //采购申请
    @GetMapping("/request/list")
    public Object requestList(@ModelAttribute AdminParameter adminParameter) {
        return adminThreeService.requestList(adminParameter).getData();
    }

    //采购申请详情
    @GetMapping("/request")
    public Result request(@ModelAttribute AdminParameter adminParameter) {
        return adminThreeService.request(adminParameter);
    }


    //采购申请增删改
    @PostMapping("/request/sud")
    public Result requestSud(@ModelAttribute AdminParameter adminParameter) {
        return adminThreeService.requestSud(adminParameter);
    }

    //发起
    @PostMapping("/request/ask")
    public Result requestAsk(@ModelAttribute AdminParameter adminParameter) {
        return adminThreeService.requestAsk(adminParameter);
    }
}
