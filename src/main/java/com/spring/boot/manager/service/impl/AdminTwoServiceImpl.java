package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminTwoServiceImpl implements AdminTwoService {

    private static final Logger logger = LogManager.getLogger(AdminTwoServiceImpl.class);


    @Autowired
    public AdminService adminService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MaterialRepository materialRepository;


    @Override
    public Result projectList(AdminParameter adminParameter, HttpSession httpSession) {
        if (adminParameter.getType() == 1) {
            return ResultUtil.okWithData(projectRepository.findDistinctCustomer());
        } else if (adminParameter.getType() == 2) {
            return ResultUtil.okWithData(projectRepository.findDistinctNameByCustomer(adminParameter.getCustomer()));
        } else if (adminParameter.getType() == 3) {
            return ResultUtil.okWithData(projectRepository.findDistinctName());
        } else {
            Project project = new Project();
            if (StringUtils.isNotBlank(adminParameter.getName())) project.setName(adminParameter.getName());
            if (StringUtils.isNotBlank(adminParameter.getCustomer())) project.setCustomer(adminParameter.getCustomer());
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("name", match -> match.contains())
                    .withMatcher("customer", match -> match.contains());
            Example<Project> example = Example.of(project, matcher);
            Sort sort = new Sort(Sort.Direction.DESC, "createtime");
            return ResultUtil.okWithData(projectRepository.findAll(example, sort));
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
                deleteProject(project);
                return ResultUtil.ok();
            }
        }
        if (StringUtils.isBlank(adminParameter.getCustomer())) return ResultUtil.errorWithMessage("客户名称未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("项目名称未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getZimu())) return ResultUtil.errorWithMessage("字母简称未填写，无法提交");
        project.setCustomer(adminParameter.getCustomer());
        project.setName(adminParameter.getName());
        project.setZimu(adminParameter.getZimu());
        projectRepository.save(project);
        return ResultUtil.ok();
    }

    @Override
    public Result resourceList(AdminParameter adminParameter, HttpSession httpSession) {
        Project project = new Project();
        Material material = new Material();
        Resource resource = new Resource();
        if (StringUtils.isNotBlank(adminParameter.getName2())) {
            project.setName(adminParameter.getName2());
        }
        if (StringUtils.isNotBlank(adminParameter.getName())) {
            material.setName(adminParameter.getName());
        }
        resource.setMaterial(material);
        resource.setProject(project);
        Example<Resource> example = Example.of(resource);
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        return ResultUtil.okWithData(resourceRepository.findAll(example, sort));
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
            resource.setCreateusername(me.getName());
        } else {
            resource = resourceRepository.findById(adminParameter.getResourceid()).get();
            if (adminParameter.getDelete() != 0) {
                deleteResource(resource);
                return ResultUtil.ok();
            }
        }
        if (adminParameter.getProjectid() == 0) return ResultUtil.errorWithMessage("项目名称未选择无法提交");
        if (adminParameter.getMaterialid() == 0) return ResultUtil.errorWithMessage("耗材类型未选择无法提交");
        if (StringUtils.isBlank(adminParameter.getSize())) return ResultUtil.errorWithMessage("尺寸大小未填写，无法提交！");
        if (StringUtils.isBlank(adminParameter.getSpecial())) return ResultUtil.errorWithMessage("特殊要求未填写，无法提交！");
        if (StringUtils.isBlank(adminParameter.getModel())) return ResultUtil.errorWithMessage("材质规格未填写，无法提交！");
        resource.setProject(projectRepository.findById(adminParameter.getProjectid()).get());
        resource.setMaterial(materialRepository.findById(adminParameter.getMaterialid()).get());
        resource.setSize(adminParameter.getSize());
        resource.setSpecial(adminParameter.getSpecial());
        resource.setModel(adminParameter.getModel());
        resourceRepository.save(resource);
        return ResultUtil.ok();
    }

    @Override
    public Result supplierList(AdminParameter adminParameter, HttpSession httpSession) {
        if (StringUtils.isNotBlank(adminParameter.getName())) {
            return ResultUtil.okWithData(supplierRepository.findByNameContains(adminParameter.getName()));
        } else {
            return ResultUtil.okWithData(supplierRepository.findAll());
        }
    }

    @Override
    public Result supplier(AdminParameter adminParameter, HttpSession httpSession) {
        return ResultUtil.okWithData(supplierRepository.findById(adminParameter.getSupplierid()).get());
    }

    @Override
    public Result supplierSud(AdminParameter adminParameter, HttpSession httpSession) {
        Supplier supplier = null;
        if (adminParameter.getSupplierid() == 0) {
            supplier = new Supplier();
        } else {
            supplier = supplierRepository.findById(adminParameter.getSupplierid()).get();
            if (adminParameter.getDelete() != 0) {
                deleteSupplier(supplier);
                return ResultUtil.ok();
            }
        }
        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("供应商名称未填写，无法提交");
        List<Supplier> supplierList = supplierRepository.findByName(adminParameter.getName());
        if (supplierList.size() > 0 && supplier.getId() != supplierList.get(0).getId())
            return ResultUtil.errorWithMessage("供应商名称重复，无法提交");
        if (StringUtils.isBlank(adminParameter.getContacts())) return ResultUtil.errorWithMessage("联系人未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getMobile())) return ResultUtil.errorWithMessage("联系电话未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getFapiao())) return ResultUtil.errorWithMessage("开票抬头未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getZhanghu())) return ResultUtil.errorWithMessage("账户银行未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getShoukuan())) return ResultUtil.errorWithMessage("收款账户未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getKaihu())) return ResultUtil.errorWithMessage("开户行未填写，无法提交");
        if (adminParameter.getProducts() == null || adminParameter.getProducts().size() == 0)
            return ResultUtil.errorWithMessage("产品类型未选择，无法提交");
        supplier.setName(adminParameter.getName());
        supplier.setContacts(adminParameter.getContacts());
        supplier.setMobile(adminParameter.getMobile());
        supplier.setFapiao(adminParameter.getFapiao());
        supplier.setZhanghu(adminParameter.getZhanghu());
        supplier.setShoukuan(adminParameter.getShoukuan());
        supplier.setKaihu(adminParameter.getKaihu());
        final Supplier savedSupplier = supplierRepository.save(supplier);
        productRepository.findBySupplier(savedSupplier).forEach(e -> {
            deleteProduct(e);
        });
        adminParameter.getProducts().forEach(e -> {
            if (e != null && e != 0) {
                Product product = new Product();
                product.setSupplier(savedSupplier);
                product.setMaterial(materialRepository.findById(e).get());
                productRepository.save(product);
            }
        });
        return ResultUtil.ok();
    }

    @Override
    public Result materialList(AdminParameter adminParameter, HttpSession httpSession) {
        if (adminParameter.getType() == 1) {
            return ResultUtil.okWithData(materialRepository.findDistinctCode());
        } else if (adminParameter.getType() == 2) {
            return ResultUtil.okWithData(materialRepository.findByCode(adminParameter.getCode()));
        } else if (adminParameter.getType() == 3) {
            return ResultUtil.okWithData(materialRepository.findDistinctName());
        } else {
            return ResultUtil.okWithData(materialRepository.findAll());
        }
    }

    @Override
    public Result materialSud(AdminParameter adminParameter, HttpSession httpSession) {
        Material material = null;
        if (adminParameter.getMaterialid() == 0) {
            material = new Material();
        } else {
            material = materialRepository.findById(adminParameter.getMaterialid()).get();
            if (adminParameter.getDelete() != 0) {
                deleteMaterial(material);
                return ResultUtil.ok();
            }
        }
        if (StringUtils.isBlank(adminParameter.getCode())) return ResultUtil.errorWithMessage("耗材编号未填写，无法提交");
        if (StringUtils.isBlank(adminParameter.getName())) return ResultUtil.errorWithMessage("耗材名称未填写，无法提交");
        material.setCode(adminParameter.getCode());
        material.setName(adminParameter.getName());
        materialRepository.save(material);
        return ResultUtil.ok();
    }

    public void deleteProject(Project project) {
        resourceRepository.findByProject(project).forEach(e -> {
            deleteResource(e);
        });
        projectRepository.delete(project);
    }


    public void deleteResource(Resource resource) {
        resourceRepository.delete(resource);
    }

    public void deleteSupplier(Supplier supplier) {
        productRepository.findBySupplier(supplier).forEach(e -> {
            productRepository.delete(e);
        });
        supplierRepository.delete(supplier);
    }

    public void deleteMaterial(Material material) {
        productRepository.findByMaterial(material).forEach(e -> {
            deleteProduct(e);
        });
        resourceRepository.findByMaterial(material).forEach(e -> {
            deleteResource(e);
        });
        materialRepository.delete(material);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

}
