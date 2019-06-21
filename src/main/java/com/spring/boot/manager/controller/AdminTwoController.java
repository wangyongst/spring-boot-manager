package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
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

    //项目列表
    //type1 findDistinctCustomer
    //type2 findDistinctNameByCustomer
    //type3 findDistinctName
    @GetMapping("/project/list")
    public Object projectList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.projectList(adminParameter, httpSession).getData();
    }

    //项目详情
    @GetMapping("/project")
    public Result project(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.project(adminParameter, httpSession);
    }

    //项目增删改
    @PostMapping("/project/sud")
    public Result projectSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.projectSud(adminParameter, httpSession);
    }

    //资源列表
    @GetMapping("/resource/list")
    //type1 findByProjectName
    public Object resourceList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.resourceList(adminParameter, httpSession).getData();
    }

    //资源详情
    @GetMapping("/resource")
    public Result resource(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.resource(adminParameter, httpSession);
    }

    //资源增删改
    @PostMapping("/resource/sud")
    public Result userSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.resourceSud(adminParameter, httpSession);
    }

    //供应商列表
    @GetMapping("/supplier/list")
    public Object supplierList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.supplierList(adminParameter, httpSession).getData();
    }


    //供应商详情
    @GetMapping("/supplier")
    public Result user(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.supplier(adminParameter, httpSession);
    }

    //供应商增删改
    @PostMapping("/supplier/sud")
    public Result supplierSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.supplierSud(adminParameter, httpSession);
    }

    //耗材列表
    //type1 findDistinctCode
    //type2 findByCode
    //type3 findDistinctName
    @GetMapping("/material/list")
    public Object materialList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.materialList(adminParameter, httpSession).getData();
    }

    //耗材增删改
    @PostMapping("/material/sud")
    public Result materialSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.materialSud(adminParameter, httpSession);
    }

}
