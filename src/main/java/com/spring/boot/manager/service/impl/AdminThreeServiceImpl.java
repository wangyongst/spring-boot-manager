package com.spring.boot.manager.service.impl;

import com.google.common.collect.Lists;
import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminThreeService;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import com.spring.boot.manager.utils.Status;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;


@Service
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class AdminThreeServiceImpl implements AdminThreeService {

    private static final Logger logger = LogManager.getLogger(AdminThreeServiceImpl.class);

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private PurchRepository purchRepository;

    @Autowired
    private RequestRepository requestRepository;

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
    public Result purchList(AdminParameter adminParameter) {
//        Specification specification = new Specification() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = Lists.newArrayList();
//                predicates.add(criteriaBuilder.notEqual(root.get("type"), 2));
//                if (StringUtils.isNotBlank(adminParameter.getCreatetime())) {
//                    predicates.add(criteriaBuilder.like(root.get("createtime"), adminParameter.getCreatetime() + "%"));
//                }
//                if (adminParameter.getStatus() != 0) {
//                    if (adminParameter.getStatus() == 99) {
//                        predicates.add(criteriaBuilder.notEqual(root.get("status"), 7));
//                    } else if (adminParameter.getStatus() == 100) {
//                        predicates.add(criteriaBuilder.equal(root.get("status"), 7));
//                    }
//                }
//                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
//        return ResultUtil.okWithData(askRepository.findAll(specification, sort));
        return ResultUtil.okWithData(purchRepository.findAll());
    }

    @Override
    public Result askList(AdminParameter adminParameter) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                predicates.add(criteriaBuilder.notEqual(root.get("type"), 2));
                if (StringUtils.isNotBlank(adminParameter.getCreatetime())) {
                    predicates.add(criteriaBuilder.like(root.get("createtime"), adminParameter.getCreatetime() + "%"));
                }
                if (adminParameter.getStatus() != 0) {
                    if (adminParameter.getStatus() == 99) {
                        predicates.add(criteriaBuilder.notEqual(root.get("status"), 7));
                    } else if (adminParameter.getStatus() == 100) {
                        predicates.add(criteriaBuilder.equal(root.get("status"), 7));
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
            request.setCreateusername(me.getName());
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
            request.setTotal(request.getPrice().multiply(new BigDecimal(request.getNum().toString())));
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
            } else if (adminParameter.getType() == 2) {
                if (request.getNum() == 1 && (request.getSellnum() == null || request.getSellnum() == 0)) {
                    ask.setType(2);
                    final Ask saveedask = askRepository.save(ask);
                    productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                        Purch purch = new Purch();
                        purch.setAsk(saveedask);
                        purch.setSupplier(e.getSupplier());
                        purch.setStatus(Status.TWO);
                        purchRepository.save(purch);
                    });
                } else {
                    return ResultUtil.errorWithMessage("采购数量必须为1，销售数量必须为0！");
                }
            } else if (adminParameter.getType() == 3) {
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

    public void deleteRequest(Request request) {
        askRepository.findAllByRequest(request).forEach(e -> {
            deleteAsk(e);
        });
        requestRepository.delete(request);
    }

    public void deleteAsk(Ask ask) {
        purchRepository.deleteAllByAsk(ask);
        askRepository.delete(ask);
    }
}
