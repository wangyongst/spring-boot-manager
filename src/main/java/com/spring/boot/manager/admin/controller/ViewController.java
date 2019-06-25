package com.spring.boot.manager.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("page-changepassword")
    public String changepassword() {
        return "page-changepassword";
    }

    @RequestMapping("request-list")
    public String request() {
        return "request-list";
    }

    @RequestMapping("role-list")
    public String role() {
        return "role-list";
    }

    @RequestMapping("role-new")
    public String rolenew() {
        return "role-new";
    }

    @RequestMapping("role-update")
    public String roleupdate() {
        return "role-update";
    }

    @RequestMapping("user-list")
    public String user() {
        return "user-list";
    }

    @RequestMapping("user-new")
    public String usernew() {
        return "user-new";
    }

    @RequestMapping("user-update")
    public String userupdate(String userid, Model model) {
        model.addAttribute("userid",userid);
        return "user-update";
    }
}
