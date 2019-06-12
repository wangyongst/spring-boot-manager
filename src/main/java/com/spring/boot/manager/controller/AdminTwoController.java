package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminTwoController {

    @Autowired
    public AdminTwoService adminTwoService;

    //询价单
    @GetMapping("/ask/list")
    public Object askList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.askList(adminParameter, httpSession);
    }

    //采购申请
    @GetMapping("/apply/list")
    public Object applyList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.applyList(adminParameter, httpSession);
    }

    //采购申请详情
    @GetMapping("/apply")
    public Result apply(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.apply(adminParameter, httpSession);
    }


    //项目列表
    @GetMapping("/project/list")
    public Object projectList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.projectList(adminParameter, httpSession);
    }

    //资源列表
    @GetMapping("/resource/list")
    public Object resourceList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.resourceList(adminParameter, httpSession);
    }

    //供应商列表
    @GetMapping("/supplier/list")
    public Object supplierList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.supplierList(adminParameter, httpSession);
    }

}
