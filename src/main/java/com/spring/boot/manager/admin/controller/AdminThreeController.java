package com.spring.boot.manager.admin.controller;


import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.model.vo.*;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.Status;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.excel.PoiExcelExport;
import com.spring.boot.manager.utils.excel.ServletUtil;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
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

    //采购记录表检索
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
        List<Purch> purchList = (List<Purch>) adminTwoService.financeHistory(adminParameter).getData();
        List<PurchV2> purchV2List = change(purchList);
        String fileName = "采购记录.xls";
        ServletUtil su = new ServletUtil(fileName, req, resp);
        su.poiExcelServlet();
        String[] heads = {"序号", "采购编号", "采购日期", "接单日期", "签收日期", "项目名称", "采购公司", "供应商名称", "耗材编号", "耗材类型", "尺寸大小", "特殊要求", "材质规格", "采购数量", "销售数量", "收货数量", "采购单价（元）", "销售单价（元）", "应收金额（元）", "应付金额（元）", "状态"};
        String[] cols = {"id", "number", "createtime", "acceptime", "overtime", "projectname", "customer", "suppliername", "code", "materialname", "size", "special", "model", "num", "sellnum", "acceptnum", "acceptprice", "price", "totalprice", "totalpay", "status"};
        int[] numerics = {0};
        ServletUtil suresp = new ServletUtil(resp);
        PoiExcelExport<PurchV2> pee = new PoiExcelExport<>(fileName, heads, cols, purchV2List, numerics, suresp.getOut());
        pee.exportExcel();
    }


    public PurchV2 changeVo(Purch purch) {
        PurchV2 p = new PurchV2();
        p.setId(purch.getId());
        p.setCreatetime(purch.getAsk().getCreatetime());
        switch (purch.getStatus()) {
            case 1:  //1.待报价 2.待审核 3.待接单 4 已失效 5.生产中 6.送货中 7.已完成 8 待出账 9 完结
                p.setStatus("待报价");
                break;
            case 2:
                p.setStatus("待审核");
                break;
            case 3:
                p.setStatus("待接单");
                break;
            case 4:
                p.setStatus("待接单（已过期）");
                break;
            case 5:
                p.setStatus("生产中");
                break;
            case 6:
                p.setStatus("送货中");
                break;
            case 7:
                p.setStatus("已完成");
                break;
            case 8:
                p.setStatus("待出账");
                break;
            case 9:
                p.setStatus("已完结");
                break;
            default:
                break;
        }
        p.setNumber(purch.getAsk().getRequest().getNumber());
        p.setOvertime(purch.getAsk().getOvertime());
        p.setProjectname(purch.getAsk().getRequest().getResource().getProject().getName());
        p.setSize(purch.getAsk().getRequest().getResource().getSize());
        p.setSpecial(purch.getAsk().getRequest().getResource().getSpecial());
        p.setModel(purch.getAsk().getRequest().getResource().getModel());
        p.setCode(purch.getAsk().getRequest().getResource().getCode());
        p.setMaterialname(purch.getAsk().getRequest().getResource().getMaterial().getName());
        p.setNum(purch.getAsk().getRequest().getNum());
        p.setAcceptprice(purch.getAcceptprice());
        p.setPrice(purch.getAsk().getRequest().getPrice());
        p.setProjectname(purch.getAsk().getRequest().getResource().getProject().getName());
        p.setSuppliername(purch.getSupplier().getName());
        p.setCustomer(purch.getAsk().getRequest().getResource().getProject().getCustomer());
        p.setSellnum(purch.getAsk().getRequest().getSellnum());
        p.setAcceptnum(purch.getAcceptnum());
        p.setAcceptime(purch.getAccepttime());
        p.setCreatetime(purch.getAsk().getCreatetime());
//        // 应收金额 应收金额是销售单价*销售数量
//        private BigDecimal totalprice;
//        //应付金额 供应商的报价*送货数量
//        private BigDecimal totalpay;
        if (purch.getAsk().getRequest().getSellnum() != null && purch.getAsk().getRequest().getPrice() != null) {
            p.setTotalprice(purch.getAsk().getRequest().getPrice().multiply(new BigDecimal(purch.getAsk().getRequest().getSellnum().intValue())));
        }
        if (purch.getAcceptnum() != null && purch.getAcceptprice() != null) {
            p.setTotalpay(purch.getAcceptprice().multiply(new BigDecimal(purch.getAcceptnum())));
        }
        if (p.getTotalpay() == null) p.setTotalpay(new BigDecimal(0));
        if (p.getTotalprice() == null) p.setTotalprice(new BigDecimal(0));
        return p;
    }


    public List<PurchV2> change(List<Purch> purchList) {
        List<PurchV2> purchVS = new ArrayList<>();
        ((List<Purch>) purchList).forEach(e -> {
            purchVS.add(changeVo(e));
        });
        return purchVS;
    }

}
