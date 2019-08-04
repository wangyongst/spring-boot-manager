package com.spring.boot.manager.controller;


import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.service.ApiService;
import com.spring.boot.manager.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "供应商接口")
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

    @Autowired
    private ApiService apiService;


    @ApiOperation(value = "绑定微信", notes = "回传微信登录获取的code,实现和后台账号绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = " code（必需）,String型", required = true, dataType = "String")
    })
    @PostMapping(value = "/banding")
    public Result banding(@RequestParam("code") String code) {
        return apiService.banding(code);
    }


    @ApiOperation(value = "订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = " 1.待报价 3.待接单 5 生产中 8.待出账 9完结", required = true, dataType = "Integer")

    })
    @GetMapping("/purch/list")
    public Result purchList(@RequestParam Integer status) {
        return apiService.purchList(status);
    }

    @ApiOperation(value = "接单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Integer")

    })
    @PostMapping("/purch/accept")
    public Result purchAccept(@RequestParam Integer id) {
        return apiService.purchAccept(id);
    }

    @ApiOperation(value = "订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Integer")

    })
    @GetMapping("/purch")
    public Result purch(@RequestParam Integer id) {
        return apiService.purch(id);
    }


    @ApiOperation(value = "报价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "price", value = "报价", required = true, dataType = "String")

    })
    @PostMapping("/purch/price")
    public Result purchPrice(@RequestParam Integer id, @RequestParam String price) {
        return apiService.purchPrice(id, price);
    }

    @ApiOperation(value = "打样发货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Integer")

    })
    @PostMapping("/purch/send")
    public Result purchPrice(@RequestParam Integer id) {
        return apiService.purchSend(id);
    }

    @ApiOperation(value = "生成送货单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "delivernum", value = "送货数量", required = true, dataType = "Integer")

    })
    @PostMapping("/purch/deliver")
    public Result purchDeliver(@RequestParam Integer id, @RequestParam Integer delivernum) {
        return apiService.purchDeliver(id, delivernum);
    }

    @ApiOperation(value = "完成订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Integer")

    })
    @PostMapping("/purch/complete")
    public Result purchComplete(@RequestParam Integer id) {
        return apiService.purchComplete(id);
    }

    @ApiOperation(value = "送货单列表(1待送货 2送货中)")
    @GetMapping("/deliver/list")
    public Result deliverList() {
        return apiService.deliverList();
    }

    @ApiOperation(value = "送货单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "送货单id", required = true, dataType = "Integer")

    })
    @GetMapping("/deliver")
    public Result deliver(@RequestParam Integer id) {
        return apiService.deliver(id);
    }


    @ApiOperation(value = "送货单修改送货数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "送货单id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "delivernum", value = "送货数量", required = true, dataType = "Integer")
    })
    @PostMapping("/deliver/number")
    public Result deliverNumber(@RequestParam Integer id, @RequestParam Integer delivernum) {
        return apiService.deliverNumber(id, delivernum);
    }


    @ApiOperation(value = "收货确定(收货员)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "送货单id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "delivernum", value = "收货数量", required = true, dataType = "Integer")
    })
    @PostMapping("/deliver/accept")
    public Result deliverAccept(@RequestParam Integer id, @RequestParam Integer delivernum) {
        return apiService.deliverAccept(id, delivernum);
    }

    @ApiOperation(value = "收货确定(供应商)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "送货单id", required = true, dataType = "Integer")
    })
    @PostMapping("/deliver/confirm")
    public Result deliverConfirm(@RequestParam Integer id) {
        return apiService.deliverConfirm(id);
    }

    @ApiOperation(value = "对账单")
    @GetMapping("/bill/list")
    public Result billList() {
        return apiService.billList();
    }

    @ApiOperation(value = "提交对账单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "对账单id", required = true, dataType = "Integer")
    })
    @PostMapping("/bill/ok")
    public Result billOk(@RequestParam Integer id) {
        return apiService.billOk(id);
    }
}
