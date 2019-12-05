package com.spring.boot.manager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.model.vo.CountV;
import com.spring.boot.manager.model.weixin.WXData;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
    private DeliverRepository deliverRepository;

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

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BilldetailRepository billdetailRepository;


    public Result sendMessage(Object object, int type, String message) {
        Map<String, WXData> data = new HashMap<String, WXData>();
        List<User> userList = new ArrayList<>();
        if (type == 1) {
            Purch p = (Purch) object;
            userList = userRepository.findBySupplier(p.getSupplier());
            WXData first = new WXData();
            first.setValue("您好，您有新的采购单等待处理");
            data.put("first", first);
            WXData keyword1 = new WXData();
            keyword1.setValue(p.getId() + "");
            data.put("keyword1", keyword1);
            WXData keyword2 = new WXData();
            keyword2.setValue("宝时物流");
            data.put("keyword2", keyword2);
            WXData keyword3 = new WXData();
            keyword3.setValue(p.getAsk().getRequest().getPrice().multiply(new BigDecimal(p.getAsk().getRequest().getNum())).toString());
            data.put("keyword3", keyword3);
            WXData keyword4 = new WXData();
            Setting setting = settingRepository.findByType(3).get(0);
            keyword4.setValue("有新的采购单待处理，不做更改将于" + setting.getValue() + "小时自动下发供应商");
            data.put("keyword4", keyword4);
        } else if (type == 2) {
            Bill b = (Bill) object;
            userList = userRepository.findBySupplier(b.getSupplier());
            WXData first = new WXData();
            first.setValue("您好，您的对账单已生成。");
            data.put("first", first);
            WXData keyword1 = new WXData();
            keyword1.setValue(b.getCreatetime());
            data.put("keyword1", keyword1);
            WXData keyword2 = new WXData();
            keyword2.setValue(b.getBilldetails().size() + "单");
            data.put("keyword2", keyword2);
            WXData keyword3 = new WXData();
            keyword3.setValue(b.getTotal() + "元");
            data.put("keyword3", keyword3);
            WXData remark = new WXData();
            remark.setValue("宝时物流已经完成" + b.getBilltime() + "对账，点击查看详情");
            data.put("remark", remark);
        } else if (type == 3) {
            Purch p = (Purch) object;
            userList = userRepository.findBySupplier(p.getSupplier());
            WXData first = new WXData();
            first.setValue("您好，宝时物流向您发送了一份" + message);
            data.put("first", first);
            WXData keyword1 = new WXData();
            keyword1.setValue(p.getId() + "");
            data.put("keyword1", keyword1);
            WXData keyword2 = new WXData();
            keyword2.setValue("宝时物流");
            data.put("keyword2", keyword2);
            WXData remark = new WXData();
            remark.setValue("待报价耗材1款，请注意查看");
            data.put("remark", remark);
        }
        return sendMessage(userList, data, type);
    }

    @Override
    public Result billTime() {
        return ResultUtil.okWithData(billRepository.findDistinctBillTime());
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
        if (adminParameter.getType() == 3) {
            return ResultUtil.okWithData(resourceRepository.findDistinctName());
        }
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
        if (StringUtils.isBlank(adminParameter.getCode())) return ResultUtil.errorWithMessage("耗材编号未填写，无法提交！");
        if (StringUtils.isBlank(adminParameter.getSize())) return ResultUtil.errorWithMessage("尺寸大小未填写，无法提交！");
        if (StringUtils.isBlank(adminParameter.getSpecial())) return ResultUtil.errorWithMessage("特殊要求未填写，无法提交！");
        if (StringUtils.isBlank(adminParameter.getModel())) return ResultUtil.errorWithMessage("材质规格未填写，无法提交！");
//        if (resourceRepository.findByCode(adminParameter.getCode()).size() > 0)
//            return ResultUtil.errorWithMessage("耗材编号已经存在，无法提交！");
        resource.setProject(projectRepository.findById(adminParameter.getProjectid()).get());
        resource.setMaterial(materialRepository.findById(adminParameter.getMaterialid()).get());
        resource.setCode(adminParameter.getCode());
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
                predicates.add(criteriaBuilder.between(root.get("status"), 3, 9));
                predicates.add(criteriaBuilder.equal(root.get("ask").get("type"), 3));
                predicates.add(criteriaBuilder.equal(root.get("islower"), 1));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<String> orders = new ArrayList<>();
        orders.add("ask.createtime");
        Sort sort = new Sort(Sort.Direction.DESC, orders);
        return ResultUtil.okWithData(purchRepository.findAll(specification, sort));
    }

    @Override
    public Result financeHistory(AdminParameter adminParameter) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                predicates.add(criteriaBuilder.between(root.get("status"), 3, 9));
                predicates.add(criteriaBuilder.equal(root.get("ask").get("type"), 3));
                predicates.add(criteriaBuilder.equal(root.get("islower"), 1));
                Predicate predicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                if (StringUtils.isNotBlank(adminParameter.getName())) {
                    String name = "%" + adminParameter.getName() + "%";
                    Predicate p = criteriaBuilder.like(root.get("supplier").get("name"), name);
                    Predicate p2 = criteriaBuilder.like(root.get("ask").get("request").get("resource").get("project").get("name"), name);
                    Predicate p3 = criteriaBuilder.like(root.get("ask").get("request").get("resource").get("project").get("customer"), name);
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(p, p2, p3));

                }
                if (StringUtils.isNotBlank(adminParameter.getCreatetime()) && StringUtils.isNotBlank(adminParameter.getAccepttime())) {
                    Predicate p = criteriaBuilder.between(root.get("ask").get("request").get("createtime"), adminParameter.getCreatetime(),adminParameter.getAccepttime());
                    Predicate p2 = criteriaBuilder.between(root.get("ask").get("overtime"), adminParameter.getCreatetime(),adminParameter.getAccepttime());
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(p, p2));
                }
                return predicate;
            }
        };
        List<String> orders = new ArrayList<>();
        orders.add("ask.createtime");
        Sort sort = new Sort(Sort.Direction.DESC, orders);
        return ResultUtil.okWithData(purchRepository.findAll(specification, sort));
    }

    @Override
    public Result purchSud(AdminParameter adminParameter) {
        Purch purch = purchRepository.findById(adminParameter.getPurchid()).get();
        if (adminParameter.getDelete() != 0) {
            if (purch.getStatus() <= Status.FIVE && purch.getDelivers() == null) {
                deletePurch(purch);
            } else {
                return ResultUtil.errorWithMessage("当前状态不允许撤回");
            }
            return ResultUtil.ok();
        }
        return ResultUtil.ok();
    }

    @Override
    public Result purchCoc(AdminParameter adminParameter) {
        Purch purch = purchRepository.findById(adminParameter.getPurchid()).get();
        if (purch.getStatus() == Status.FOUR) {
            purch.getAsk().setConfirmtime(TimeUtils.format(System.currentTimeMillis()));
            purch.setStatus(Status.THREE);
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
                        predicates.add(criteriaBuilder.equal(root.get("status"), 1));
                    } else if (adminParameter.getStatus() == 100) {
                        predicates.add(criteriaBuilder.notEqual(root.get("status"), 1));
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

    public String makeNumber() {
        String preNumber = "BSHC" + TimeUtils.formatMD(System.currentTimeMillis());
        Request request = requestRepository.findTop1ByNumberLikeOrderByNumberDesc(preNumber + "%");
        if (request == null) return preNumber + "001";
        String numberSuf = (Integer.parseInt(request.getNumber().substring(10)) + 1) + "";
        if (numberSuf.length() <= 1) numberSuf = "00" + numberSuf;
        if (numberSuf.length() <= 2) numberSuf = "0" + numberSuf;
        return preNumber + numberSuf;
    }


    @Override
    public Result requestSud(AdminParameter adminParameter) {
        Setting setting = settingRepository.findByType(1).get(0);
        Request request = null;
        if (adminParameter.getRequestid() == 0) {
            request = new Request();
            request.setNumber(makeNumber());
            request.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            User me = (User) SecurityUtils.getSubject().getPrincipal();
            request.setCreateusername(me.getName());
            request.setCreateusermobile(me.getMobile());
        } else {
            request = requestRepository.findById(adminParameter.getRequestid()).get();
            if (adminParameter.getDelete() != 0) {
                request.setStatus(Status.ZERO);
                requestRepository.save(request);
                askRepository.findAllByRequest(request).forEach(e -> {
                    e.setStatus(Status.FOUR);
                    askRepository.save(e);
                    purchRepository.findAllByAsk(e).forEach(t -> {
                        t.setStatus(Status.FOUR);
                        t.setIslower(0);
                        purchRepository.save(t);
                    });
                });
                return ResultUtil.ok();
            }
        }
        if (adminParameter.getResourceid() == 0) return ResultUtil.errorWithMessage("请先依次选择客户名称、项目名称和耗材类型！");
        request.setResource(resourceRepository.findById(adminParameter.getResourceid()).get());
        if (StringUtils.isNotBlank(adminParameter.getNum())) {
            if (StringUtils.isBlank(adminParameter.getSellnum())) return ResultUtil.errorWithMessage("销售数量不能为空！");
            if (!StringUtils.isNumeric(adminParameter.getNum())) return ResultUtil.errorWithMessage("采购数量只能是整数！");
            if (!StringUtils.isNumeric(adminParameter.getSellnum())) return ResultUtil.errorWithMessage("销售数量只能是整数！");
            if (StringUtils.isNotBlank(adminParameter.getPrice()) && !adminParameter.getPrice().matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"))
                return ResultUtil.errorWithMessage("采购单价只能是两位小数或整数！");
            request.setNum(Integer.parseInt(adminParameter.getNum()));
            request.setSellnum(Integer.parseInt(adminParameter.getSellnum()));
            if (StringUtils.isNotBlank(adminParameter.getPrice())) {
                request.setPrice(BigDecimal.valueOf(Double.parseDouble(adminParameter.getPrice())));
                request.setTotal(request.getPrice().multiply(new BigDecimal(request.getSellnum().toString())));
            }
        }
        requestRepository.save(request);
        return ResultUtil.ok();
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
            Request request = requestRepository.findTop1ByNumberLikeOrderByNumberDesc(id);//requestRepository.findById(Integer.parseInt(id)).get();
            if (request.getType() != null && request.getType() != 0) return ResultUtil.errorWithMessage("每个采购单只能发起一次！");
            Ask ask = new Ask();
            ask.setStatus(Status.ONE);
            ask.setRequest(request);
            ask.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            ask.setCreateusername(me.getName());
            //询价
            if (adminParameter.getType() == 1) {
                if ((request.getNum() == null || request.getNum() == 0) && (request.getSellnum() == null || request.getSellnum() == 0)) {
                    request.setType(1);
                    request.setStatus(Status.ONE);
                    requestRepository.save(request);
                    ask.setType(1);
                    final Ask saveedask = askRepository.save(ask);
                    productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                        Purch purch = new Purch();
                        purch.setAsk(saveedask);
                        purch.setSupplier(e.getSupplier());
                        purch.setStatus(Status.ONE);
                        purchRepository.save(purch);
                        sendMessage(purch, 3, "询价单");
                    });
                } else {
                    return ResultUtil.errorWithMessage("采购数量必须为0，销售数量必须为0！");
                }
                //打样
            } else if (adminParameter.getType() == 2) {
                if (request.getNum() != null && request.getNum() == 1 && (request.getSellnum() == null || request.getSellnum() == 0)) {
                    request.setType(2);
                    request.setStatus(Status.THREE);
                    requestRepository.save(request);
                    ask.setType(2);
                    ask.setStatus(Status.THREE);
                    final Ask saveedask = askRepository.save(ask);
                    productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                        Purch purch = new Purch();
                        purch.setAsk(saveedask);
                        purch.setSupplier(e.getSupplier());
                        purch.getAsk().setConfirmtime(TimeUtils.format(System.currentTimeMillis()));
                        purch.setStatus(Status.THREE);
                        purchRepository.save(purch);
                    });
                } else {
                    return ResultUtil.errorWithMessage("采购数量必须为1，销售数量必须为0！");
                }
                //采购
            } else if (adminParameter.getType() == 3) {
                if ((request.getNum() == null || request.getNum() == 0) || (request.getSellnum() == null || request.getSellnum() == 0))
                    return ResultUtil.errorWithMessage("采购数量、销售数量不能为0！");
                request.setType(3);
                request.setStatus(Status.ONE);
                requestRepository.save(request);
                ask.setType(3);
                final Ask saveedask = askRepository.save(ask);
                productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                    Purch purch = new Purch();
                    purch.setAsk(saveedask);
                    purch.setSupplier(e.getSupplier());
                    purch.setStatus(Status.ONE);
                    purchRepository.save(purch);
                    sendMessage(purch, 3, "采购单");
                });
            }
        }
        return ResultUtil.ok();
    }

    @Override
    public Result priceSchedu() {
        Setting setting = settingRepository.findByType(2).get(0);
        Integer hous = 0 - setting.getValue().intValue();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hous);
        List<Ask> asks = askRepository.findByStatusAndCreatetimeLessThanEqualAndConfirmtimeIsNull(Status.ONE, TimeUtils.format(cal.getTime().getTime()));
        for (Ask ask : asks) {
            ask.setStatus(Status.FOUR);
            ask.getRequest().setStatus(Status.FOUR);
            List<Purch> purches = purchRepository.findAllByAsk(ask);
            for (Purch purch : purches) {
                purch.setIslower(null);
                purch.setStatus(Status.FOUR);
                purchRepository.save(purch);
            }
            askRepository.save(ask);
        }
        return ResultUtil.ok();
    }


    @Override
    public Result priceSchedu2() {
        Setting setting = settingRepository.findByType(2).get(0);
        Integer hous = 0 - setting.getValue().intValue();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hous);
        List<Ask> asks = askRepository.findByStatusAndCreatetimeLessThanEqual(Status.TWO, TimeUtils.format(cal.getTime().getTime()));
        for (Ask ask : asks) {
            if (ask.getType() == 1) {  //询价
                ask.setStatus(Status.FOUR);
            } else {  //采购
                ask.setStatus(Status.THREE);
                ask.setConfirmtime(TimeUtils.format(System.currentTimeMillis()));
                ask.getRequest().setStatus(Status.THREE);
            }
            Purch lower = purchRepository.findTop1ByAskAndAcceptpriceIsNotNullOrderByAcceptpriceAsc(ask);
            List<Purch> purches = purchRepository.findAllByAsk(ask);
            for (Purch purch : purches) {
                if (lower != null && purch.getId() == lower.getId() && ask.getType() == 3) {
                    purch.setIslower(1);
                    purch.setStatus(Status.THREE);
                    Setting setting2 = settingRepository.findByType(1).get(0);
                    if (purch.getAsk().getRequest().getPrice() == null) purch.getAsk().getRequest().setPrice(purch.getAcceptprice().multiply(setting2.getValue()));
                    if (purch.getAsk().getRequest().getSellnum() != null)
                        purch.getAsk().getRequest().setTotal(purch.getAsk().getRequest().getPrice().multiply(new BigDecimal(purch.getAsk().getRequest().getSellnum())));
                    sendMessage(purch, 1, "");
                } else {
                    purch.setStatus(Status.FOUR);
                }
                purchRepository.save(purch);
            }
            askRepository.save(ask);
        }
        return ResultUtil.ok();
    }

    @Override
    public Result acceptSchedu() {
        Setting setting = settingRepository.findByType(3).get(0);
        Integer hous = 0 - setting.getValue().intValue();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hous);
        List<Ask> asks = askRepository.findByStatusAndConfirmtimeLessThanEqual(Status.THREE, TimeUtils.format(cal.getTime().getTime()));
        for (Ask ask : asks) {
            if (ask.getType() == 2) {
                ask.setStatus(Status.FOUR);
                ask.getRequest().setStatus(Status.FOUR);
                askRepository.save(ask);
            }
            List<Purch> purches = purchRepository.findAllByAsk(ask);
            for (Purch purch : purches) {
                if (purch.getStatus() != Status.FOUR) {
                    purch.setStatus(Status.FOUR);
                    purchRepository.save(purch);
                }
            }
        }
        return ResultUtil.ok();
    }

    @Override
    public Result acceptSchedu2() {
        Setting setting = settingRepository.findByType(3).get(0);
        Integer hous = 0 - setting.getValue().intValue();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hous);
        List<Ask> asks = askRepository.findByStatusAndConfirmtimeLessThanEqual(Status.FIVE, TimeUtils.format(cal.getTime().getTime()));
        for (Ask ask : asks) {
            List<Purch> purches = purchRepository.findAllByAsk(ask);
            for (Purch purch : purches) {
                if (purch.getStatus() == Status.THREE) {
                    purch.setStatus(Status.FOUR);
                    purchRepository.save(purch);
                }
            }
            ask.setStatus(Status.FOUR);
            askRepository.save(ask);
        }
        return ResultUtil.ok();
    }

    @Override
    public Result billSchedu() {
        List<Purch> purchList = purchRepository.findAllByStatus(Status.SEVEN);
        for (Purch purch : purchList) {
            if (purch.getAsk().getType() == Status.THREE) {
                String billtime = purch.getAsk().getOvertime().substring(0, 7);
                List<Bill> bills = billRepository.findBySupplierAndBilltime(purch.getSupplier(), billtime);
                Bill bill = null;
                if (bills.size() == 1) bill = bills.get(0);
                else {
                    bill = new Bill();
                    bill.setBilltime(billtime);
                    bill.setSupplier(purch.getSupplier());
                    bill.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
                    bill.setTotal(new BigDecimal(0));
                    bill.setStatus(Status.ONE);
                    billRepository.save(bill);
                }
                Billdetail billdetail = new Billdetail();
                billdetail.setStatus(Status.ONE);
                billdetail.setBill(bill);
                billdetail.setPurch(purch);
                if (purch.getAcceptprice().doubleValue() != 0.00d && purch.getAcceptnum() != null)
                    bill.setTotal(purch.getAcceptprice().multiply(new BigDecimal(purch.getAcceptnum())).add(bill.getTotal()));
                billdetailRepository.save(billdetail);
            }
            purch.setStatus(Status.EIGHT);
            purch.getAsk().getRequest().setStatus(Status.EIGHT);
            purchRepository.save(purch);
        }
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM--dd");
        List<Bill> bills = billRepository.findByCreatetimeLike(formatters.format(LocalDate.now()));
        for (Bill bill : bills) {
            sendMessage(bill, 2, "");
        }
        return ResultUtil.ok();
    }

    @Override
    public Result billList(AdminParameter adminParameter) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                if (StringUtils.isNotBlank(adminParameter.getBilltime())) {
                    predicates.add(criteriaBuilder.equal(root.get("billtime"), adminParameter.getBilltime()));
                }
                if (StringUtils.isNotBlank(adminParameter.getName())) {
                    predicates.add(criteriaBuilder.like(root.get("supplier").get("name"), "%" + adminParameter.getName() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "billtime");
        return ResultUtil.okWithData(billRepository.findAll(specification,sort));
    }

    @Override
    public Result billdetailList(AdminParameter adminParameter) {
        Bill bill = billRepository.findById(adminParameter.getBillid()).get();
        if (adminParameter.getStatus() == 0) {
            Specification specification = new Specification() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = Lists.newArrayList();
                    predicates.add(criteriaBuilder.equal(root.get("bill").get("id"), bill.getId()));
                    if (StringUtils.isNotBlank(adminParameter.getBilltime())) {
                        predicates.add(criteriaBuilder.like(root.get("purch").get("ask").get("overtime"), "%" + adminParameter.getBilltime() + "%"));
                    }
                    if (StringUtils.isNotBlank(adminParameter.getName())) {
                        predicates.add(criteriaBuilder.like(root.get("bill").get("supplier").get("name"), "%" + adminParameter.getName() + "%"));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            return ResultUtil.okWithData(billdetailRepository.findAll(specification));
        } else return ResultUtil.okWithData(billdetailRepository.findByBillAndStatus(bill, adminParameter.getStatus()));
    }

    @Override
    public Result billdetailSud(AdminParameter adminParameter) {
        Billdetail billdetail = billdetailRepository.findById(adminParameter.getBilldetailid()).get();
        billdetail.setBillno(adminParameter.getBillno());
        billdetail.getBill().setStatus(Status.THREE);
        billdetail.setStatus(Status.THREE);
        billdetailRepository.save(billdetail);
        return ResultUtil.ok();
    }

    @Override
    public Result count(AdminParameter adminParameter) {
        String date = TimeUtils.format(System.currentTimeMillis()).substring(0, 10);
        CountV countV = new CountV();
        countV.setCount1(deliverRepository.countByAccepttimeLike(date + "%"));
        countV.setCount2(askRepository.countByCreatetimeLike(date + "%"));
        double count3 = 0.00d;
        for (Bill bill : billRepository.findByCreatetimeLike(date + "%")) {
            count3 += bill.getTotal().doubleValue();
        }
        countV.setCount3(count3);
        return ResultUtil.okWithData(countV);
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
        userRepository.findBySupplier(supplier).forEach(e -> {
            e.setSupplier(null);
            userRepository.save(e);
        });
        billRepository.findBySupplier(supplier).forEach(e -> {
            deleteBill(e);
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
        billdetailRepository.findByPurch(purch).forEach(e -> {
            deleteBilldetail(e);
        });
        deliverRepository.findByPurch(purch).forEach(e -> {
            deliverRepository.delete(e);
        });
        purchRepository.delete(purch);
    }

    public void deleteAsk(Ask ask) {
        purchRepository.findAllByAsk(ask).forEach(e -> {
            deletePurch(e);
        });
        askRepository.delete(ask);
    }

    public void deleteBilldetail(Billdetail billdetail) {
        billdetailRepository.delete(billdetail);
    }

    public void deleteBill(Bill bill) {
        billdetailRepository.findByBill(bill).forEach(e -> {
            deleteBilldetail(e);
        });
        billRepository.delete(bill);
    }

    public Result sendMessage(List<User> userList, Map<String, WXData> messageData, int type) {
        String response = WeixinUtils.getAccessToken(restTemplate);
        ObjectMapper mapper = new ObjectMapper();
        for (User user : userList) {
            if (StringUtils.isBlank(user.getOpenid())) continue;
            try {
                WeiXinM weiXinM = mapper.readValue(response, WeiXinM.class);
                if (StringUtils.isNotBlank(weiXinM.getAccess_token())) {
                    response = WeixinUtils.sendMessage(type, restTemplate, weiXinM.getAccess_token(), user.getOpenid(), messageData);
                    System.out.println(response);
                    weiXinM = mapper.readValue(response, WeiXinM.class);
                    if (weiXinM.getErrcode() != null && weiXinM.getErrcode() == 0) return ResultUtil.ok();
                    else return ResultUtil.errorWithMessage(weiXinM.getErrmsg());
                } else return ResultUtil.errorWithMessage(weiXinM.getErrmsg());
            } catch (IOException e) {
                return ResultUtil.errorWithMessage("消息发送失败");
            }
        }
        return ResultUtil.ok();
    }
}
