package com.spring.boot.manager.controller;


import com.spring.boot.manager.service.AdminOneService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

@Api(tags= "用户相关服务")
@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminOneController {

    @Autowired
    public AdminOneService adminOneService;

    //我
    @GetMapping("/user/me")
    public Result userme(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userMe(httpSession));
    }

    //后端账号
    @GetMapping("/user/admin/list")
    public Object userAdminList(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.userAdminList(httpSession));
    }

    //后端账号
    @GetMapping("/user/admin")
    public Result userAdmin(@ModelAttribute FourParameter fourParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userAdmin(fourParameter, httpSession));
    }

    //后端角色
    @GetMapping("/user/role/list/result")
    public Result userRoleResut(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userRoleList(httpSession));
    }

    //后端角色
    @GetMapping("/user/role/15")
    public Result userRole15(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userRole15(httpSession));
    }

    //后端角色
    @GetMapping("/user/role/list")
    public Object userRole(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.userRoleList(httpSession));
    }

    //后端角色
    @GetMapping("/user/role")
    public Result userRole(@ModelAttribute FourParameter fourParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userRole(fourParameter, httpSession));
    }

    //后端菜单
    @GetMapping("/menu/list")
    public Result userMenu(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userMenuList(httpSession));
    }

    //后端权限
    @GetMapping("/user/privilege")
    public Result userPrivilege(@ModelAttribute FourParameter fourParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userPrivilege(fourParameter, httpSession));
    }

    //后端权限
    @PostMapping("/user/privilege")
    public Result postUserPrivilege(@ModelAttribute FourParameter fourParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.postUserPrivilege(fourParameter, httpSession));
    }

    //后端账号type1增改，2删
    @PostMapping("/user/admin")
    public Result postUserAdmin(@ModelAttribute FourParameter fourParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.postUserAdmin(fourParameter, httpSession));
    }

    //后端角色type1增改，2删
    @PostMapping("/user/role")
    public Result postUserRole(@ModelAttribute FourParameter fourParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.postUserRole(fourParameter, httpSession));
    }

    //登录
    @PostMapping("/user/login")
    public Result login(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.login(oneParameter, httpSession));
    }

    //登出
    @PostMapping("/user/logout")
    public Result logout(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.logout(httpSession));
    }

    //用户列表,搜索用户
    @GetMapping("/user/list")
    public Object userList(@ModelAttribute AdminOneParameter adminOneParameter, HttpSession httpSession) {
        return ResultUtils.data(adminOneService.userList(adminOneParameter,httpSession));
    }

    //登录时间列表
    @GetMapping("/user/token/list")
    public Object userTokenList(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.userTokenList(httpSession));
    }

    @GetMapping("/user/group/list")
    public Object userGroupList(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.userGroupList(httpSession));
    }

    //在线人数
    @GetMapping("/count/token")
    public Result countToken(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.countToken(httpSession));
    }

    //删除修改 type2删除,type3推广
    @PostMapping("/user")
    public Result postUser(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) throws ParseException {
        return ResultUtils.result(adminOneService.user(oneParameter, httpSession));
    }

    @PostMapping("/user/lock")
    public Result userLock(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userLock(oneParameter, httpSession));
    }

    //用户
    @GetMapping("/user")
    public Result getUser(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.getUser(oneParameter, httpSession));
    }


    //注册人数
    @GetMapping("/count/user")
    public Result countUser(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.countUser(httpSession));
    }

    //全网想学
    @GetMapping("/count/study")
    public Result countStudy(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.studyCount(httpSession));
    }

    //全网私信
    @GetMapping("/message/list")
    public Object messageList(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.messageList(httpSession));
    }

    //1发送，2接收，3发送给谁
    @GetMapping("/count/message/user")
    public Result countMessageUser(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.countMessageUser(oneParameter, httpSession));
    }

    //求助详情
    @GetMapping("/help")
    public Result getHelp(@ModelAttribute TwoParameter twoParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.help(twoParameter, httpSession));
    }

    //广告详情
    @GetMapping("/advert")
    public Result advert(@ModelAttribute TwoParameter twoParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.advert(twoParameter, httpSession));
    }

    //搜索详情
    @GetMapping("/searcing/list")
    public Object searchingList(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.searchingList(httpSession));
    }


    //求助列表
    @GetMapping("/help/list")
    public Object helpList(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.helpList(httpSession));
    }

    //求助列表
    @GetMapping("/help/list/draft")
    public Object helpDraft(HttpSession httpSession) {
        return ResultUtils.result(adminOneService.helpDraft(httpSession));
    }

    //type2删除,type1审核
    @PostMapping("/help")
    public Result postHelp(@ModelAttribute TwoParameter twoParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.postHelp(twoParameter, httpSession));
    }

    //operation 1增，2改，3删除
    @PostMapping("/advert")
    public Result PostAdvert(@ModelAttribute AdminOneParameter adminOneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.postAdvert(adminOneParameter, httpSession));
    }

    //operation 1增改,2删
    @PostMapping("/setting")
    public Result PostSetting(@ModelAttribute AdminOneParameter adminOneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.setting(adminOneParameter, httpSession));
    }

    //查询设置
    @GetMapping("/setting")
    public Result getSetting(@ModelAttribute AdminOneParameter adminOneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.getSetting(adminOneParameter, httpSession));
    }

    //查询设置
    @GetMapping("/setting/list")
    public Object settingList(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.settingList(httpSession));
    }

    @GetMapping("/searching/list")
    public Object searchingList(@ModelAttribute AdminOneParameter adminOneParameter, HttpSession httpSession) {
        return ResultUtils.data(adminOneService.searchingList(httpSession));
    }

    @GetMapping("/report/list/user")
    public Object reportListUser(@ModelAttribute AdminOneParameter adminOneParameter, HttpSession httpSession) {
        return ResultUtils.data(adminOneService.reportListUser(httpSession));
    }

    @GetMapping("/report/list/help")
    public Object reportListHelp(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.reportListHelp(httpSession));
    }

    @GetMapping("/study/help/list")
    public Object studyHelpList(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.data(adminOneService.studyHelpList(oneParameter, httpSession));
    }

    @PostMapping("/user/send/message")
    public Result userSendMessage(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.userSendMessage(oneParameter, httpSession));
    }

    //每个用户被关注的次数
    @GetMapping("/count/follow")
    public Result countFollow(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.countFollow(oneParameter, httpSession));
    }

    //每个用户被关注的详情
    @GetMapping("/follow")
    public Result getfollow(@ModelAttribute OneParameter oneParameter, HttpSession httpSession) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (oneParameter.getPage() == null) oneParameter.setPage(0);
        if (oneParameter.getPagesize() == null) oneParameter.setPagesize(10);
        Pageable pageable = new PageRequest(oneParameter.getPage(), oneParameter.getPagesize(), sort);
        return ResultUtils.result(adminOneService.follow(oneParameter, pageable, httpSession));
    }

    //强制推荐
    @PostMapping("/help/refer")
    public Result helpRefer(@ModelAttribute TwoParameter twoParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.helpRefer(twoParameter, httpSession));
    }

    //强制推荐广告
    @PostMapping("/advert/refer")
    public Result advertRefer(@ModelAttribute TwoParameter twoParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.advertRefer(twoParameter, httpSession));
    }

    //下线广告
    @PostMapping("/advert/out")
    public Result advertOut(@ModelAttribute TwoParameter twoParameter, HttpSession httpSession) {
        return ResultUtils.result(adminOneService.advertOut(twoParameter, httpSession));
    }

    //广告列表
    @GetMapping("/advert/list")
    public Object advertList(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.advertList(httpSession));
    }

    //操作日志
    @GetMapping("/showlog")
    public Object showlog(HttpSession httpSession) {
        return ResultUtils.data(adminOneService.showlog(httpSession));
    }
}
