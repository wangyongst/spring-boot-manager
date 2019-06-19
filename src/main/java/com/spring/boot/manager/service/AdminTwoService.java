package com.spring.boot.manager.service;

import com.spring.boot.manager.model.AdminParameter;
import com.spring.boot.manager.utils.result.Result;

import javax.servlet.http.HttpSession;

public interface AdminTwoService {

    Result projectList(AdminParameter adminParameter, HttpSession httpSession);

    Result project(AdminParameter adminParameter, HttpSession httpSession);

    Result projectSud(AdminParameter adminParameter, HttpSession httpSession);

    Result projectSearch(AdminParameter adminParameter, HttpSession httpSession);

    Result resourceList(AdminParameter adminParameter, HttpSession httpSession);

    Result resource(AdminParameter adminParameter, HttpSession httpSession);

    Result resourceSud(AdminParameter adminParameter, HttpSession httpSession);

    Result supplierList(AdminParameter adminParameter, HttpSession httpSession);

    Result supplier(AdminParameter adminParameter, HttpSession httpSession);

    Result supplierSud(AdminParameter adminParameter, HttpSession httpSession);

    Result materialList(AdminParameter adminParameter, HttpSession httpSession);

    Result materialSud(AdminParameter adminParameter, HttpSession httpSession);

}
