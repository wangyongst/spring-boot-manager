package com.spring.boot.manager.controller;

import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "用户认证接口")
@CrossOrigin("*")
@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @Autowired
    public AdminService adminService;

    @ApiIgnore
    @RequestMapping("/403")
    public Result unauthorizedRole() {
        return ResultUtil.noPermission();
    }


    @ApiOperation(value = "用户登录", notes = "用户登录，输入账号密码，返回token，获取到的token做为认证标志，所有需要认证的请求头部添加Token参数进行认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号（必需）,String型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = " 密码（必需）,String型", required = true, dataType = "String")

    })
    @PostMapping(value = "/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isBlank(username)) return ResultUtil.errorWithMessage("登录账号不能为空！");
        if (StringUtils.isBlank(password)) return ResultUtil.errorWithMessage("登录密码不能为空！");
        String regex = "^[a-z0-9A-Z]+$";
        if (!password.matches(regex)) return ResultUtil.errorWithMessage("密码只支持数字和英文！");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            return ResultUtil.loginOK(subject.getSession().getId().toString(),user.getIschange() + "");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.loginFail("登录失败");
        }
    }

    @ApiOperation(value = "用户登出", notes = "请求头部添加Token参数，接口调用后token被销毁")
    @PostMapping(value = "/logout")
    public void logout() {
    }
}
