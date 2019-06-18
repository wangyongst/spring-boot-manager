package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;


@Service("AdminTwoService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminTwoServiceImpl implements AdminTwoService {

    private static final Logger logger = LogManager.getLogger(AdminTwoServiceImpl.class);

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Result askList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(askRepository.findAll());
    }

    @Override
    public Result applyList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(applyRepository.findAll());
    }

    @Override
    public Result projectList(AdminParameter adminParameter, HttpSession httpSession) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        if (StringUtils.isBlank(adminParameter.getCustomer()) && StringUtils.isNotBlank(adminParameter.getName())) {
            return ResultUtil.okWithData(projectRepository.findByNameLike("%" + adminParameter.getName() + "%", sort));
        } else if (StringUtils.isNotBlank(adminParameter.getCustomer()) && StringUtils.isBlank(adminParameter.getName())) {
            return ResultUtil.okWithData(projectRepository.findByCustomerLike("%" + adminParameter.getCustomer() + "%", sort));
        } else if (StringUtils.isNotBlank(adminParameter.getCustomer()) && StringUtils.isNotBlank(adminParameter.getName())) {
            return ResultUtil.okWithData(projectRepository.findByCustomerLikeAndNameLike("%" + adminParameter.getCustomer() + "%", "%" + adminParameter.getName() + "%", sort));
        } else {
            return ResultUtil.okWithData(projectRepository.findAll(sort));
        }
    }

    @Override
    public Result project(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(projectRepository.findById(adminParameter.getProjectid()).get());
    }

    @Override
    public Result projectSud(AdminParameter adminParameter, HttpSession httpSession) {
        Project project = null;
        if (adminParameter.getProjectid() == 0) {
            project = new Project();
            project.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            User me = (User) httpSession.getAttribute("user");
            project.setCreateusername(me.getName());
        } else {
            project = projectRepository.findById(adminParameter.getProjectid()).get();
            if (adminParameter.getDelete() != 0) {
                projectRepository.delete(project);
                return ResultUtil.ok();
            }
        }
        if (StringUtils.isBlank(adminParameter.getCustomer())) return ResultUtil.errorWithMessage("客户名称不能为空！");
        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("项目名称不能为空！");
        if (StringUtils.isBlank(adminParameter.getZimu())) return ResultUtil.errorWithMessage("字母简称不能为空！");
        project.setCustomer(adminParameter.getCustomer());
        project.setName(adminParameter.getName());
        project.setZimu(adminParameter.getZimu());
        projectRepository.save(project);
        return ResultUtil.ok();
    }

    @Override
    public Result resourceList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(resourceRepository.findAll());
    }

    @Override
    public Result resource(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(resourceRepository.findById(adminParameter.getResourceid()).get());
    }

    @Override
    public Result resourceSud(AdminParameter adminParameter, HttpSession httpSession) {
        Resource resource = null;
        if (adminParameter.getResourceid() == 0) {
            resource = new Resource();
            resource.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            User me = (User) httpSession.getAttribute("user");
            resource.setCreateuserid(me.getId());
        } else {
            resource = resourceRepository.findById(adminParameter.getResourceid()).get();
            if (adminParameter.getDelete() != 0) {
                resourceRepository.delete(resource);
                return ResultUtil.ok();
            }
        }
//        if (StringUtils.isBlank(adminParameter.getCustomer())) return ResultUtil.errorWithMessage("客户名称不能为空！");
//        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("项目名称不能为空！");
//        if (StringUtils.isBlank(adminParameter.getZimu())) return ResultUtil.errorWithMessage("字母简称不能为空！");
        //resource.setMaterialid(adminParameter.getMaterialid());
        resource.setSize(adminParameter.getSize());
        resource.setSpecial(adminParameter.getSpecial());
        resource.setModel(adminParameter.getModel());
        resourceRepository.save(resource);
        return ResultUtil.ok();
    }

    @Override
    public Result supplierList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(supplierRepository.findAll());
    }

    @Override
    public Result supplier(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(supplierRepository.findById(adminParameter.getSupplierid()).get());
    }

    @Override
    public Result supplierSud(AdminParameter adminParameter, HttpSession httpSession) {
        Supplier supplier = null;
//        if (adminParameter.getSupplierid() == 0) {
//            supplier = new Supplier();
//            private String name;
//            private String userid;
//            private String product;
//            private String fapiao;
//            private String yinhang;
//            private String zhanghu;
//            private String kaihu;
//        } else {
//            resource = resourceRepository.findById(adminParameter.getResourceid()).get();
//            if (adminParameter.getDelete() != 0) {
//                resourceRepository.delete(resource);
//                return ResultUtil.ok();
//            }
//        }
////        if (StringUtils.isBlank(adminParameter.getCustomer())) return ResultUtil.errorWithMessage("客户名称不能为空！");
////        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("项目名称不能为空！");
////        if (StringUtils.isBlank(adminParameter.getZimu())) return ResultUtil.errorWithMessage("字母简称不能为空！");
//        resource.setMaterialid(adminParameter.getMaterialid());
//        resource.setSize(adminParameter.getSize());
//        resource.setSpecial(adminParameter.getSpecial());
//        resource.setModel(adminParameter.getModel());
//        resourceRepository.save(resource);
        return ResultUtil.ok();
    }

    @Override
    public Result materialList(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(materialRepository.findAll());
    }

    @Override
    public Result material(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(materialRepository.findById(adminParameter.getMaterialid()).get());
    }

    @Override
    public Result materialSud(AdminParameter adminParameter, HttpSession httpSession) {
        Material material = null;
        if (adminParameter.getMaterialid() == 0) {
            material = new Material();
        } else {
            material = materialRepository.findById(adminParameter.getMaterialid()).get();
            if (adminParameter.getDelete() != 0) {
                materialRepository.delete(material);
                return ResultUtil.ok();
            }
        }
        if (StringUtils.isBlank(adminParameter.getCode())) return ResultUtil.errorWithMessage("耗材编号不能为空！");
        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("耗材名称不能为空！");
        material.setCode(adminParameter.getCode());
        material.setName(adminParameter.getName());
        materialRepository.save(material);
        return ResultUtil.ok();
    }

    @Override
    public Result apply(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(applyRepository.findById(adminParameter.getUserid()).get());
    }
}
