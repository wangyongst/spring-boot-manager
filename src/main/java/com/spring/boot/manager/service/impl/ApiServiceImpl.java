package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.Deliver;
import com.spring.boot.manager.entity.Purch;
import com.spring.boot.manager.entity.User;
import com.spring.boot.manager.repository.DeliverRepository;
import com.spring.boot.manager.repository.PurchRepository;
import com.spring.boot.manager.service.ApiService;
import com.spring.boot.manager.utils.Status;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service("ApiService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class ApiServiceImpl implements ApiService {

    @Autowired
    private PurchRepository purchRepository;

    @Autowired
    private DeliverRepository deliverRepository;

    @Override
    public Result purchList(Integer status) {
        if (status == null || status <= 0 || status >= 10) return ResultUtil.errorWithMessage("状态参数不正确");
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        return ResultUtil.okWithData(purchRepository.findAllBySupplierAndStatus(me.getRole().getSupplier(), status));
    }

    @Override
    public Result purchAccept(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.ONE) {
            purch.setStatus(Status.TWO);
            purch.setAccepttime(TimeUtils.format(System.currentTimeMillis()));
            purchRepository.save(purch);
            return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是待接单状态，不能接单");
    }

    @Override
    public Result purch(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        return ResultUtil.okWithData(purchRepository.findById(id).get());
    }

    @Override
    public Result purchPrice(Integer id, String price) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (StringUtils.isBlank(price)) return ResultUtil.errorWithMessage("报价不能为空！");
        if (price.matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"))
            return ResultUtil.errorWithMessage("采购单价只能是两位小数或整数！");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.TWO) {
            purch.setStatus(Status.THREE);
            purch.setAcceptprice(BigDecimal.valueOf(Double.parseDouble(price)));
            purchRepository.save(purch);
            return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是待报价状态，不能报价");
    }

    @Override
    public Result purchSend(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getAsk().getType() != 2) return ResultUtil.errorWithMessage("该订单不是打样订单，不能发货");
        if (purch.getStatus() == Status.FOUR) {
            purch.setStatus(Status.NINE);
            purchRepository.save(purch);
            return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是生产中状态，不能发货");
    }

    @Override
    public Result purchDeliver(Integer id, Integer delivernum) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (delivernum == null || delivernum == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (delivernum < 0) return ResultUtil.errorWithMessage("送货数量不正确");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.FOUR) {
            Deliver deliver = new Deliver();
            deliver.setPurch(purch);
            deliver.setDelivernum(delivernum);
            deliverRepository.save(deliver);
            Double deliverCount = 0D;
            List<Deliver> delivers = deliverRepository.findByPurch(purch);
            for (Deliver deli : delivers) {
                deliverCount += deli.getDelivernum();
            }
            if (deliverCount > 0 && deliverCount / purch.getAsk().getRequest().getNum() > 0.9) {
                return ResultUtil.okWithMessage("与订单采购数量相差" + (deliverCount.intValue() - purch.getAsk().getRequest().getNum()) + "个，是否终结此次生产任务?");
            } else return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是生产中状态，不能送货");
    }
}
