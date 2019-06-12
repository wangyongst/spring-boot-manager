package com.spring.boot.manager.service;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.utils.result.Result;

import javax.servlet.http.HttpSession;

public interface AdminTwoService {
    Result askList(AdminParameter adminParameter, HttpSession httpSession);

    Result applyList(AdminParameter adminParameter, HttpSession httpSession);

    Result projectList(AdminParameter adminParameter, HttpSession httpSession);

    Result resourceList(AdminParameter adminParameter, HttpSession httpSession);

    Result supplierList(AdminParameter adminParameter, HttpSession httpSession);

    Result apply(AdminParameter adminParameter, HttpSession httpSession);
}
