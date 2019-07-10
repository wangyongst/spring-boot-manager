package com.spring.boot.manager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.model.weixin.WeiXinM;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminService;
import com.spring.boot.manager.service.AdminTwoService;
import com.spring.boot.manager.utils.Status;
import com.spring.boot.manager.utils.WeixinUtils;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@Service
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminTwoServiceImpl implements AdminTwoService {

    private static final Logger logger = LogManager.getLogger(AdminTwoServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public AdminService adminService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private PurchRepository purchRepository;

    @Autowired
    private RequestRepository requestRepository;


    @Override
    public Result sendMessage(AdminParameter adminParameter) {
        String response = WeixinUtils.getAccessToken(restTemplate);
        ObjectMapper mapper = new ObjectMapper();
        try {
            WeiXinM weiXinM = mapper.readValue(response, WeiXinM.class);
            if (weiXinM.getErrcode() != null && weiXinM.getErrcode() == 0 && StringUtils.isNotBlank(weiXinM.getAccess_token())) {
                response = WeixinUtils.sendMessage(restTemplate, weiXinM.getAccess_token(), null, null);
                weiXinM = mapper.readValue(response, WeiXinM.class);
                if (weiXinM.getErrcode() != null && weiXinM.getErrcode() == 0) return ResultUtil.ok();
                else return ResultUtil.errorWithMessage(weiXinM.getErrmsg());
            } else return ResultUtil.errorWithMessage(weiXinM.getErrmsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.errorWithMessage("消息发送失败");
    }

    @Override
    public Result projectList(AdminParameter adminParameter) {
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
    public Result project(AdminParameter adminParameter) {
        return ResultUtil.okWithData(projectRepository.findById(adminParameter.getProjectid()).get());
    }

    @Override
    public Result projectSud(AdminParameter adminParameter) {
        Project project = null;
        if (adminParameter.getProjectid() == 0) {
            project = new Project();
            project.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            User me = (User) SecurityUtils.getSubject().getPrincipal();
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
    public Result resourceList(AdminParameter adminParameter) {
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
    public Result resource(AdminParameter adminParameter) {
        return ResultUtil.okWithData(resourceRepository.findById(adminParameter.getResourceid()).get());
    }

    @Override
    public Result resourceSud(AdminParameter adminParameter) {
        Resource resource = null;
        if (adminParameter.getResourceid() == 0) {
            resource = new Resource();
            resource.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            User me = (User) SecurityUtils.getSubject().getPrincipal();
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
    public Result supplierList(AdminParameter adminParameter) {
        if (StringUtils.isNotBlank(adminParameter.getName())) {
            return ResultUtil.okWithData(supplierRepository.findByNameContains(adminParameter.getName()));
        } else {
            return ResultUtil.okWithData(supplierRepository.findAll());
        }
    }

    @Override
    public Result supplier(AdminParameter adminParameter) {
        return ResultUtil.okWithData(supplierRepository.findById(adminParameter.getSupplierid()).get());
    }

    @Override
    public Result supplierSud(AdminParameter adminParameter) {
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
    public Result materialList(AdminParameter adminParameter) {
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
    public Result materialSud(AdminParameter adminParameter) {
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


    @Override
    public Result purchList(AdminParameter adminParameter) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                if (adminParameter.getAskid() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("ask").get("id"), adminParameter.getAskid()));
                }
                if (adminParameter.getStatus() != 0 && adminParameter.getStatus() < 10) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), adminParameter.getStatus()));
                }
                if (adminParameter.getStatus() != 0 && adminParameter.getStatus() == 29) {
                    predicates.add(criteriaBuilder.between(root.get("status"), 2, 9));
                    predicates.add(criteriaBuilder.equal(root.get("ask").get("type"), 3));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return ResultUtil.okWithData(purchRepository.findAll(specification));
    }

    @Override
    public Result purchSud(AdminParameter adminParameter) {
        Purch purch = purchRepository.findById(adminParameter.getPurchid()).get();
        if (adminParameter.getDelete() != 0) {
            if (purch.getStatus() < Status.SIX)
                deletePurch(purch);
            return ResultUtil.ok();
        }
        return ResultUtil.ok();
    }

    @Override
    public Result purchCoc(AdminParameter adminParameter) {
        Purch purch = purchRepository.findById(adminParameter.getPurchid()).get();
        if (purch.getStatus() == Status.TWO) {
            purch.getAsk().setConfirmtime(TimeUtils.format(System.currentTimeMillis()));
            purch.setStatus(Status.THREE);
        } else if (purch.getStatus() == Status.THREE) {
            purch.getAsk().setConfirmtime(null);
            purch.setStatus(Status.TWO);
        }
        purchRepository.save(purch);
        return ResultUtil.ok();
    }

    @Override
    public Result askList(AdminParameter adminParameter) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                if (StringUtils.isNotBlank(adminParameter.getCreatetime())) {
                    predicates.add(criteriaBuilder.like(root.get("createtime"), adminParameter.getCreatetime() + "%"));
                }
                if (adminParameter.getStatus() != 0) {
                    if (adminParameter.getStatus() == 99) {
                        predicates.add(criteriaBuilder.notEqual(root.get("status"), 2));
                    } else if (adminParameter.getStatus() == 100) {
                        predicates.add(criteriaBuilder.equal(root.get("status"), 2));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        return ResultUtil.okWithData(askRepository.findAll(specification, sort));
    }

    @Override
    public Result askSud(AdminParameter adminParameter) {
        if (adminParameter.getDelete() == 1) {
            Ask ask = askRepository.findById(adminParameter.getAskid()).get();
            deleteAsk(ask);
        }
        return ResultUtil.ok();
    }

    @Override
    public Result requestList(AdminParameter adminParameter) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                if (StringUtils.isNotBlank(adminParameter.getName())) {
                    predicates.add(criteriaBuilder.equal(root.get("resource").get("project").get("name"), adminParameter.getName()));
                }
                if (StringUtils.isNotBlank(adminParameter.getName2())) {
                    predicates.add(criteriaBuilder.equal(root.get("resource").get("material").get("name"), adminParameter.getName2()));
                }
                if (StringUtils.isNotBlank(adminParameter.getCustomer())) {
                    predicates.add(criteriaBuilder.like(root.get("resource").get("project").get("customer"), "%" + adminParameter.getCustomer() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        return ResultUtil.okWithData(requestRepository.findAll(specification, sort));
    }

    @Override
    public Result requestSud(AdminParameter adminParameter) {
        Request request = null;
        if (adminParameter.getRequestid() == 0) {
            request = new Request();
            request.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            User me = (User) SecurityUtils.getSubject().getPrincipal();
            request.setCreateuser(me);
            request.setStatus(1);
        } else {
            request = requestRepository.findById(adminParameter.getRequestid()).get();
            if (adminParameter.getDelete() != 0) {
                deleteRequest(request);
                return ResultUtil.ok();
            }
        }
        if (adminParameter.getResourceid() == 0) return ResultUtil.errorWithMessage("请先依次选择客户名称、项目名称和耗材类型！");
        request.setResource(resourceRepository.findById(adminParameter.getResourceid()).get());
        if (StringUtils.isNotBlank(adminParameter.getNum())) {
            if (StringUtils.isBlank(adminParameter.getSellnum())) return ResultUtil.errorWithMessage("销售数量不能为空！");
            if (StringUtils.isBlank(adminParameter.getPrice())) return ResultUtil.errorWithMessage("采购单价不能为空！");
            if (!StringUtils.isNumeric(adminParameter.getNum())) return ResultUtil.errorWithMessage("采购数量只能是整数！");
            if (!StringUtils.isNumeric(adminParameter.getSellnum())) return ResultUtil.errorWithMessage("销售数量只能是整数！");
            if (!adminParameter.getPrice().matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"))
                return ResultUtil.errorWithMessage("采购单价只能是两位小数或整数！");
            request.setNum(Integer.parseInt(adminParameter.getNum()));
            request.setSellnum(Integer.parseInt(adminParameter.getSellnum()));
            request.setPrice(BigDecimal.valueOf(Double.parseDouble(adminParameter.getPrice())));
            request.setTotal(request.getPrice().multiply(new BigDecimal(request.getSellnum().toString())));
        }
        requestRepository.save(request);
        return ResultUtil.okWithData(request);
    }

    @Override
    public Result request(AdminParameter adminParameter) {
        return ResultUtil.okWithData(requestRepository.findById(adminParameter.getRequestid()).get());
    }

    @Override
    public Result requestAsk(AdminParameter adminParameter) {
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        String[] ids = adminParameter.getIds().split(",");
        for (String id : ids) {
            Request request = requestRepository.findById(Integer.parseInt(id)).get();
            Ask ask = new Ask();
            ask.setStatus(Status.ONE);
            ask.setRequest(request);
            ask.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            ask.setCreateusername(me.getName());
            //询价
            if (adminParameter.getType() == 1) {
                if ((request.getNum() == null || request.getNum() == 0) && (request.getSellnum() == null || request.getSellnum() == 0)) {
                    ask.setType(1);
                    final Ask saveedask = askRepository.save(ask);
                    productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                        Purch purch = new Purch();
                        purch.setAsk(saveedask);
                        purch.setSupplier(e.getSupplier());
                        purch.setStatus(Status.ONE);
                        purchRepository.save(purch);
                    });
                } else {
                    return ResultUtil.errorWithMessage("采购数量必须为0，销售数量必须为0！");
                }
                //打样
            } else if (adminParameter.getType() == 2) {
                if (request.getNum() != null && request.getNum() == 1 && (request.getSellnum() == null || request.getSellnum() == 0)) {
                    ask.setType(2);
                    final Ask saveedask = askRepository.save(ask);
                    productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                        Purch purch = new Purch();
                        purch.setAsk(saveedask);
                        purch.setSupplier(e.getSupplier());
                        purch.setStatus(Status.THREE);
                        purchRepository.save(purch);
                    });
                } else {
                    return ResultUtil.errorWithMessage("采购数量必须为1，销售数量必须为0！");
                }
                //采购
            } else if (adminParameter.getType() == 3) {
                if ((request.getNum() == null || request.getNum() == 0) || (request.getSellnum() == null || request.getSellnum() == 0) || (request.getPrice() == null || request.getSellnum() == 0))
                    return ResultUtil.errorWithMessage("采购数量、销售数量不能为0！");
                ask.setType(3);
                final Ask saveedask = askRepository.save(ask);
                productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                    Purch purch = new Purch();
                    purch.setAsk(saveedask);
                    purch.setSupplier(e.getSupplier());
                    purch.setStatus(Status.ONE);
                    purchRepository.save(purch);
                });
            }
        }
        return ResultUtil.ok();
    }

    public void deleteProject(Project project) {
        resourceRepository.findByProject(project).forEach(e -> {
            deleteResource(e);
        });
        projectRepository.delete(project);
    }


    public void deleteResource(Resource resource) {
        requestRepository.findAllByResource(resource).forEach(e -> {
            deleteRequest(e);
        });
        resourceRepository.delete(resource);
    }

    public void deleteSupplier(Supplier supplier) {
        productRepository.findBySupplier(supplier).forEach(e -> {
            deleteProduct(e);
        });
        roleRepository.findAllBySupplierid(supplier.getId()).forEach(e -> {
            adminService.deleteRole(e);
        });
        userRepository.findBySupplier(supplier).forEach(e -> {
            e.setSupplier(null);
            userRepository.save(e);
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

    public void deleteRequest(Request request) {
        askRepository.findAllByRequest(request).forEach(e -> {
            deleteAsk(e);
        });
        requestRepository.delete(request);
    }

    public void deletePurch(Purch purch) {
        purchRepository.delete(purch);
    }

    public void deleteAsk(Ask ask) {
        purchRepository.findAllByAsk(ask).forEach(e -> {
            deletePurch(e);
        });
        askRepository.delete(ask);
    }

}
