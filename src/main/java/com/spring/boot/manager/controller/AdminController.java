package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public AdminService adminService;

    //我
    @GetMapping("/me")
    public Result me(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.me(httpSession);
    }

    //账号列表
    @GetMapping("/user/list")
    public Object userList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.userList(httpSession);
    }

    //账号详情
    @GetMapping("/user")
    public Result user(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.user(httpSession);
    }

    //角色列表
    @GetMapping("/role/list")
    public Result roleList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.roleList(httpSession);
    }

    //角色详情
    @GetMapping("/role")
    public Result role(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.role(httpSession);
    }

    //后端权限
    @GetMapping("/privilege/all")
    public Result privilegeAll(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.privilegeAll(httpSession);
    }

    //登录
    @PostMapping("/login")
    public Result login(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.login(adminParameter, httpSession);
    }

    //登出
    @PostMapping("/logout")
    public Result logout(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.logout(httpSession);
    }

    //修改密码
    @PostMapping("/changePassword")
    public Result changePassword(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminService.changePassword(adminParameter, httpSession);
    }


}
