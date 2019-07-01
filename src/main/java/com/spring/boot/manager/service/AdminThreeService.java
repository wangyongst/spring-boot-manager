package com.spring.boot.manager.service;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.utils.result.Result;

public interface AdminThreeService {

    Result purchList(AdminParameter adminParameter);

    Result askList(AdminParameter adminParameter);

    Result askSud(AdminParameter adminParameter);

    Result requestList(AdminParameter adminParameter);

    Result requestSud(AdminParameter adminParameter);

    Result request(AdminParameter adminParameter);

    Result requestAsk(AdminParameter adminParameter);
}
