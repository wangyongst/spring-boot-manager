package com.spring.boot.manager.admin.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public AdminService adminService;

    //我
    @GetMapping("/me")
    public Result me(@ModelAttribute AdminParameter adminParameter) {
        return adminService.me();
    }

    //账号列表
    @GetMapping("/user/list")
    public Object userList(@ModelAttribute AdminParameter adminParameter) {
        return adminService.userList(adminParameter).getData();
    }

    //账号详情
    @GetMapping("/user")
    public Result user(@ModelAttribute AdminParameter adminParameter) {
        return adminService.user(adminParameter);
    }

    //账号增删改
    @PostMapping("/user/sud")
    public Result userSud(@ModelAttribute AdminParameter adminParameter) {
        return adminService.userSud(adminParameter);
    }

    //角色列表
    @GetMapping("/role/list")
    public Object roleList(@ModelAttribute AdminParameter adminParameter) {
        return adminService.roleList(adminParameter).getData();
    }

    //角色详情
    @GetMapping("/role")
    public Result role(@ModelAttribute AdminParameter adminParameter) {
        return adminService.role(adminParameter);
    }

    //角色增删改
    @PostMapping("/role/sud")
    public Result roleSud(@ModelAttribute AdminParameter adminParameter) {
        return adminService.roleSud(adminParameter);
    }

    //修改密码
    @PostMapping("/changepassword")
    public Result changePassword(@ModelAttribute AdminParameter adminParameter) {
        return adminService.changePassword(adminParameter);
    }

    //系统设置
    @PostMapping("/setting/sud")
    public Result settingSud(@ModelAttribute AdminParameter adminParameter) {
        return adminService.settingSud(adminParameter);
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("uploadfile") MultipartFile file, @ModelAttribute AdminParameter adminParameter) {
        return adminService.upload(file,adminParameter);
    }
}
