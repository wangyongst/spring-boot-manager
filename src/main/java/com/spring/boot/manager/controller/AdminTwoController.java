package com.spring.boot.manager.controller;


import com.spring.boot.manager.entity.Project;
import com.spring.boot.manager.entity.Resource;
import com.spring.boot.manager.entity.Supplier;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.excel.PoiExcelExport;
import com.spring.boot.manager.utils.excel.ServletUtil;
import com.spring.boot.manager.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminTwoController {

    @Autowired
    public AdminTwoService adminTwoService;

    //项目列表
    //type1 findDistinctCustomer
    //type2 findDistinctNameByCustomer
    //type3 findDistinctName
    @GetMapping("/project/list")
    public Object projectList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.projectList(adminParameter, httpSession).getData();
    }

    //项目导出
    @GetMapping("/project/export")
    public void projectExport(@ModelAttribute AdminParameter adminParameter, HttpServletRequest req, HttpServletResponse resp, HttpSession httpSession) {
        List<Project> projectList = (List<Project>) adminTwoService.projectList(adminParameter, httpSession).getData();
        String fileName = "项目管理.xls";
        ServletUtil su = new ServletUtil(fileName, req, resp);
        su.poiExcelServlet();
        String[] heads = {"序号", "客户名称", "项目名称", "字母简称", "创建人", "创建时间"};
        String[] cols = {"id", "customer", "name", "zimu", "createusername", "createtime"};
        int[] numerics = {0};
        ServletUtil suresp = new ServletUtil(resp);
        PoiExcelExport<Project> pee = new PoiExcelExport<>(fileName, heads, cols, projectList, numerics, suresp.getOut());
        pee.exportExcel();
    }


    //项目详情
    @GetMapping("/project")
    public Result project(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.project(adminParameter, httpSession);
    }

    //项目增删改
    @PostMapping("/project/sud")
    public Result projectSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.projectSud(adminParameter, httpSession);
    }

    //资源列表
    @GetMapping("/resource/list")
    public Object resourceList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.resourceList(adminParameter, httpSession).getData();
    }


    //资源导出
    @GetMapping("/resource/export")
    public void resourceExport(@ModelAttribute AdminParameter adminParameter, HttpServletRequest req, HttpServletResponse resp, HttpSession httpSession) {
        List<Resource> resourceList = (List<Resource>) adminTwoService.resourceList(adminParameter, httpSession).getData();
        List<AdminParameter> adminParameterList = new ArrayList<>();
        resourceList.forEach(e -> {
            AdminParameter parameter = new AdminParameter();
            parameter.setResourceid(e.getId());
            parameter.setName2(e.getProject().getName());
            parameter.setCode(e.getMaterial().getCode());
            parameter.setName(e.getMaterial().getName());
            parameter.setSize(e.getSize());
            parameter.setSpecial(e.getSpecial());
            parameter.setModel(e.getModel());
            parameter.setFile(e.getFile());
            parameter.setCreateusername(e.getCreateusername());
            parameter.setCreatetime(e.getCreatetime());
            adminParameterList.add(parameter);
        });
        //执行导出
        String fileName = "采购资源表.xls";
        ServletUtil su = new ServletUtil(fileName, req, resp);
        su.poiExcelServlet();
        String[] heads = {"序号", "项目名称", "耗材编号", "耗材类型", "尺寸大小", "特殊要求", "材质规格", "附件", "创建人", "创建时间"};
        String[] cols = {"resourceid", "name2", "code", "name", "size", "special", "model", "file", "createusername", "createtime"};
        //这里传第几个字段是数字，从0开始
        int[] numerics = {0};
        ServletUtil suresp = new ServletUtil(resp);
        PoiExcelExport<AdminParameter> pee = new PoiExcelExport<>(fileName, heads, cols, adminParameterList, numerics, suresp.getOut());
        pee.exportExcel();
    }

    //资源详情
    @GetMapping("/resource")
    public Result resource(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.resource(adminParameter, httpSession);
    }

    //资源增删改
    @PostMapping("/resource/sud")
    public Result userSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.resourceSud(adminParameter, httpSession);
    }

    //供应商列表
    @GetMapping("/supplier/list")
    public Object supplierList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.supplierList(adminParameter, httpSession).getData();
    }

    //供应商列表
    @GetMapping("/supplier/export")
    public void supplierExport(@ModelAttribute AdminParameter adminParameter, HttpServletRequest req, HttpServletResponse resp, HttpSession httpSession) {
        List<Supplier> supplierList = (List<Supplier>) adminTwoService.supplierList(adminParameter, httpSession).getData();
        List<AdminParameter> adminParameterList = new ArrayList<>();
        supplierList.forEach(e -> {
            AdminParameter parameter = new AdminParameter();
            parameter.setSupplierid(e.getId());
            parameter.setName(e.getName());
            parameter.setContacts(e.getContacts());
            parameter.setMobile(e.getMobile());
            e.getProducts().forEach(k -> {
                adminParameter.setProductsname(adminParameter.getProductsname() + "、" + k.getMaterial().getName());
            });
            adminParameter.setProductsname(adminParameter.getProductsname().substring(1));
            parameter.setFapiao(e.getFapiao());
            parameter.setZhanghu(e.getZhanghu());
            parameter.setShoukuan(e.getShoukuan());
            parameter.setKaihu(e.getKaihu());
            adminParameterList.add(parameter);
        });
        //执行导出
        String fileName = "供应商表.xls";
        ServletUtil su = new ServletUtil(fileName, req, resp);
        su.poiExcelServlet();
        String[] heads = {"序号", "供应商名称", "联系人", "联系电话", "产品类型", "开票抬头", "账户银行", "收款账户", "开户行"};
        String[] cols = {"supplierid", "name", "contacts", "mobile", "productsname", "fapiao", "zhanghu", "shoukuan", "kaihu"};
        //这里传第几个字段是数字，从0开始
        int[] numerics = {0};
        ServletUtil suresp = new ServletUtil(resp);
        PoiExcelExport<AdminParameter> pee = new PoiExcelExport<>(fileName, heads, cols, adminParameterList, numerics, suresp.getOut());
        pee.exportExcel();
    }

    //供应商详情
    @GetMapping("/supplier")
    public Result user(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.supplier(adminParameter, httpSession);
    }

    //供应商增删改
    @PostMapping("/supplier/sud")
    public Result supplierSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.supplierSud(adminParameter, httpSession);
    }

    //耗材列表
    //type1 findDistinctCode
    //type2 findByCode
    //type3 findDistinctName
    @GetMapping("/material/list")
    public Object materialList(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.materialList(adminParameter, httpSession).getData();
    }

    //耗材增删改
    @PostMapping("/material/sud")
    public Result materialSud(@ModelAttribute AdminParameter adminParameter, HttpSession httpSession) {
        return adminTwoService.materialSud(adminParameter, httpSession);
    }

}
