package com.spring.boot.manager.admin.controller;

import com.spring.boot.manager.entity.Setting;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.ThymeleafUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    public AdminService adminService;

    @Autowired
    public AdminTwoService adminTwoService;

    @RequestMapping("/ask-list")
    public String ask() {
        return "ask-list";
    }

    @RequestMapping("/finance-list")
    public String finance(Model model) {
        AdminParameter adminParameter = new AdminParameter();
        adminParameter.setType(2);
        model.addAttribute("accepttime", ((Setting) adminService.setting(adminParameter).getData()).getValue().toBigInteger());
        adminParameter.setType(3);
        model.addAttribute("asktime", ((Setting) adminService.setting(adminParameter).getData()).getValue().toBigInteger());
        return "finance-list";
    }

    @RequestMapping("/purch-list")
    public String purch(@ModelAttribute AdminParameter adminParameter, Model model) {
        model.addAttribute("askid", adminParameter.getAskid()+"");
        model.addAttribute("thymeleafutils", new ThymeleafUtils());
        return "purch-list";
    }

    @RequestMapping("/page-changepassword")
    public String changepassword() {
        return "page-changepassword";
    }

    @RequestMapping("/request-list")
    public String request(Model model) {
        AdminParameter adminParameter = new AdminParameter();
        adminParameter.setType(1);
        model.addAttribute("value", ((Setting) adminService.setting(adminParameter).getData()).getValue());
        return "request-list";
    }

    @RequestMapping("/role-list")
    public String role() {
        return "role-list";
    }

    @RequestMapping("/role-new")
    public String rolenew(Model model) {
        AdminParameter adminParameter = new AdminParameter();
        model.addAttribute("permissions", adminService.permissionList(adminParameter).getData());
        model.addAttribute("projects", adminTwoService.projectList(adminParameter).getData());
        model.addAttribute("suppliers", adminTwoService.supplierList(adminParameter).getData());
        return "role-new";
    }

    @RequestMapping("/role-update")
    public String roleupdate(@ModelAttribute AdminParameter adminParameter, Model model) {
        model.addAttribute("permissions", adminService.permissionList(adminParameter).getData());
        model.addAttribute("projects", adminTwoService.projectList(adminParameter).getData());
        model.addAttribute("suppliers", adminTwoService.supplierList(adminParameter).getData());
        model.addAttribute("role", adminService.role(adminParameter).getData());
        model.addAttribute("thymeleafutils", new ThymeleafUtils());
        return "role-update";
    }

    @RequestMapping("/user-list")
    public String user() {
        return "user-list";
    }

    @RequestMapping("/user-new")
    public String usernew() {
        return "user-new";
    }

    @RequestMapping("/user-update")
    public String userupdate(String userid, Model model) {
        model.addAttribute("userid", userid);
        return "user-update";
    }
}
