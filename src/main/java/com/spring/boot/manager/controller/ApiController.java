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

    @ApiOperation(value = "订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = " 1.待接单 2.待报价 3.待审核 4 生产中 5.待送货 5.待收货 6.待确定 7.待出账 8已出账 9完结", required = true, dataType = "Integer")

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

    @ApiOperation(value = "送货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "delivernum", value = "送货数量", required = true, dataType = "Integer")

    })
    @PostMapping("/purch/deliver")
    public Result purchDeliver(@RequestParam Integer id,@RequestParam Integer delivernum) {
        return apiService.purchDeliver(id,delivernum);
    }



}
