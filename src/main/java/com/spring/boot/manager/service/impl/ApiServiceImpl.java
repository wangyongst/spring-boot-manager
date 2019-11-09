package com.spring.boot.manager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.spring.boot.manager.entity.*;
import com.spring.boot.manager.model.vo.BillDetailV;
import com.spring.boot.manager.model.vo.BillV;
import com.spring.boot.manager.model.vo.DevlierV;
import com.spring.boot.manager.model.weixin.WeiXinM;
import com.spring.boot.manager.model.vo.PurchV;
import com.spring.boot.manager.repository.*;
import com.spring.boot.manager.service.ApiService;
import com.spring.boot.manager.utils.Status;
import com.spring.boot.manager.utils.WeixinUtils;
import com.spring.boot.manager.utils.db.TimeUtils;
import com.spring.boot.manager.utils.result.Result;
import com.spring.boot.manager.utils.result.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("ApiService")
@SuppressWarnings("All")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false)
public class ApiServiceImpl implements ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchRepository purchRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private DeliverRepository deliverRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BilldetailRepository billdetailRepository;

    @Override
    public Result banding(String code) {
        if (StringUtils.isBlank(code)) return ResultUtil.errorWithMessage("code不能为空！");
        String response = WeixinUtils.getOpenId(restTemplate, code);
        ObjectMapper mapper = new ObjectMapper();
        try {
            WeiXinM weiXinM = mapper.readValue(response, WeiXinM.class);
            if (StringUtils.isNotBlank(weiXinM.getOpenid())) {
                User me = (User) SecurityUtils.getSubject().getPrincipal();
                User user = userRepository.findById(me.getId()).get();
                user.setOpenid(weiXinM.getOpenid());
                userRepository.save(user);
                return ResultUtil.ok();
            } else return ResultUtil.errorWithMessage(weiXinM.getErrmsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.errorWithMessage("授权失败，无法获取openid");
    }

    @Override
    public Result purchList(Integer status) {
        if (status == null || status <= 0 || status >= 10) return ResultUtil.errorWithMessage("状态参数不正确");
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        List<Purch> purchList = purchRepository.findAllBySupplierAndStatus(me.getSupplier(), status);
        Setting acceptSetting = settingRepository.findByType(2).get(0);
        Setting priceSetting = settingRepository.findByType(3).get(0);
        if (purchList == null || purchList.size() == 0 || acceptSetting == null || priceSetting == null)
            return ResultUtil.ok();
        return ResultUtil.okWithData(change(purchList, acceptSetting, priceSetting));
    }

    @Override
    public Result purchAccept(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (!purchRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.THREE) {
            purch.setStatus(Status.FIVE);
            purch.setAccepttime(TimeUtils.format(System.currentTimeMillis()));
            purch.getAsk().setStatus(Status.FIVE);
            purch.getAsk().getRequest().setStatus(Status.FIVE);
            purchRepository.save(purch);
            return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是待接单状态，不能接单");
    }

    @Override
    public Result purch(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        if (!purchRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Purch purch = purchRepository.findById(id).get();
        Setting acceptSetting = settingRepository.findByType(2).get(0);
        Setting priceSetting = settingRepository.findByType(3).get(0);
        if (acceptSetting == null || priceSetting == null) return ResultUtil.ok();
        return ResultUtil.okWithData(change(purch, acceptSetting, priceSetting));
    }

    @Override
    public Result purchPrice(Integer id, String price) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (StringUtils.isBlank(price)) return ResultUtil.errorWithMessage("报价不能为空！");
        if (!price.matches("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$"))
            return ResultUtil.errorWithMessage("报价只能是两位小数或整数！");
        if (!purchRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.ONE) {
            purch.setStatus(Status.TWO);
            purch.getAsk().setStatus(Status.TWO);
            purch.getAsk().getRequest().setStatus(Status.TWO);
            purch.setAcceptprice(BigDecimal.valueOf(Double.parseDouble(price)));
            if ((purch.getAsk().getRequest().getPrice() == null || purch.getAsk().getRequest().getPrice().doubleValue() == 0.00d) && purch.getAsk().getRequest().getType() == 1) {
                Setting setting = settingRepository.findByType(1).get(0);
                purch.getAsk().getRequest().setPrice(new BigDecimal(price).multiply(setting.getValue()));
                if (purch.getAsk().getRequest().getSellnum() != null)
                    purch.getAsk().getRequest().setTotal(purch.getAsk().getRequest().getPrice().multiply(new BigDecimal(purch.getAsk().getRequest().getSellnum())));
            }
            purchRepository.save(purch);
            return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是待报价状态，不能报价");
    }

    @Override
    public Result purchSend(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (!purchRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getAsk().getType() != 2) return ResultUtil.errorWithMessage("该订单不是打样订单，不能发货");
        if (purch.getStatus() == Status.FIVE) {
            purch.setStatus(Status.SEVEN);
            purch.getAsk().getRequest().setStatus(Status.SEVEN);
            purchRepository.save(purch);
            return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是生产中状态，不能发货");
    }

    @Override
    public Result purchDeliver(Integer id, Integer delivernum) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (delivernum == null || delivernum == 0) return ResultUtil.errorWithMessage("送货数量不能为空");
        if (delivernum < 0) return ResultUtil.errorWithMessage("送货数量不正确");
        if (!purchRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.FIVE) {
            Deliver deliver = new Deliver();
            deliver.setCreatetime(TimeUtils.format(System.currentTimeMillis()));
            deliver.setPurch(purch);
            deliver.setDelivernum(delivernum);
            deliver.setStatus(Status.ONE);
            deliverRepository.save(deliver);
            Double deliverCount = 0D;
            List<Deliver> delivers = deliverRepository.findByPurch(purch);
            for (Deliver deli : delivers) {
                deliverCount += deli.getDelivernum();
            }
            if (deliverCount > 0 && deliverCount / purch.getAsk().getRequest().getNum() >= 0.9) {
                return ResultUtil.okWithMessage("与订单采购数量相差" + (purch.getAsk().getRequest().getNum() - deliverCount.intValue()) + "个，是否终结此次生产任务?");
            } else return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是生产中状态，不能送货");
    }

    @Override
    public Result deliverList() {
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        if (me.getSupplier() == null) return ResultUtil.errorWithMessage("不是供应商");
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                predicates.add(criteriaBuilder.equal(root.get("purch").get("supplier"), me.getSupplier()));
                predicates.add(criteriaBuilder.between(root.get("status"), Status.ONE, Status.TWO));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<Deliver> deliverList = deliverRepository.findAll(specification);
        if (deliverList == null || deliverList.size() == 0) return ResultUtil.ok();
        return ResultUtil.okWithData(change(deliverList));
    }

    @Override
    public Result deliver(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (!deliverRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Deliver deliver = deliverRepository.findById(id).get();
        return ResultUtil.okWithData(changeVo(deliver));
    }

    @Override
    public Result deliverAccept(Integer id, Integer delivernum) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (delivernum == null) return ResultUtil.errorWithMessage("收货数量不能为空");
        if (!deliverRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Deliver deliver = deliverRepository.findById(id).get();
        if (deliver.getStatus() != Status.ONE) return ResultUtil.errorWithMessage("不可以收货");
        if (delivernum > deliver.getDelivernum()) return ResultUtil.errorWithMessage("收货数量不可以大于送货数量");
        deliver.setConfirmnum(delivernum);
        deliver.setAccepttime(TimeUtils.format(System.currentTimeMillis()));
        deliver.setStatus(Status.TWO);
        deliverRepository.save(deliver);
        return ResultUtil.ok();
    }

    @Override
    public Result deliverNumber(Integer id, Integer delivernum) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (delivernum == null) return ResultUtil.errorWithMessage("收货数量不能为空");
        if (!deliverRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Deliver deliver = deliverRepository.findById(id).get();
        if (deliver.getStatus() != Status.ONE) return ResultUtil.errorWithMessage("不可以修改");
        deliver.setDelivernum(delivernum);
        deliverRepository.save(deliver);
        return ResultUtil.ok();
    }

    @Override
    public Result purchComplete(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (!purchRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Purch purch = purchRepository.findById(id).get();
        boolean flag = false;
        boolean flag2 = true;
        for (Deliver deliver : deliverRepository.findByPurch(purch)) {
            if (deliver.getStatus() != 3) flag = true;
            flag2 = false;
        }
        if(flag2) return ResultUtil.errorWithMessage("没有生成送货任务，不能结束");
        if (flag) {
            purch.setStatus(Status.SIX);
            purch.getAsk().getRequest().setStatus(Status.SIX);
        } else {
            purch.setStatus(Status.SEVEN);
            purch.getAsk().getRequest().setStatus(Status.SEVEN);
            purch.getAsk().setOvertime(TimeUtils.format(System.currentTimeMillis()));
        }
        purchRepository.save(purch);
        return ResultUtil.ok();
    }

    @Override
    public Result deliverConfirm(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (!deliverRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Deliver deliver = deliverRepository.findById(id).get();
        if (deliver.getStatus() != Status.TWO) return ResultUtil.errorWithMessage("不可以确认");
        deliver.setStatus(Status.THREE);
        Purch purch = deliver.getPurch();
        if (purch.getAcceptnum() == null || purch.getAcceptnum() == 0){
            purch.setAcceptnum(deliver.getConfirmnum());
        }
        else {
            purch.setAcceptnum(purch.getAcceptnum() + deliver.getConfirmnum());
        }
        purch.getAsk().getRequest().setAcceptnum(purch.getAcceptnum());
        boolean flag = true;
        for (Deliver deliver1 : deliverRepository.findByPurch(purch)) {
            if (deliver1.getStatus() != 3) flag = false;
        }
        if (flag && purch.getStatus() == Status.SIX) {
            purch.setStatus(Status.SEVEN);
            purch.getAsk().getRequest().setStatus(Status.SEVEN);
            purch.getAsk().setOvertime(TimeUtils.format(System.currentTimeMillis()));
        }
        deliverRepository.save(deliver);
        return ResultUtil.ok();
    }

    @Override
    public Result billList() {
        User me = (User) SecurityUtils.getSubject().getPrincipal();
        if (me.getSupplier() == null) return ResultUtil.errorWithMessage("不是供应商");
        List<Bill> bills = billRepository.findBySupplier(me.getSupplier());
        if (bills == null || bills.size() == 0) return ResultUtil.ok();
        return ResultUtil.okWithData(changeB(bills));
    }

    @Override
    public Result billOk(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (!billRepository.existsById(id)) return ResultUtil.errorWithMessage("单号错误");
        Bill bill = billRepository.findById(id).get();
        List<Billdetail> billdetails = billdetailRepository.findByBill(bill);
        billdetails.forEach(e -> {
            e.setStatus(Status.TWO);
            billdetailRepository.save(e);
            Purch purch = e.getPurch();
            purch.setStatus(Status.FINISH);
            purch.getAsk().getRequest().setStatus(Status.FINISH);
            purchRepository.save(purch);
        });
        bill.setStatus(Status.TWO);
        billRepository.save(bill);
        return ResultUtil.ok();
    }


    public PurchV changeVo(Purch purch, Setting acceptSetting, Setting priceSetting) {
        PurchV p = new PurchV();
        p.setId(purch.getId());
        p.setNumber(purch.getAsk().getRequest().getNumber());
        p.setType(purch.getAsk().getType());
        p.setCreatetime(purch.getAsk().getCreatetime());
        p.setStatus(purch.getStatus());
        p.setProjectname(purch.getAsk().getRequest().getResource().getProject().getName());
        p.setFile(purch.getAsk().getRequest().getResource().getFile());
        p.setSize(purch.getAsk().getRequest().getResource().getSize());
        p.setSpecial(purch.getAsk().getRequest().getResource().getSpecial());
        p.setModel(purch.getAsk().getRequest().getResource().getModel());
        p.setCode(purch.getAsk().getRequest().getResource().getCode());
        p.setMaterialname(purch.getAsk().getRequest().getResource().getMaterial().getName());
        p.setNum(purch.getAsk().getRequest().getNum());
        p.setContact(purch.getAsk().getRequest().getCreateusername());
        p.setMobile(purch.getAsk().getRequest().getCreateusermobile());
        p.setPrice(purch.getAsk().getRequest().getPrice());
        p.setProjectname(purch.getAsk().getRequest().getResource().getProject().getName());
        if (purch.getStatus() > Status.THREE) {
            p.setAcceptnum(purch.getAcceptnum());
            if (p.getAcceptnum() == null) p.setAcceptnum(0);
            //送货数量
            p.setDelivernum(0);
            purch.getDelivers().forEach(e -> {
                p.setDelivernum(p.getDelivernum() + e.getDelivernum());
            });
            //待生产数量
            p.setProductnum(purch.getAsk().getRequest().getNum() - p.getDelivernum());
        }
        if (p.getStatus() == 1) {
            try {
                p.setTime(acceptSetting.getValue().multiply(new BigDecimal(3600000)).longValue() - (System.currentTimeMillis() - TimeUtils.parse(purch.getAsk().getCreatetime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (p.getStatus() == 3 && p.getType() != 1) {
            try {
                p.setTime(priceSetting.getValue().multiply(new BigDecimal(3600000)).longValue() - (System.currentTimeMillis() - TimeUtils.parse(purch.getAsk().getConfirmtime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        p.setAcceptprice(purch.getAcceptprice());
        if (p.getPrice() == null) p.setPrice(new BigDecimal(0));
        if (p.getNum() == null) p.setNum(0);
        return p;
    }


    public DevlierV changeVo(Deliver deliver) {
        DevlierV d = new DevlierV();
        d.setId(deliver.getId());
        d.setPurchid(deliver.getPurch().getId());
        d.setType(deliver.getPurch().getAsk().getType());
        d.setCreatetime(deliver.getPurch().getAsk().getCreatetime());
        d.setStatus(deliver.getStatus());
        d.setProjectname(deliver.getPurch().getAsk().getRequest().getResource().getProject().getName());
        d.setFile(deliver.getPurch().getAsk().getRequest().getResource().getFile());
        d.setSize(deliver.getPurch().getAsk().getRequest().getResource().getSize());
        d.setSpecial(deliver.getPurch().getAsk().getRequest().getResource().getSpecial());
        d.setModel(deliver.getPurch().getAsk().getRequest().getResource().getModel());
        d.setCode(deliver.getPurch().getAsk().getRequest().getResource().getCode());
        d.setMaterialname(deliver.getPurch().getAsk().getRequest().getResource().getMaterial().getName());
        d.setNum(deliver.getPurch().getAsk().getRequest().getNum());
        d.setAcceptnum(deliver.getPurch().getAcceptnum());
        d.setConfirmnum(deliver.getConfirmnum());
        d.setDelivernum(deliver.getDelivernum());
        d.setContact(deliver.getPurch().getAsk().getRequest().getCreateusername());
        d.setMobile(deliver.getPurch().getAsk().getRequest().getCreateusermobile());
        d.setAcceptprice(deliver.getPurch().getAcceptprice());
        d.setPrice(deliver.getPurch().getAsk().getRequest().getPrice());
        d.setProjectname(deliver.getPurch().getAsk().getRequest().getResource().getProject().getName());
        d.setCustomer(deliver.getPurch().getAsk().getRequest().getResource().getProject().getCustomer());
        d.setSuppliername(deliver.getPurch().getSupplier().getName());
        d.setDelivertime(deliver.getCreatetime());
        int accpetnum = 0;
        if (deliver.getPurch().getAcceptnum() != null) accpetnum = deliver.getPurch().getAcceptnum();
        int delivernum = 0;
        for (Deliver deliver1 : deliver.getPurch().getDelivers()) {
            delivernum += deliver1.getDelivernum();
        }
        d.setProductnum(deliver.getPurch().getAsk().getRequest().getNum() - delivernum);
        if (d.getAcceptnum() == null) d.setAcceptnum(0);
        return d;
    }

    public BillV changeVo(Bill bill) {
        BillV b = new BillV();
        b.setId(bill.getId());
        b.setMonth(bill.getBilltime());
        if (bill.getBilldetails().get(0).getStatus() == Status.ONE) b.setStatus(Status.ONE);
        else b.setStatus(Status.TWO);
        b.setTotal(bill.getTotal());
        List<BillDetailV> billDetailVS = new ArrayList<>();
        bill.getBilldetails().forEach(e -> {
            BillDetailV bv = new BillDetailV();
            bv.setCode(e.getPurch().getAsk().getRequest().getResource().getCode());
            bv.setMaterialname(e.getPurch().getAsk().getRequest().getResource().getMaterial().getName());
            bv.setOvertime(e.getPurch().getAsk().getOvertime());
            Integer delivernum = 0;
            for (Deliver deliver : e.getPurch().getDelivers()) {
                delivernum += deliver.getDelivernum();
            }
            bv.setNumber(e.getPurch().getAsk().getRequest().getNumber());
            bv.setDelivernum(delivernum);
            bv.setAcceptnum(e.getPurch().getAcceptnum());
            bv.setNum(e.getPurch().getAsk().getRequest().getNum());
            bv.setPrice(e.getPurch().getAsk().getRequest().getPrice());
            bv.setAcceptprice(e.getPurch().getAcceptprice());
            if(e.getPurch().getAcceptprice() != null && e.getPurch().getAcceptnum() !=null) {
                bv.setTotal(e.getPurch().getAcceptprice().multiply(new BigDecimal(e.getPurch().getAcceptnum())));
            }else{
                bv.setTotal(new BigDecimal(0));
            }
            billDetailVS.add(bv);
        });
        b.setBillDetailVList(billDetailVS);
        return b;
    }


    public Object change(Object object, Setting acceptSetting, Setting priceSetting) {
        if (object instanceof Purch) {
            return changeVo((Purch) object, acceptSetting, priceSetting);
        } else if (object instanceof List) {
            List<PurchV> purchVS = new ArrayList<>();
            ((List<Purch>) object).forEach(e -> {
                purchVS.add(changeVo(e, acceptSetting, priceSetting));
            });
            return purchVS;
        }
        return null;
    }


    public Object change(Object object) {
        if (object instanceof Deliver) {
            return changeVo((Deliver) object);
        } else if (object instanceof List) {
            List<DevlierV> devlierVS = new ArrayList<>();
            ((List<Deliver>) object).forEach(e -> {
                devlierVS.add(changeVo(e));
            });
            return devlierVS;
        }
        return null;
    }

    public Object changeB(Object object) {
        if (object instanceof List) {
            List<BillV> billVS = new ArrayList<>();
            ((List<Bill>) object).forEach(e -> {
                billVS.add(changeVo(e));
            });
            return billVS;
        }
        return null;
    }


    public void deleteDeliver(Deliver deliver) {
        deliverRepository.delete(deliver);
    }
}
