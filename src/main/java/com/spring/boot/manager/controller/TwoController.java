package com.spring.boot.manager.controller;


import com.myweb.service.TwoService;
import com.myweb.vo.ResultUtils;
import com.myweb.vo.TwoParameter;
import com.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api
@CrossOrigin("*")
@Controller
public class TwoController {

    @Autowired
    public TwoService twoService;

    @ApiOperation(value = "发布效果求助", notes = "发布效果求助")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "audience", value = " 观众（必需,1所有，2粉丝，3自己）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = " 标题（可选）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "image", value = "图片 （必需）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "description", value = " 问题描述（可选）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "video", value = " 导入视频（可选）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tag", value = "标签（必需，多个标签请以,符隔开传递）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "design", value = "设计分类 （必需）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "background", value = " 背景颜色（可选）0白，1黑", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "setting", value = " 设置（可选，1定时发表）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "indexpic", value = "封面参数（七牛参数）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "draft", value = "发布方式 （必需，1草稿，2立即发布）", required = true, dataType = "Integer")

    })
    @ResponseBody
    @PostMapping("/help/seek")
    public Result seek(@ModelAttribute TwoParameter twoParameter) {
        return ResultUtils.result(twoService.seek(twoParameter));
    }

    @ApiOperation(value = "已发求助", notes = "已发求助")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "draft", value = "发布方式 （可选，0,全部，1草稿，2，未审核，3未通过审核，4已经发表,5隐蒧的）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/help/user")
    public Result user(@ModelAttribute TwoParameter twoParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (twoParameter.getPage() == null) twoParameter.setPage(0);
        if (twoParameter.getPagesize() == null) twoParameter.setPagesize(10);
        Pageable pageable = new PageRequest(twoParameter.getPage(), twoParameter.getPagesize(), sort);
        return ResultUtils.result(twoService.user(twoParameter, pageable));
    }


    @ApiOperation(value = "搜索历史记录", notes = "搜索历史记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "类型（可选）0全部，1搜图，2搜人,默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/searching/user")
    public Result searchingUser(@ModelAttribute TwoParameter twoParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (twoParameter.getPage() == null) twoParameter.setPage(0);
        if (twoParameter.getPagesize() == null) twoParameter.setPagesize(10);
        Pageable pageable = new PageRequest(twoParameter.getPage(), twoParameter.getPagesize(), sort);
        return ResultUtils.result(twoService.searchingUser(twoParameter, pageable));
    }

    @ApiOperation(value = "清空搜索历史", notes = "清空搜索历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "类型（可选）0全部，1搜图，2搜人,默认为0", required = true, dataType = "Integer"),
    })
    @ResponseBody
    @PostMapping("/searching/clear")
    public Result searchingClear(@ModelAttribute TwoParameter twoParameter) {
        return ResultUtils.result(twoService.searchingClear(twoParameter));
    }

    @ApiOperation(value = "删除搜索历史", notes = "删除搜索历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "searchingid", value = "记录id(必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/searching/clear/id")
    public Result searchingClearId(@ModelAttribute TwoParameter twoParameter) {
        return ResultUtils.result(twoService.searchingClearId(twoParameter));
    }


    @ApiOperation(value = "举报", notes = "举报")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "helpid", value = "举报求助id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "touserid", value = "举报用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "类型，1举报求助，2举报用户", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "举报信息", required = true, dataType = "String")
    })
    @ResponseBody
    @PostMapping("/report")
    public Result report(@ModelAttribute TwoParameter twoParameter) {
        return ResultUtils.result(twoService.report(twoParameter));
    }

    @ApiOperation(value = "首页求助推荐", notes = "首页推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "推荐方式 （可选，0,最新，1想学最多，2，点击最多，3特别推荐），默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "design", value = "设计分类 （可选)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "  （可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/help/index")
    public Result index(@ModelAttribute TwoParameter twoParameter) {
        Sort sort = null;
        if (twoParameter.getType() == null || twoParameter.getType() == 0) {
            sort = new Sort(Sort.Direction.DESC, "createtime");
        } else if (twoParameter.getType() == 1) {
            sort = new Sort(Sort.Direction.DESC, "studied");
        } else if (twoParameter.getType() == 2) {
            sort = new Sort(Sort.Direction.DESC, "clicked");
        } else if (twoParameter.getType() == 3) {
            sort = new Sort(Sort.Direction.DESC, "refertime");
        }
        if (twoParameter.getPage() == null) twoParameter.setPage(0);
        if (twoParameter.getPagesize() == null) twoParameter.setPagesize(10);
        if (StringUtils.isBlank(twoParameter.getDesign()) || twoParameter.getDesign().equals("全部"))
            twoParameter.setDesign(null);
        Pageable pageable = new PageRequest(twoParameter.getPage(), twoParameter.getPagesize(), sort);
        return ResultUtils.result(twoService.index(twoParameter, pageable));
    }

    @ApiOperation(value = "删除求助", notes = "删除求助")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "helpids", value = "求助id(必需），有多个时以，号隔开，如1，2，3", required = true, dataType = "String"),
    })
    @ResponseBody
    @PostMapping("/help/delete")
    public Result delete(@ModelAttribute TwoParameter twoParameter, @RequestParam String helpids) {
        return twoService.delete(twoParameter, helpids);
    }


    @ApiOperation(value = "找图", notes = "首页找图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "tag", value = "标签关键字 （可选)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每  页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/help/search")
    public Result search(@ModelAttribute TwoParameter twoParameter) {
        Sort sort = null;
        if (twoParameter.getType() == null || twoParameter.getType() == 0) {
            sort = new Sort(Sort.Direction.DESC, "createtime");
        } else if (twoParameter.getType() == 1) {
            sort = new Sort(Sort.Direction.DESC, "studied");
        } else if (twoParameter.getType() == 2) {
            sort = new Sort(Sort.Direction.DESC, "clicked");
        } else if (twoParameter.getType() == 3) {
            sort = new Sort(Sort.Direction.DESC, "refertime");
        }
        if (twoParameter.getPage() == null) twoParameter.setPage(0);
        if (twoParameter.getPagesize() == null) twoParameter.setPagesize(10);
        Pageable pageable = new PageRequest(twoParameter.getPage(), twoParameter.getPagesize(), sort);
        return ResultUtils.result(twoService.search(twoParameter, pageable));
    }


    @ApiOperation(value = "广告", notes = "广告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/advert")
    public Result advert(@ModelAttribute TwoParameter twoParameter) {
        if (twoParameter.getPage() == null) twoParameter.setPage(0);
        if (twoParameter.getPagesize() == null) twoParameter.setPagesize(10);
        Pageable pageable = new PageRequest(twoParameter.getPage(), twoParameter.getPagesize(), null);
        return ResultUtils.result(twoService.advert(twoParameter, pageable));
    }

    @ApiOperation(value = "广告人", notes = "广告人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/advert/user")
    public Result advertUser(@ModelAttribute TwoParameter twoParameter) {
        if (twoParameter.getPage() == null) twoParameter.setPage(0);
        if (twoParameter.getPagesize() == null) twoParameter.setPagesize(10);
        Pageable pageable = new PageRequest(twoParameter.getPage(), twoParameter.getPagesize(), null);
        return ResultUtils.result(twoService.advertUser(twoParameter, pageable));
    }

    @ApiOperation(value = "点击广告", notes = "点击广告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertid", value = "广告id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/advert/click")
    public Result click(@ModelAttribute TwoParameter twoParameter) {
        return twoService.click(twoParameter);
    }

    @ApiOperation(value = "想买广告", notes = "想买广告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "advertid", value = "广告id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @PostMapping("/advert/buy")
    public Result buy(@ModelAttribute TwoParameter twoParameter) {
        return twoService.buy(twoParameter);
    }

    @ApiOperation(value = "个人主页", notes = "个人主页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "touserid", value = "主页用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "查询方式 （可选，0,我发布的，1我想学的），默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "tag", value = "标签关键字 （可选)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数（可选）从0开始，如果不传默认为0", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "每页条数（可选），如果不传默认10条", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/help/mine")
    public Result mine(@ModelAttribute TwoParameter twoParameter) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (twoParameter.getPage() == null) twoParameter.setPage(0);
        if (twoParameter.getPagesize() == null) twoParameter.setPagesize(10);
        Pageable pageable = new PageRequest(twoParameter.getPage(), twoParameter.getPagesize(), sort);
        return ResultUtils.result(twoService.mine(twoParameter, pageable));
    }


    @ApiOperation(value = "个人信息", notes = "个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（可选）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "touserid", value = "主页用户id（必需）", required = true, dataType = "Integer")
    })
    @ResponseBody
    @GetMapping("/info/mine")
    public Result info(@ModelAttribute TwoParameter twoParameter) {
        return ResultUtils.result(twoService.info(twoParameter));
    }


    @ApiOperation(value = "隐蒧/取消隐藏", notes = "隐蒧/取消隐藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "当前用户id（必需）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "（必需，1,隐藏，2取消隐藏）", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "helpid", value = "求助id （必需)", required = true, dataType = "String")

    })
    @ResponseBody
    @PostMapping("/help/hidden")
    public Result helpHidden(@ModelAttribute TwoParameter twoParameter) {
        return ResultUtils.result(twoService.hidden(twoParameter));
    }
}
