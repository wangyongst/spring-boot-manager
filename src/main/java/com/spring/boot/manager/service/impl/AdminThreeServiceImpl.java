package com.spring.boot.manager.service.impl;

import com.google.common.collect.Lists;
import com.spring.boot.manager.entity.Ask;
import com.spring.boot.manager.entity.Request;
import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.AdminThreeService;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import com.spring.boot.manager.utils.result.Status;
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
    public Result askList(AdminParameter adminParameter) {
        return ResultUtil.okWithData(askRepository.findAll());
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
            request = requestRepository.findById(adminParameter.getResourceid()).get();
            if (adminParameter.getDelete() != 0) {
                //requestRepository.delete(request);
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
            if (!adminParameter.getPrice().matches("^(([1-9]{1}\\d*)|(0{1}))(\\.\\d{2})$"))
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
            productRepository.findByMaterial(request.getResource().getMaterial()).forEach(e -> {
                Ask ask = new Ask();
                ask.setRequest(request);
                ask.setSupplier(e.getSupplier());
                ask.setStatus(Status.ONE);
                askRepository.save(ask);
            });
        }
        return ResultUtil.ok();
    }
}
