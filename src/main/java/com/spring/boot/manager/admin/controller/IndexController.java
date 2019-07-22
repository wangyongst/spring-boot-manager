package com.spring.boot.manager.admin.controller;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.service.AdminTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    public AdminService adminService;

    @Autowired
    public AdminTwoService adminTwoService;

    @RequestMapping("/")
    public String index(Model model) {
        AdminParameter adminParameter = new AdminParameter();
        model.addAttribute("resources", adminTwoService.resourceList(adminParameter).getData());
        model.addAttribute("projects", adminTwoService.projectList(adminParameter).getData());
        model.addAttribute("suppliers", adminTwoService.supplierList(adminParameter).getData());
        model.addAttribute("count", adminTwoService.count(adminParameter).getData());
        return "index";
    }

    @RequestMapping("page-login")
    public String login() {
        return "page-login";
    }
}
