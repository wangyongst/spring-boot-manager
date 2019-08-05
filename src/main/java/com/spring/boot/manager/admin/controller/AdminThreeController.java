package com.spring.boot.manager.admin.controller;


import com.spring.boot.manager.entity.Project;
import com.spring.boot.manager.entity.Purch;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.excel.PoiExcelExport;
import com.spring.boot.manager.utils.excel.ServletUtil;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminThreeController {

    @Autowired
    public AdminTwoService adminTwoService;

    //物料采销表
    @GetMapping("/purch/list")
    public Object purchList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.purchList(adminParameter).getData();
    }

    //采购记录表
    @GetMapping("/finance/history")
    public Object financeHistory(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.financeHistory(adminParameter).getData();
    }


    //采购申请撤回
    @PostMapping("/purch/sud")
    public Result purchSud(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.purchSud(adminParameter);
    }

    //采购申请确认
    @PostMapping("/purch/coc")
    public Result purchCoc(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.purchCoc(adminParameter);
    }

    //询价单
    @GetMapping("/ask/list")
    public Object askList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.askList(adminParameter).getData();
    }

    //询价单增删改
    @PostMapping("/ask/sud")
    public Object askSud(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.askSud(adminParameter).getData();
    }

    //采购申请
    @GetMapping("/request/list")
    public Object requestList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.requestList(adminParameter).getData();
    }

    //采购申请详情
    @GetMapping("/request")
    public Result request(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.request(adminParameter);
    }

    //采购申请增删改
    @PostMapping("/request/sud")
    public Result requestSud(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.requestSud(adminParameter);
    }

    //发起
    @PostMapping("/request/ask")
    public Result requestAsk(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.requestAsk(adminParameter);
    }

    //对账单
    @GetMapping("/bill/list")
    public Object billList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.billList(adminParameter).getData();
    }

    //对账单
    @GetMapping("/billdetail/list")
    public Object billdetailList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.billdetailList(adminParameter).getData();
    }

    //对账单
    @PostMapping("/billdetail/sud")
    public Object billdetailSud(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.billdetailSud(adminParameter);
    }

    //对账单
    @GetMapping("/finance/list")
    public Object financeList(@ModelAttribute AdminParameter adminParameter) {
        return adminTwoService.purchList(adminParameter).getData();
    }

    //项目导出
    @GetMapping("/finance/export")
    public void financeExport(@ModelAttribute AdminParameter adminParameter, HttpServletRequest req, HttpServletResponse resp) {
        List<Purch> projectList = (List<Purch>) adminTwoService.purchList(adminParameter).getData();
        String fileName = "采购记录.xls";
        ServletUtil su = new ServletUtil(fileName, req, resp);
        su.poiExcelServlet();
        String[] heads = {"采购编号", "采购日期", "接单日期", "签收日期", "项目名称", "采购公司", "供应商名称", "耗材编号", "耗材类型", "尺寸大小", "特殊要求", "材质规格", "采购数量", "销售数量", "收货数量", "采购单价（元）", "销售单价（元）", "应收金额（元）", "应付金额（元）", "状态"};
        String[] cols = {"id", "customer", "name", "zimu", "createusername", "createtime"};
        int[] numerics = {0};
        ServletUtil suresp = new ServletUtil(resp);
        PoiExcelExport<Purch> pee = new PoiExcelExport<>(fileName, heads, cols, projectList, numerics, suresp.getOut());
        pee.exportExcel();
    }
}
