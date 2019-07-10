package com.spring.boot.manager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.manager.entity.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public Result banding(String code) {
        if (StringUtils.isBlank(code)) return ResultUtil.errorWithMessage("code不能为空！");
        String response = WeixinUtils.getOpenId(restTemplate, code);
        ObjectMapper mapper = new ObjectMapper();
        try {
            WeiXinM weiXinM = mapper.readValue(response, WeiXinM.class);
            if (weiXinM.getErrcode() != null && weiXinM.getErrcode() == 0 && StringUtils.isNotBlank(weiXinM.getOpenid())) {
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
        return ResultUtil.okWithData(change(purchList, acceptSetting, priceSetting));
    }

    @Override
    public Result purchAccept(Integer id) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        Purch purch = purchRepository.findById(id).get();
        if (purch.getStatus() == Status.THREE) {
            purch.setStatus(Status.FOUR);
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
        Setting acceptSetting = settingRepository.findByType(2).get(0);
        Setting priceSetting = settingRepository.findByType(3).get(0);
        return ResultUtil.okWithData(change(purch, acceptSetting, priceSetting));
    }

    @Override
    public Result purchPrice(Integer id, String price) {
        if (id == null || id == 0) return ResultUtil.errorWithMessage("单号不能为空");
        if (StringUtils.isBlank(price)) return ResultUtil.errorWithMessage("报价不能为空！");
        if (!StringUtils.isNumeric(price)) return ResultUtil.errorWithMessage("报价只能是数字！");
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
            for (Purch p : purchList) {
                if (purch.getAcceptprice() == null) iscomplete = false;
            }
            if (iscomplete) {
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
        if (delivernum == null || delivernum == 0) return ResultUtil.errorWithMessage("送货数量不能为空");
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
                return ResultUtil.okWithMessage("与订单采购数量相差" + (purch.getAsk().getRequest().getNum() - deliverCount.intValue()) + "个，是否终结此次生产任务?");
            } else return ResultUtil.ok();
        } else return ResultUtil.errorWithMessage("该订单不是生产中状态，不能送货");
    }


    public PurchV changeVo(Purch purch, Setting acceptSetting, Setting priceSetting) {
        PurchV p = new PurchV();
        p.setId(purch.getId());
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
        if (purch.getStatus() > Status.THREE) {
            p.setAcceptnum(purch.getAcceptnum());
            if (p.getAcceptnum() == null) p.setAcceptnum(0);
            //送货数量
            p.setDelivernum(0);
            purch.getDelivers().forEach(e -> {
                p.setDelivernum(p.getDelivernum() + e.getDelivernum());
            });
            //待生产数量
            p.setProductnum(purch.getAsk().getRequest().getNum() - p.getAcceptnum() - p.getDelivernum());
            p.setContact(purch.getSupplier().getContacts());
            p.setMobile(purch.getSupplier().getMobile());
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
        return p;
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
}
