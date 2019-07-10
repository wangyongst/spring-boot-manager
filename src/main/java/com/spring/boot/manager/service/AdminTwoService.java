package com.spring.boot.manager.service;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.utils.result.Result;

public interface AdminTwoService {

    Result sendMessage(AdminParameter adminParameter);

    Result projectList(AdminParameter adminParameter);

    Result project(AdminParameter adminParameter);

    Result projectSud(AdminParameter adminParameter);

    Result resourceList(AdminParameter adminParameter);

    Result resource(AdminParameter adminParameter);

    Result resourceSud(AdminParameter adminParameter);

    Result supplierList(AdminParameter adminParameter);

    Result supplier(AdminParameter adminParameter);

    Result supplierSud(AdminParameter adminParameter);

    Result materialList(AdminParameter adminParameter);

    Result materialSud(AdminParameter adminParameter);

    Result purchList(AdminParameter adminParameter);

    Result purchCoc(AdminParameter adminParameter);

    Result askList(AdminParameter adminParameter);

    Result askSud(AdminParameter adminParameter);

    Result requestList(AdminParameter adminParameter);

    Result requestSud(AdminParameter adminParameter);

    Result request(AdminParameter adminParameter);

    Result requestAsk(AdminParameter adminParameter);
}
