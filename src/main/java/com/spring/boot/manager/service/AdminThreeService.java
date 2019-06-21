package com.spring.boot.manager.service;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.utils.result.Result;

import javax.servlet.http.HttpSession;

public interface AdminThreeService {
    Result askList(AdminParameter adminParameter, HttpSession httpSession);

    Result requestList(AdminParameter adminParameter, HttpSession httpSession);

    Result requestSud(AdminParameter adminParameter, HttpSession httpSession);

    Result request(AdminParameter adminParameter, HttpSession httpSession);

    Result requestAsk(AdminParameter adminParameter, HttpSession httpSession);
}
