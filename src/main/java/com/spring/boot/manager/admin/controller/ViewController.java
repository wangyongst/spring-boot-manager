package com.spring.boot.manager.admin.controller;

import com.spring.boot.manager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    public AdminService adminService;

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
    public String role() {
        return "role-list";
    }

    @RequestMapping("/role-new")
    public String rolenew(Model model) {
        model.addAttribute("permissionF", adminService.permissionList(null));
        return "role-new";
    }

    @RequestMapping("/role-update")
    public String roleupdate() {
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
