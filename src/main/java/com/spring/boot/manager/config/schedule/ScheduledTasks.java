package com.spring.boot.manager.config.schedule;

import com.spring.boot.manager.service.AdminTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private AdminTwoService adminTwoService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void minCron() {
        //未报价单失效
        adminTwoService.priceSchedu();
        //自动派单
        adminTwoService.acceptSchedu();
        //再次自动派单
        adminTwoService.acceptSchedu2();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void monCron() {
        //生成对账单
        adminTwoService.billSchedu();
    }
}
