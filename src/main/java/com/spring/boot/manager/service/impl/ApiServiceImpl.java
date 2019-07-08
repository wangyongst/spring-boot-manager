package com.spring.boot.manager.service.impl;

import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.vo.PurchV;
import com.spring.boot.manager.repository.AskRepository;
import com.spring.boot.manager.repository.DeliverRepository;
import com.spring.boot.manager.repository.PurchRepository;
import com.spring.boot.manager.repository.RequestRepository;
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
import java.util.ArrayList;
import java.util.List;


@Service("ApiService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class ApiServiceImpl implements ApiService {

    @Autowired
    private PurchRepository purchRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private DeliverRepository deliverRepository;

    @Override
    public Result purchList(Integer status) {
        if (status == null || status <= 0 || status >= 10) return ResultUtil.errorWithMessage("状态参数不正确");
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        List<Purch> purchList = purchRepository.findAllBySupplierAndStatus(me.getSupplier(), status);
        return ResultUtil.okWithData(change(purchList));
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
        Purch purch = purchRepository.findById(id).get();
        return ResultUtil.okWithData(change(purch));
    }

    @Override
    public Result purchPrice(Integer id, String price) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (StringUtils.isBlank(price)) return ResultUtil.errorWithMessage("报价不能为空！");
        if (!price.matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"))
            return ResultUtil.errorWithMessage("报价只能是两位小数或整数！");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.ONE) {
            if (purch.getAsk().getType() == 1) {
                purch.setStatus(Status.FINISH);
            } else {
                purch.setStatus(Status.TWO);
            }
            purch.setAcceptprice(BigDecimal.valueOf(Double.parseDouble(price)));
            purchRepository.save(purch);
            Ask ask = purch.getAsk();
            List<Purch> purchList = purchRepository.findAllByAsk(ask);
            boolean iscomplete = true;
            for(Purch p:purchList) {
               if(purch.getAcceptprice() == null) iscomplete = false;
            }
            if(iscomplete){
                ask.setStatus(Status.TWO);
                askRepository.save(ask);
            }
            return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是待报价状态，不能报价");
    }

    @Override
    public Result purchSend(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getAsk().getType() != 2) return ResultUtil.errorWithMessage("该订单不是打样订单，不能发货");
        if (purch.getStatus() == Status.FOUR) {
            purch.setStatus(Status.FINISH);
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
            if (deliverCount > 0 && deliverCount / purch.getAsk().getRequest().getNum() >= 0.9) {
                return ResultUtil.okWithMessage("与订单采购数量相差" + (deliverCount.intValue() - purch.getAsk().getRequest().getNum()) + "个，是否终结此次生产任务?");
            } else return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是生产中状态，不能送货");
    }


    public PurchV changeVo(Purch purch) {
        PurchV p = new PurchV();
        p.setType(purch.getAsk().getType());
        p.setCreatetime(purch.getAsk().getCreatetime());
        p.setStatus(purch.getStatus());
        p.setProjectname(purch.getAsk().getRequest().getResource().getProject().getName());
        p.setFile(purch.getAsk().getRequest().getResource().getFile());
        p.setSize(purch.getAsk().getRequest().getResource().getSize());
        p.setSpecial(purch.getAsk().getRequest().getResource().getSpecial());
        p.setModel(purch.getAsk().getRequest().getResource().getModel());
        p.setCode(purch.getAsk().getRequest().getResource().getMaterial().getCode());
        p.setMaterialname(purch.getAsk().getRequest().getResource().getMaterial().getName());
        p.setNum(purch.getAsk().getRequest().getNum());
//        //待生产数量
//        private Integer productnum;
//        //送货数量
//        private Integer delivernum;
//        //实收数量
//        private Integer acceptnum;
        p.setAcceptprice(purch.getAcceptprice());
        p.setContact(purch.getSupplier().getContacts());
        p.setMobile(purch.getSupplier().getMobile());
        return p;
    }

    public Object change(Object object) {
        if (object instanceof Purch) {
            return changeVo((Purch) object);
        } else if (object instanceof List) {
            List<PurchV> purchVS = new ArrayList<>();
            ((List<Purch>) object).forEach(e -> {
                purchVS.add(changeVo(e));
            });
            return purchVS;
        }
        return null;
    }
}
