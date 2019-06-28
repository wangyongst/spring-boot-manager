package com.spring.boot.manager.admin.controller;

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

    @RequestMapping("/page-changepassword")
    public String changepassword() {
        return "page-changepassword";
    }

    @RequestMapping("/request-list")
    public String request() {
        return "request-list";
    }

    @RequestMapping("/role-list")
    public String role(Model model) {
        AdminParameter adminParameter = new AdminParameter();
        model.addAttribute("roles", adminService.roleList(adminParameter).getData());
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
    public String user(Model model) {
        AdminParameter adminParameter = new AdminParameter();
        model.addAttribute("users", adminService.userList(adminParameter).getData());
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
