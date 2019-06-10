package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.*;
import com.spring.boot.manager.service.AdminOneService;
import com.spring.boot.manager.service.ThreeService;
import com.spring.boot.manager.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api
@CrossOrigin("*")
@Controller
public class ThreeController {

    @Autowired
    public ThreeService threeService;

    @ApiOperation(value = "点击", notes = "点击")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helpid", value = "求助id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/help/click")
    public Result click(@ModelAttribute ThreeParameter threeParameter) {
        return threeService.click(threeParameter);
    }

    @ApiOperation(value = "阅读", notes = "阅读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helpid", value = "求助id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/help/read")
    public Result helpRead(@ModelAttribute ThreeParameter threeParameter) {
        return threeService.helpRead(threeParameter);
    }


    @ApiOperation(value = "推荐", notes = "推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "helpid", value = "求助id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/help/recommend")
    public Result recommend(@ModelAttribute ThreeParameter threeParameter, HttpServletRequest request) {
        return threeService.recommend(threeParameter, request);
    }


    @ApiOperation(value = "详情", notes = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "helpid", value = "求助id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/help")
    public Result help(@ModelAttribute ThreeParameter threeParameter) {
        return threeService.help(threeParameter);
    }

    @ApiOperation(value = "想学的人", notes = "想学的人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "helpid", value = "求助id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/help/studied")
    public Result helpStudied(@ModelAttribute ThreeParameter threeParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), sort);
        return threeService.helpStudied(threeParameter, pageable);
    }


    @ApiOperation(value = "想学", notes = "想学")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "helpid", value = "求助id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "（可选）0想学，1取消想学，默认为0", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/help/study")
    public Result study(@ModelAttribute ThreeParameter threeParameter) {
        return threeService.study(threeParameter);
    }

    @ApiOperation(value = "转发", notes = "转发")
    @ApiImplicitParams({
                       @ApiImplicitParam(name = "helpid", value = "求助id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/help/forward")
    public Result forward(@ModelAttribute ThreeParameter threeParameter) {
        return threeService.forward(threeParameter);
    }


    @ApiOperation(value = "广告转发", notes = "广告转发")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertid", value = "广告id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/advert/forward")
    public Result advertForward(@ModelAttribute ThreeParameter threeParameter) {
        return threeService.advertForward(threeParameter);
    }

    @ApiOperation(value = "发送消息", notes = "发送消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "touserid", value = "发送用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "message", value = "私信（必需）", required = true, dataType = "String")
    })
    @ResponseBody
    @PostMapping("/message/send")
    public Result send(@ModelAttribute ThreeParameter threeParameter) {
        return threeService.send(threeParameter);
    }

    @ApiOperation(value = "我的消息（用户列表）", notes = "我的消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/message/user")
    public Result user(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.user(threeParameter));
    }

    @ApiOperation(value = "我的消息（查看消息）", notes = "我的消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "touserid", value = "对应用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/message/message")
    public Result message(@ModelAttribute ThreeParameter threeParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), sort);
        return ResultUtils.result(threeService.message(threeParameter, pageable));
    }

    @ApiOperation(value = "我的消息（删除消息）", notes = "删除单条消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "messageid", value = "消息id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/message/message/delete")
    public Result messageDelete(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.messageDelete(threeParameter));
    }

    @ApiOperation(value = "我的消息（删除用户消息）", notes = "删除与此用户的所有消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "touserid", value = "消息用户id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/message/user/delete")
    public Result userDelete(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.userDelete(threeParameter));
    }

    @ApiOperation(value = "我的消息（已读消息）", notes = "调用此接口后，与此发送用户的所有相关消息都变为已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "touserid", value = "发送用户id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/message/read")
    public Result read(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.read(threeParameter));
    }


    @ApiOperation(value = "系统提醒", notes = "系统提醒")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/notice/new")
    public Result noticeNew(@ModelAttribute ThreeParameter threeParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), sort);
        return ResultUtils.result(threeService.noticeNew(threeParameter, pageable));
    }

    @ApiOperation(value = "系统提醒已读", notes = "系统提醒已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/notice/new/read")
    public Result noticeNewRead(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.noticeNewRead(threeParameter));
    }


    @ApiOperation(value = "关注动态", notes = "关注动态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/follow/help")
    public Result followHelp(@ModelAttribute ThreeParameter threeParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), sort);
        return ResultUtils.result(threeService.followHelp(threeParameter, pageable));
    }


    @ApiOperation(value = "求助最多的设计师", notes = "求助最多的设计师，传参userid将判断是否关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/user/most")
    public Result mostUser(@ModelAttribute ThreeParameter threeParameter) {
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), null);
        return ResultUtils.result(threeService.userMost(threeParameter, pageable));
    }


    @ApiOperation(value = "求助最多的设计师及三条发布", notes = "求助最多的设计师，传参userid将判断是否关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/user/most/help")
    public Result mostUserHelp(@ModelAttribute ThreeParameter threeParameter) {
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), null);
        return ResultUtils.result(threeService.userMostHelp(threeParameter, pageable));
    }

    @ApiOperation(value = "未读消息数量提示", notes = "未读消息数量提示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/news/count")
    public Result newsCount(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.newsCount(threeParameter));
    }

    @ApiOperation(value = "未读消息数量设置成已读", notes = "未读消息数量设置成已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "类型（必需）1关注动态，2我的关注，3我的粉丝，4邀请好友，5我的私信", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/news/count/read")
    public Result newsCountRead(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.newsCountRead(threeParameter));
    }

    @ApiOperation(value = "广告列表", notes = "广告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminuserid", value = "广告主id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "类型（必需）1，想买广告2，了解详情3，立即购买，4推广自己，5私信广告", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/advert/list")
    public Result advertList(@ModelAttribute ThreeParameter threeParameter) {
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), null);
        return ResultUtils.result(threeService.advertList(threeParameter, pageable));
    }


    @ApiOperation(value = "广告详情", notes = "广告详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertid", value = "广告id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/advert/info")
    public Result advertInfo(@ModelAttribute ThreeParameter threeParameter) {
        return ResultUtils.result(threeService.advertInfo(threeParameter));
    }

    @ApiOperation(value = "广告想买详情", notes = "广告想买详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminuserid", value = "广告主id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "advertid", value = "广告id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/advert/studied/list")
    public Result advertStudiedList(@ModelAttribute ThreeParameter threeParameter) {
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), null);
        return ResultUtils.result(threeService.advertStudiedList(threeParameter, pageable));
    }

    @ApiOperation(value = "广告推广自己详情", notes = "广告推广自己详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminuserid", value = "广告主id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "userid", value = "用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/advert/refer/list")
    public Result advertReferList(@ModelAttribute ThreeParameter threeParameter) {
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), null);
        return ResultUtils.result(threeService.advertReferList(threeParameter, pageable));
    }

    @ApiOperation(value = "广告私信详情", notes = "广告私信详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminuserid", value = "广告主id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "messageid", value = "私信id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/advert/message/list")
    public Result advertMessageList(@ModelAttribute ThreeParameter threeParameter) {
        if (threeParameter.getPage() == null) threeParameter.setPage(0);
        if (threeParameter.getPagesize() == null) threeParameter.setPagesize(10);
        Pageable pageable = new PageRequest(threeParameter.getPage(), threeParameter.getPagesize(), null);
        return ResultUtils.result(threeService.advertMessageList(threeParameter, pageable));
    }

}
